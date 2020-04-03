/*
    La classe CloudletEvent presenta i metodi utili per la gestione
    delle statistiche relative al Cloudlet, in particolare per il
    conteggio del numero totale e medio dei job nel Cloudlet.
 */

public class CloudletEvent {


    void record_cloudlet_jobs(Event [] event){

        int local_count = 0;
        int local_count1 = 0;
        int local_count2 = 0;

        Statistics statistics = new Statistics();
        Simulator simulator = new Simulator();

        statistics.cloudlet_count++;

        for(int i=1; i<simulator.SERVERS+1; i++){
            if(event[i].x==1){
                local_count++;
                if(event[i].type==1){
                    local_count1++;
                }else{
                    local_count2++;
                }
            }
        }
        statistics.tot_cloudlet_jobs_number += local_count;
        statistics.tot_cloudlet_jobs_number1 += local_count1;
        statistics.tot_cloudlet_jobs_number2 += local_count2;

    }


    float mean_cloudlet_jobs_number(){

        Statistics statistics = new Statistics();
        return((float) statistics.tot_cloudlet_jobs_number/statistics.cloudlet_count);
    }

    float mean_cloudlet_jobs_number1(){

        Statistics statistics = new Statistics();
        return((float) statistics.tot_cloudlet_jobs_number1/statistics.cloudlet_count);
    }

    float mean_cloudlet_jobs_number2(){

        Statistics statistics = new Statistics();
        return((float) statistics.tot_cloudlet_jobs_number2/statistics.cloudlet_count);
    }


}
