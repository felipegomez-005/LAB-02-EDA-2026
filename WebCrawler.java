import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
    private Pila<String> pila;
    private Cola<String> cola;

    public WebCrawler(Pila<String> pila, Cola<String> cola) {
        this.pila = pila;
        this.cola = cola;
    }

    public boolean esXHTMLValido(String xhtml) {
        if (!xhtml.contains("<!DOCTYPE") || !xhtml.contains("<html xmlns=")) {
            return false;
        }

        Pattern patron = Pattern.compile("<(/?[a-zA-Z0-9]+)[^>]*>");
        Matcher buscador = patron.matcher(xhtml);

        while (buscador.find()) {
            String etiqueta = buscador.group(1);

            if (etiqueta.toUpperCase().startsWith("!DOCTYPE")) {
                continue;
            }

            String nombreLimpio = etiqueta.replace("/", "");

            if (!nombreLimpio.equals(nombreLimpio.toLowerCase())) {
                return false;
            }

            if (!etiqueta.startsWith("/")) {
                pila.push(nombreLimpio);
            } else {
                if (pila.isEmpty()) return false;

                String ultimaAbierta = pila.pop();
                if (!ultimaAbierta.equals(nombreLimpio)) return false;
            }
        }

        return pila.isEmpty();
    }

    public boolean chequearURL(String url) {
        try {
            In in = new In(url);
            String codigoHTML = in.readAll();

            return esXHTMLValido(codigoHTML);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean chequeaURLs(String urlInicial, int limite) {
        SET<String> visitados = new SET<String>();

        cola.enqueue(urlInicial);
        visitados.add(urlInicial);

        boolean todasSonValidas = true;
        int paginasRevisadas = 0;

        Pattern patronLink = Pattern.compile("<a[^>]+href=[\"'](http[^\"']+)[\"']");

        while (!cola.isEmpty() && paginasRevisadas < limite) {
            String urlActual = cola.dequeue();
            paginasRevisadas++;

            System.out.println("Revisando (" + paginasRevisadas + "/" + limite + "): " + urlActual);

            boolean esValida = chequearURL(urlActual);
            if (!esValida) {
                todasSonValidas = false;
            }

            try {
                In in = new In(urlActual);
                String html = in.readAll();
                Matcher buscador = patronLink.matcher(html);

                while (buscador.find()) {
                    String nuevoLink = buscador.group(1);
                    if (!visitados.contains(nuevoLink)) {
                        visitados.add(nuevoLink);
                        cola.enqueue(nuevoLink);
                    }
                }
            } catch (IllegalArgumentException e) {
            }
        }
        return todasSonValidas;
    }

    public static void main(String[] args) {
        Pila<String> miPila = new PilaPrinceton<>();
        Cola<String> miCola = new ColaPrinceton<>();

        WebCrawler crawler = new WebCrawler(miPila, miCola);

        System.out.println("Iniciando escaneo en UDP...");
        crawler.chequeaURLs("https://eit.udp.cl/", 100);
    }
}
