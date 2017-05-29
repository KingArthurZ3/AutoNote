package bin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class testWriter
{
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("out.txt");
        PrintWriter pw = new PrintWriter(fw);
        BufferedWriter bw =  new BufferedWriter(fw);
     
        for (int i = 0; i < 10; i++) {
            bw.append("something");

        }

        fw.close();
        
    }
}
