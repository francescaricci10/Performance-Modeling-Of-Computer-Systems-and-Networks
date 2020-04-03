/*
    La classe Event rappresenta gli eventi che
    possono alterare lo stato del sistema. Ogni
    evento presenta cinque variabili: un tempo di
    'next-event', lo stato relativo al server
    che gestisce l'evento, il tipo (1 o 2 in
    base alla classe del job), il tempo di
    arrivo (utilizzato per calcolare il tempo
    di permanenza nel cloudlet dei job interrotti),
    il tempo di servizio.
 */

class Event {
    double t;                //  next event time
    int    x;                //  event status, 0 or 1
    int type;                //  event type, 1 or 2
    double arrival_time;     //  tempo di arrivo
    double service;          //  tempo di servizio
}