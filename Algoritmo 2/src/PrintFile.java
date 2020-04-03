import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PrintFile {

    void write_output(Float val){

        try(FileWriter fw = new FileWriter("../Algoritmo2/txt/output.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(Float.toString(val));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    void print_space(){

        try(FileWriter fw = new FileWriter("../Algoritmo2/txt/output.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(";");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
