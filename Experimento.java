import edu.princeton.cs.algs4.StopwatchCPU;
import edu.princeton.cs.algs4.Out;

public class Experimento {

    public static String generarXHTMLSintetico(int N) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n<html xmlns='http://...'>\n");
        for (int i = 0; i < N - 1; i++) sb.append("<div>\n");
        for (int i = 0; i < N - 1; i++) sb.append("</div>\n");
        sb.append("</html>");
        return sb.toString();
    }

    public static void main(String[] args) {
        int repeticiones = 100;
        int[] tamanios = {1024, 2048, 4096, 8192, 16384, 32768};

        System.out.println("Iniciando la recolección masiva de datos...");

        for (int N : tamanios) {
            System.out.println("Procesando tamaño N = " + N + " e imprimiendo CSV...");

            String paginaFalsa = generarXHTMLSintetico(N);

            Out archivoCSV = new Out("resultados_" + N + ".csv");

            archivoCSV.println("Repeticion,PilaPrinceton,PilaDeCola,ColaPrinceton,ColaDePilas");

            for (int i = 1; i <= repeticiones; i++) {

                Cola<String> colaBasura = new ColaPrinceton<>();

                WebCrawler crawlerP1 = new WebCrawler(new PilaPrinceton<>(), colaBasura);
                StopwatchCPU cronoP1 = new StopwatchCPU();
                crawlerP1.esXHTMLValido(paginaFalsa);
                double tiempoPilaPrinceton = cronoP1.elapsedTime();

                WebCrawler crawlerP2 = new WebCrawler(new PilaDeCola<>(), colaBasura);
                StopwatchCPU cronoP2 = new StopwatchCPU();
                crawlerP2.esXHTMLValido(paginaFalsa);
                double tiempoPilaDeCola = cronoP2.elapsedTime();

                Cola<String> colaPrinceton = new ColaPrinceton<>();
                StopwatchCPU cronoC1 = new StopwatchCPU();
                WebCrawler.simularCrawlerOffline(N, colaPrinceton);
                double tiempoColaPrinceton = cronoC1.elapsedTime();

                Cola<String> colaDePilas = new ColaDePilas<>();
                StopwatchCPU cronoC2 = new StopwatchCPU();
                WebCrawler.simularCrawlerOffline(N, colaDePilas);
                double tiempoColaDePilas = cronoC2.elapsedTime();

                archivoCSV.println(i + "," +
                        tiempoPilaPrinceton + "," +
                        tiempoPilaDeCola + "," +
                        tiempoColaPrinceton + "," +
                        tiempoColaDePilas);
            }

            archivoCSV.close();
        }

        System.out.println("\n¡Experimentos finalizados exitosamente");
    }
}