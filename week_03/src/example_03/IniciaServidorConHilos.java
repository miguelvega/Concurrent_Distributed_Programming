package example_03;
public class IniciaServidorConHilos {
    public static void main(String[] args) {
        ServidorDeEcoConHilos server = new ServidorDeEcoConHilos();
        new Thread(server).start();
    }
}
