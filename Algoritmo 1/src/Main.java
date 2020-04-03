/*
    La classe Main viene invocata all'avvio dell'applicazione;
    in tale classe è possibile settare il numero di simulazioni
    da effettuare e il seed iniziale di ogni simulazione.
 */
public class Main {

    public static void main(String[] args) {

        Simulator simulator = new Simulator();

        for (int i = 0; i < 1; i++) {

            simulator.start_simulation(1, i * 6);
        }
    }
}
