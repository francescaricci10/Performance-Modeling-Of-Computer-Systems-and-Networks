/*
    La classe PrintFile viene utilizzata epr stampare le
    statistiche di interesse in file di testo.
 */


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class PrintFile {


    // Stampa la variabile passata alla funzione in un file di testo
    void write_output(double time){

        try(FileWriter fw = new FileWriter("../Algoritmo1/txt/output.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(Double.toString(time));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Stampa il carattere di demarcazione ";" (necessario
    // l'applicazione del metodo dei Batch Means)
    void print_space(){

        try(FileWriter fw = new FileWriter("../Algoritmo1/txt/output.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(";");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
