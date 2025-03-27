// CÓDIGO FEITO POR MARCO ANTONIO DA CONCEIÇÃO E KAIO FELIPE SALDANHA
// LINGUAGENS FORMAIS E AUTOMATOS, PROFESSOR ALEX RESE

package buscaweb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class CapturaRecursosWeb {
    private ArrayList<String> listaRecursos = new ArrayList<String>();

    public ArrayList<String> carregarRecursos() {
        ArrayList<String> resultado = new ArrayList<String>();
        for (String stringURL : listaRecursos) {
            String resposta = "";

            try {
                // Use URI to create the URL object
                URI uri = new URI(stringURL);
                URL url = uri.toURL();
                URLConnection connection = url.openConnection();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()));

                String inputLine;

                StringBuilder sb = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine).append("\n");
                }
                resposta = sb.toString();
                resultado.add(resposta);
                in.close();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

    public ArrayList<String> getListaRecursos() {
        return listaRecursos;
    }
}