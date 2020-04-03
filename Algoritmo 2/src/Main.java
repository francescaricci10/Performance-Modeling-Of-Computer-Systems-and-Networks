import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Simulator simulator = new Simulator();

        for(int i = 0; i<1; i++) {

            simulator.start_simulation(123456789, i*6);

        }
    }

}
