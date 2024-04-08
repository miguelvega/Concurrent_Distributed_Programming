package example_01;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
//Cliente
public class PruebaSocket {    
    public static void main(String[] args) {
        try {
            Socket s = new Socket("time-A.timefreq.bldrdoc.gov",13);
            //Socket s = new Socket("132.163.96.1",13);
            //Socket s = new Socket("127.0.0.1",8189);
            try {
                InputStream secuenciaDeEntrada = s.getInputStream();
                Scanner in = new Scanner(secuenciaDeEntrada);
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    System.out.println(line);                    
                }
            } finally{
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
}
