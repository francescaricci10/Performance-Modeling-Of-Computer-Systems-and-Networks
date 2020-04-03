/*
    La classe CloudEvent presenta i metodi utili per la gestione
    delle statistiche relative al Cloud, in particolare per il
    conteggio del numero totale e medio dei job nel Cloud.
    Il metodo empty_cloud() viene invocato allo scadere del 'close
    the door time' per servire tutti i job rimasti nel Cloud
    prima di terminare la simulazione.
 */

public class CloudEvent {

    void record_cloud_jobs(Heap heap){

        int value = heap.isEmpty();
        int size = heap.size;
        int local_count1 = 0;
        int local_count2 = 0;

        Statistics statistics = new Statistics();
        statistics.cloud_count++;

        for (int i = 1; i <= size; i++) {
            if(heap.Heap[i].type==1){
                local_count1++;
            }else{
                local_count2++;
            }
        }
        statistics.tot_cloud_jobs_number += value;
        statistics.tot_cloud_jobs_number1 += local_count1;
        statistics.tot_cloud_jobs_number2 += local_count2;
    }


    float mean_cloud_jobs_number(){

        Statistics statistics = new Statistics();
        return((float) statistics.tot_cloud_jobs_number/ statistics.cloud_count);
    }

    float mean_cloud_jobs_number1(){

        Statistics statistics = new Statistics();
        return((float) statistics.tot_cloud_jobs_number1/ statistics.cloud_count);
    }

    float mean_cloud_jobs_number2(){

        Statistics statistics = new Statistics();
        return((float) statistics.tot_cloud_jobs_number2/ statistics.cloud_count);
    }

    float mean_cloud_jobs_number2_controller(){

        Statistics statistics = new Statistics();
        return((float) statistics.arrival_type2_cloud_fromController/ statistics.cloud_count);
    }





    void empity_cloud(Heap cloud_event_heap){

        Statistics statistics = new Statistics();
        EventManager em = new EventManager();

        while(cloud_event_heap.isEmpty() != 0){

            statistics.tot_processed++;
            statistics.completitions_cloud++;
            em.manage_departure_from_cloud(cloud_event_heap);
        }
    }
}
