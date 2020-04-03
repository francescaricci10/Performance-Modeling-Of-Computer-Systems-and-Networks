/*
    La classe Statistics presenta tutte le variabili utilizzate
    per memorizzare e determinare le statistiche di interesse.
 */

import java.math.RoundingMode;
import java.text.NumberFormat;


public class Statistics {

    static int arrival_type;                   // tipo di arrivo (1 o 2)

    static int arrivals_on_clet = 0;           // n° di arrivi sul Cloudlet
    static int arrivals_on_cloud = 0;          // n° di arrivi sul Cloud

    static int arrival_type1_clet = 0;         // n° di arrivi di tipo 1 sul Cloudlet
    static int arrival_type2_clet = 0;         // n° di arrivi di tipo 2 sul Cloudlet
    static int arrival_type1_cloud = 0;        // n° di arrivi di tipo 1 sul Cloud
    static int arrival_type2_cloud = 0;        // n° di arrivi di tipo 2 sul Cloud

    static int completitions_clet = 0;         // n° di completamenti sul Cloudlet
    static int completitions_cloud = 0;        // n° di completamenti sul Cloud

    // n° di completamenti di tipo 1 sul Cloudlet
    static int completition_clet_type1 = 0;
    // n° di completamenti di tipo 2 sul Cloudlet
    static int completition_clet_type2 = 0;
    // n° di completamenti di tipo 1 sul Cloud
    static int completition_cloud_type1 = 0;
    // n° di completamenti di tipo 2 sul Cloud
    static int completition_cloud_type2 = 0;

    // somma dei tempi di servizio per job di tipo 1 sul Cloudlet
    static double sum_service_times1_clet = 0;
    // somma dei tempi di servizio per job di tipo 2 sul Cloudlet
    static double sum_service_times2_clet = 0;
    // somma dei tempi di servizio per job di tipo 1 sul Cloud
    static double sum_service_times1_cloud = 0;
    // somma dei tempi di servizio per job di tipo 2 sul Cloud
    static double sum_service_times2_cloud = 0;

    // Tipologia di evento da gestire: partenza da Cloud o arrivo al controller
    static int event_to_manage;
    // indice dell'evento del Cloudlet da gestire
    static int clet_event_index;

    static int cloud_count = 0;
    static int cloudlet_count = 0;
    static int tot_cloud_jobs_number = 0;
    static int tot_cloudlet_jobs_number = 0;

    static int tot_cloud_jobs_number1 = 0;
    static int tot_cloudlet_jobs_number1 = 0;

    static int tot_cloud_jobs_number2 = 0;
    static int tot_cloudlet_jobs_number2 = 0;

    static long tot_processed = 0;      // numero totale dei job processati
    static long tot_arrived = 0;        // numero totale degli arrivi al sistema





/*
    Questo metodo in izializza tutte le statistiche; viene utulizzato nel caso
    dell'analisi transiente, quando è necessario effettuare più simulazioni.
 */

    void initialize_statistics(){

        Simulator s = new Simulator();

        s.START = 0.0;
        s.STOP = 50000.0;
        s.SERVERS = 20;

        arrivals_on_clet = 0;
        arrivals_on_cloud = 0;

        arrival_type1_clet = 0;
        arrival_type2_clet = 0;
        arrival_type1_cloud = 0;
        arrival_type2_cloud = 0;

        completitions_clet = 0;
        completitions_cloud = 0;

        completition_clet_type1 = 0;
        completition_clet_type2 = 0;
        completition_cloud_type1 = 0;
        completition_cloud_type2 = 0;

        sum_service_times1_clet = 0;
        sum_service_times2_clet = 0;
        sum_service_times1_cloud = 0;
        sum_service_times2_cloud = 0;

        cloud_count = 0;
        cloudlet_count = 0;
        tot_cloud_jobs_number = 0;
        tot_cloudlet_jobs_number = 0;

        tot_cloud_jobs_number1 = 0;
        tot_cloudlet_jobs_number1 = 0;

        tot_cloud_jobs_number2 = 0;
        tot_cloudlet_jobs_number2 = 0;

        s.sarrival = s.START;
        s.sarrival1 = s.START;
        s.sarrival2 = s.START;

        tot_processed = 0;
        tot_arrived = 0;


    }






    /*
        Questo metodo calcola le statistiche finali di interesse
        e le stampa a schermo
     */

