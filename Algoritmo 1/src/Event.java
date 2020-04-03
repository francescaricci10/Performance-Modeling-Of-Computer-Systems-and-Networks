/*
    La classe Event rappresenta gli eventi che
    possono alterare lo stato del sistema. Ogni
    evento presenta tre variabili: un tempo di
    'next-event', uno stato relativo al server
    che gestisce l'evento, e un tipo (1 o 2 in
    base alla classe del job).
 */


class Event {

    double t;               //   next event time
    int    x;               //   event status, 0 or 1
    int type;               //   event type, 1 or 2
}