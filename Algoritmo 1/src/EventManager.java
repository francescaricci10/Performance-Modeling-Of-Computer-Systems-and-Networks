public class EventManager {


    /* Generazione di una variabile esponenziale random */

    double exponential(double m, Rngs r) {

        return (-m * Math.log(1.0 - r.random()));
    }

    /* Generazione del tempo di arrivo di un job di classe 1 con tasso 4 task/s */

    double getArrival1(Rngs r, int i) {

        Simulator m = new Simulator();

        r.selectStream(i);
        m.sarrival1 = exponential(0.25, r);
        return (m.sarrival1);
    }


    /* Generazione del tempo di arrivo di un job di classe 2 con tasso 6.25 task/s */

    double getArrival2(Rngs r, int i) {

        Simulator m = new Simulator();

        r.selectStream(i+1);
        m.sarrival2 = exponential(0.16, r);
        return (m.sarrival2);
    }


    /* Generazione del tempo di servizio di un job di classe 1
       nel Cloudlet con tasso 0.45 task/s                      */

    double getService1_clet(Rngs r, int i) {

        r.selectStream(i+2);
        return (exponential(2.222222, r));
    }


    /* Generazione del tempo di servizio di un job di classe 2
       nel Cloudlet con tasso 0.27 task/s                      */

    double getService2_clet(Rngs r, int i) {

        r.selectStream(i+3);
        return (exponential(3.703704, r));
    }


    /* Generazione del tempo di servizio di un job di classe 1
       nel Cloud con tasso 0.25 task/s                         */

    double getService1_cloud(Rngs r, int i) {

        r.selectStream(i+4);
        return (exponential(4, r));
    }



    /* Generazione del tempo di servizio di un job di classe 2
       nel Cloud con tasso 0.22 task/s                         */

    double getService2_cloud(Rngs r, int i) {

        r.selectStream(i+5);
        return (exponential(4.545455, r));
    }


    /*
       Restitiusce l'indice del prossimo evento se l'evento
       è un arrivo o una partenza dal Cloudlet, altrimenti
       pone la variabile 'event_to_manage' a 2 ad indicare
       che l'evento sarà una partenza dal Cloud.
    */
    void nextEvent(Event[] event, Heap heap) {

        int e;
        int i = 0;
        double time;
        Statistics statistics = new Statistics();
        Simulator simulator = new Simulator();

        /* Si cerca l'indice del primo elemento
         "attivo" nella lista degli eventi */
        while (event[i].x == 0) {
            i++;
            if(i == simulator.SERVERS+1){
                break;
            }
        }
        e = i;
        time = event[e].t;

        /* si controllano gli altri elementi della lista
         per verificare se vi sono altri eventi più imminenti */
        while (i < simulator.SERVERS) {
            i++;
            if ((event[i].x == 1) && (event[i].t < event[e].t))
                e = i;
            time = event[e].t;
        }

        // Si controlla che l'heap degli eventi del Cloud sia non vuoto
        int size = heap.isEmpty();
        if(size!= 0){
            Event cloud_event = heap.root_event();

            /* Si confronta il tempo di 'next-event' dell'elemento root
            con il tempo dell'evento del Cloudlet selezionato precedentemente */

            if(time<cloud_event.t){
                // l'evento verrà gestitio nel Cloudlet
                statistics.event_to_manage=1;
                statistics.clet_event_index=e;
            } else{
                // l'evento verrà gestitio nel Cloud
                statistics.event_to_manage=2;
            }
        }else{
            statistics.event_to_manage=1;
            statistics.clet_event_index=e;
        }
    }


    /*
        Il metodo gestisce una partenza dal Cloud eliminando l'elemento
        root dall'heap di eventi; vengono poi aggiornate le statistiche
        relative ai job completati nel Cloud.
    */
    void manage_departure_from_cloud(Heap heap){

        Event cloud_event = new Event();
        Statistics statistics = new Statistics();

        if(heap.isEmpty() != 0){

            cloud_event = heap.remove();

            if(cloud_event.type==1){
                statistics.completition_cloud_type1++;
            }else{
                statistics.completition_cloud_type2++;
            }
        }
    }



    // Restituisce l'indice del server disponibile che è idle da più tempo.

    int findOne(Event[] event) {

        int s;
        int i = 1;
        Simulator m = new Simulator();

        // Si cerca l'indice del primo server disponibile (idle)
        while (event[i].x == 1)
            i++;
        s = i;
        // Si controllano gli altri server per cercare quello idle da più tempo
        while (i < m.SERVERS) {
            i++;
            if ((event[i].x == 0) && (event[i].t < event[s].t))
                s = i;
        }
        return (s);
    }



    // Il metodo stampa la lista di eventi relativa al Cloudlet

    void print_event_list(Event[] e){

        Simulator m = new Simulator();

        for(int i=0; i<m.SERVERS+1; i++){
            System.out.println("[" + i + "]     time : " + e[i].t +
                    "    status : " + e[i].x + "    type : " + e[i].type);
        }
    }

}