    void print_statistics(long tot_arrived, long tot_processed, Double end_time){

        CloudEvent cloudEvent = new CloudEvent();
        CloudletEvent cletEvent = new CloudletEvent();
        PrintFile print = new PrintFile();


        // System response time & throughput

        float system_response_time = (float)(sum_service_times1_clet+ sum_service_times1_cloud + sum_service_times2_clet+ sum_service_times2_cloud)/ tot_processed;
        float response_time_class1 = (float) ((sum_service_times1_clet+ sum_service_times1_cloud)/ (completition_clet_type1 + completition_cloud_type1));
        float response_time_class2 = (float) ((sum_service_times2_clet+ sum_service_times2_cloud)/ (completition_clet_type2 + completition_cloud_type2));

        float lambda_tot = (float) (tot_arrived/end_time);
        float lambda_1 = (float)((arrival_type1_clet+arrival_type1_cloud)/end_time);
        float lambda_2 = (float)((arrival_type2_clet+arrival_type2_cloud)/end_time);


        // per-class effective cloudlet throughput

        float task_rate_clet1 = (float) ((float)completition_clet_type1 / end_time);
        float task_rate_clet2 = (float) ((float)completition_clet_type2 / end_time);


        // per-class cloud throughput

        float lambda_cloud1 = (float) ((arrival_type1_cloud)/end_time);
        float lambda_cloud2 = (float) ((arrival_type2_cloud)/end_time);


        //  class response time and mean population

        float response_time_cloudlet = (float) (sum_service_times1_clet+ sum_service_times2_clet)/ (completition_clet_type1 + completition_clet_type2);
        float response_time_cloudlet1 = (float) sum_service_times1_clet/ completition_clet_type1;
        float response_time_cloudlet2 = (float) (sum_service_times2_clet/ completition_clet_type2);

        float response_time_cloud = (float) ((sum_service_times1_cloud+ sum_service_times2_cloud)/ (completition_cloud_type1 + completition_cloud_type2));
        float response_time_cloud1 = (float) (sum_service_times1_cloud/ completition_cloud_type1);
        float response_time_cloud2 = (float) (sum_service_times2_cloud/ completition_cloud_type2);

        // E[N] cloudlet
        float mean_cloudlet_jobs = cletEvent.mean_cloudlet_jobs_number();
        // E[N] class 1 cloudlet
        float mean_cloudlet_jobs1 = cletEvent.mean_cloudlet_jobs_number1();
        // E[N] class 2 cloudlet
        float mean_cloudlet_jobs2 = cletEvent.mean_cloudlet_jobs_number2();

        // E[N] cloud
        float mean_cloud_jobs = cloudEvent.mean_cloud_jobs_number();
        // E[N] class 1 cloud
        float mean_cloud_jobs1 = cloudEvent.mean_cloud_jobs_number1();
        // E[N] class 2 cloud
        float mean_cloud_jobs2 = cloudEvent.mean_cloud_jobs_number2();


        // Altre statistiche di interesse

        float lambda_clet = (float) ((arrival_type1_clet+ arrival_type2_clet)/end_time);
        float lambda_clet1 = (float) ((arrival_type1_clet)/end_time);
        float lambda_clet2 = (float) ((arrival_type2_clet)/end_time);
        float lambda_cloud = (float) ((arrival_type1_cloud+ arrival_type2_cloud)/end_time);

        double rate1_clet = mean_cloudlet_jobs1*0.45;
        double rate2_clet = mean_cloudlet_jobs2*0.27;



        NumberFormat numForm = NumberFormat.getInstance();
        numForm.setMinimumFractionDigits(6);
        numForm.setMaximumFractionDigits(6);
        numForm.setRoundingMode(RoundingMode.HALF_EVEN);


        System.out.println("\n\n\n 1) SYSTEM RESPONSE TIME & THROUGHPUT " + "(GLOBAL & PER-CLASS)");

        System.out.println("\n  Global System response time ...... =   " + numForm.format( system_response_time));
        System.out.println("  Response time class 1 ...... =   " + numForm.format( response_time_class1));
        System.out.println("  Response time class 2 ...... =   " + numForm.format( response_time_class2));

        System.out.println("\n  Global System throughput ...... =   " + numForm.format( lambda_tot));
        System.out.println("  Throughput class 1 ...... =   " + numForm.format( lambda_1));
        System.out.println("  Throughput class 2 ...... =   " + numForm.format( lambda_2));


        System.out.println("\n\n\n 2) PER_CLASS EFFECTIVE CLOUDLET THROUGHPUT ");
        System.out.println("  Task-rate cloudlet class 1 ...... =   " + numForm.format( rate1_clet));
        System.out.println("  Task-rate cloudlet class 2 ...... =   " + numForm.format( rate2_clet));


        System.out.println("\n\n\n 3) PER_CLASS CLOUD THROUGHPUT ");
        System.out.println("  Throughput cloud class 1 ...... =   " + numForm.format( lambda_cloud1));
        System.out.println("  Throughput cloud class 2 ...... =   " + numForm.format( lambda_cloud2));


        System.out.println("\n\n\n 4) CLASS RESPONSE TIME & MEAN POPULATION " + "(CLOUDLET & CLOUD)");

        System.out.println("\n  Response Time class 1 cloudlet ...... =   " + numForm.format( response_time_cloudlet1));
        System.out.println("  Response Time class 2 cloudlet ...... =   " + numForm.format(response_time_cloudlet2));
        System.out.println("  Response Time class 1 cloud ...... =   " + numForm.format( response_time_cloud1));
        System.out.println("  Response Time class 2 cloud ...... =   " + numForm.format( response_time_cloud2));

        System.out.println("\n  Mean Population class 1 cloudlet ...... =   " + numForm.format( mean_cloudlet_jobs1));
        System.out.println("  Mean Population class 2 cloudlet ...... =   " + numForm.format( mean_cloudlet_jobs2));
        System.out.println("  Mean Population class 1 cloud ...... =   " + numForm.format( mean_cloud_jobs1));
        System.out.println("  Mean Population class 2 cloud ...... =   " + numForm.format( mean_cloud_jobs2));

    }
}
