/*
    La classe Simulator implementa un simulatore di tipo 'next-event'
    seguendo la logica di gestione dei job descritta dall'Algoritmo 2.
    Il tempo di simulazione è rappresentato dalla variabile 'STOP'.
    Si utilizza un array di 20 elementi per la gestione degli eventi
    del Cloudlet e una struttura dati dinamica (heap) per quelli del Cloud.

    Dopo la parte di inizializzazione delle variabili e di instanziazione
    delle classi, vi è un ciclo 'while' che si ripete fino allo scadere
    del tempo di simulazione. All'interno di esso vi è la logica
    dell'applicazione: ad ogni giro vengono aggiornate le statistiche di
    interesse e viene individuato l'evento più imminente (che può essere
    un arrivo, una partenza dal Cloudlet o una partenza dal Cloud), che
    viene gestito secondo la logica descritta dall'Algoritmo 2.
 */

class Simulator {

    static double START = 0.0;         // tempo di inizio
    static double STOP = 50000.0;      // condizione di terminazione
    static int SERVERS = 20;           // numero di server
    static double sarrival = START;
    static double sarrival1 = START;
    static double sarrival2 = START;
    double end_time;


    public void start_simulation(long seed, int index){

        long number = 0;        // numero di job nel Cloudlet
        long number1 = 0;       // numero di job di classe 1 nel Cloudlet
        long number2 = 0;       // numero di job di classe 2 nel Cloudlet
        int e;                  // indice dell'evento più imminente
        int s;                  // indice del server
        double service = 0;
        double setup = 0.8;     // tempo di setup per job interrotti

        Rngs r = new Rngs();
        r.plantSeeds(seed);

        Statistics statistics = new Statistics();

        Event[] event = new Event[SERVERS + 1];
        for (s = 0; s < SERVERS + 1; s++) {
            event[s] = new Event();
        }

        Heap cloud_event_heap = new Heap();

        Time t = new Time();
        CloudEvent cloudEvent = new CloudEvent();
        CloudletEvent cletEvent = new CloudletEvent();
        EventManager em = new EventManager();

        t.current = START;

        // il primo evento è un arrivo
        double time_arrival1 = em.getArrival1(r, index) + sarrival;
        double time_arrival2 = em.getArrival2(r, index) + sarrival;

        if(time_arrival1<time_arrival2){
            event[0].t = time_arrival1;
            statistics.arrival_type=1;
            event[0].type=1;
            sarrival=time_arrival1;


        }else{
            event[0].t = time_arrival2;
            statistics.arrival_type=2;
            event[0].type=2;
            sarrival=time_arrival2;
        }

        event[0].x = 1;
        for (s = 1; s <= SERVERS; s++) {
            event[s].t = START;
            event[s].x = 0;        // tutti i server sono inizialmente idle
        }

        while ((event[0].x != 0) || (number != 0)) {

            // calcolo del numero medio di job nel cloud
            cloudEvent.record_cloud_jobs(cloud_event_heap);

            // calcolo del numero medio di job nel cloudlet
            cletEvent.record_cloudlet_jobs(event);

            // si cerca qual è il prossimo evento
            em.nextEvent(event, cloud_event_heap);

            if(statistics.event_to_manage==2){
                // l'evento è una partenza dal Cloud
                statistics.tot_processed++;
                statistics.completitions_cloud++;
                em.manage_departure_from_cloud(cloud_event_heap);

            } else{
                // si gestisce l'evento sul Cloudlet

                e = statistics.clet_event_index;

                t.next = event[e].t;          // tempo di next event
                t.current = t.next;           // si aggiorna il tempo corrente

                // l'evento selezionato è un arrivo
                if (e == 0) {

                    statistics.tot_arrived++;

                    time_arrival1 = em.getArrival1(r, index) + sarrival;
                    time_arrival2 = em.getArrival2(r, index) + sarrival;

                    if(time_arrival1<time_arrival2){       // l'arrivo è di tipo 1
                        event[0].t = time_arrival1;
                        statistics.arrival_type=1;
                        event[0].type=1;
                        event[0].arrival_time = sarrival;
                        sarrival=time_arrival1;

                    }else{                                 // l'arrivo è di tipo 2
                        event[0].t = time_arrival2;
                        statistics.arrival_type=2;
                        event[0].type=2;
                        event[0].arrival_time = sarrival;
                        sarrival=time_arrival2;
                    }

                    if (event[0].t > STOP){
                        cloudEvent.empity_cloud(cloud_event_heap);
                        event[0].x = 0;
                    }


                    if(number1+number2>=SERVERS){

                        if(statistics.arrival_type==2 ||
                                (statistics.arrival_type==1 && number1==SERVERS)){

                            // il job viene gestito dal Cloud

                            statistics.arrivals_on_cloud++;

                            Event ev = new Event();

                            if(event[0].type == 1){
                                statistics.arrival_type1_cloud++;
                                service = em.getService1_cloud(r, index);
                                statistics.sum_service_times1_cloud += service;
                                statistics.sum_service_time_tot+=service;

                            }else{
                                statistics.arrival_type2_cloud++;
                                statistics.arrival_type2_cloud_fromController++;
                                service = em.getService2_cloud(r, index);
                                statistics.sum_service_times2_cloud += service;
                                statistics.sum_service_time_tot+=service;
                            }

                            ev.type = event[0].type;
                            ev.t = t.current + service;
                            ev.x = 1;

                            // l'evento viene aggiunto alla
                            // struttura dati heap del cloud
                            cloud_event_heap.insert(ev);


                        } else if(statistics.arrival_type==1 && number2>0){

                            // Si interrompe un job di classe 2
                            // in esecuzione sul cloudlet

                            number1++;
                            number2--;
                            statistics.interrupted++;

                            int g = em.findClass2Job(event);

                            statistics.arrivals_on_cloud++;
                            statistics.arrival_type2_cloud++;
                            statistics.arrival_type2_cloud_interrupted++;

                            Event ev = new Event();

                            service = em.getService2_cloud(r, index);

                            statistics.sum_response_times_int_cloud +=
                                    service + setup;
                            // tempo trascorso dal job sul Cloudlet
                            statistics.sum_spent_time_int_clet +=
                                    (t.current - event[g].arrival_time);
                            statistics.sum_service_time_tot+=
                                    (service+setup+(t.current
                                            - event[g].arrival_time));

                            ev.type = 2;
                            ev.t = t.current + service + setup;
                            ev.x = 1;

                            // il job interrotto viene inserito
                            // nella struttura dati heap del cloud
                            cloud_event_heap.insert(ev);

                            statistics.arrival_type1_clet++;
                            statistics.arrivals_on_clet++;

                            service = em.getService1_clet(r, index);
                            statistics.sum_service_times1_clet += service;
                            statistics.sum_service_time_tot+=service;

                            // il job di classe 1 entra in servizio nel
                            // server liberato dal job di classe 2 interrotto
                            event[g].t = t.current+service;
                            event[g].x = 1;
                            event[g].type = 1;
                        }


                    } else{
                        // Si accetta il job sul Cloudlet

                        number++;
                        statistics.arrivals_on_clet++;

                        if(event[0].type == 1){
                            statistics.arrival_type1_clet++;
                            number1++;
                            service = em.getService1_clet(r, index);
                            statistics.sum_service_times1_clet += service;
                            statistics.sum_service_time_tot+=service;

                        }else{
                            statistics.arrival_type2_clet++;
                            number2++;
                            service = em.getService2_clet(r, index);
                        }

                        // si cerca il server idle da più tempo
                        s = em.findOne(event);

                        event[s].t = t.current + service;
                        event[s].x = 1;                  // il server diventa busy
                        event[s].type = event[0].type;
                        event[s].arrival_time = event[0].arrival_time;
                        event[s].service = service;


                    }


                } else {
                    // l'evento selezionato è una partenza dal Cloudlet

                    statistics.tot_processed++;
                    statistics.completitions_clet++;

                    number--;
                    s = e;

                    if(event[s].type == 1){

                        statistics.completition_clet_type1++;
                        number1--;

                    }else if(event[s].type == 2){
                        statistics.completition_clet_type2++;
                        statistics.sum_service_times2_clet += event[s].service;
                        statistics.sum_service_time_tot+=event[s].service;

                        number2--;
                    }

                    event[s].x = 0;
                    event[s].type = 0;
                }
            }

            end_time = t.current;
        }

        statistics.print_statistics(statistics.tot_arrived,
                statistics.tot_processed, end_time);

        statistics.initialize_statistics();
    }
}