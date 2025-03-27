// CÓDIGO FEITO POR MARCO ANTONIO DA CONCEIÇÃO E KAIO FELIPE SALDANHA
// LINGUAGENS FORMAIS E AUTOMATOS, PROFESSOR ALEX RESE

package buscapadraoweb;

import buscaweb.CapturaRecursosWeb;
import java.util.ArrayList;

public class Main {

    // busca char em vetor e retorna indice
    public static int get_char_ref(char[] vet, char ref) {
        for (int i = 0; i < vet.length; i++) {
            if (vet[i] == ref) {
                return i;
            }
        }
        return -1;
    }

    // busca string em vetor e retorna indice
    public static int get_string_ref(String[] vet, String ref) {
        for (int i = 0; i < vet.length; i++) {
            if (vet[i].equals(ref)) {
                return i;
            }
        }
        return -1;
    }

    // retorna o próximo estado, dado o estado atual e o símbolo lido
    public static int proximo_estado(char[] alfabeto, int[][] matriz, int estado_atual, char simbolo) {
        int simbol_indice = get_char_ref(alfabeto, simbolo);
        if (simbol_indice != -1) {
            return matriz[estado_atual][simbol_indice];
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        // instancia e usa objeto que captura código-fonte de páginas Web
        CapturaRecursosWeb crw = new CapturaRecursosWeb();
        crw.getListaRecursos().add("http://127.0.0.1:5500/teste_container.html");
        ArrayList<String> listaCodigos = crw.carregarRecursos();

        String codigoHTML = listaCodigos.get(0);

        // mapa do alfabeto (letras maiúsculas, números e hífen)
        char[] alfabeto = new char[37];
        int index = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            alfabeto[index++] = c;
        }
        for (char c = '0'; c <= '9'; c++) {
            alfabeto[index++] = c;
        }
        alfabeto[index++] = '-'; // Adiciona o hífen ao alfabeto

        // mapa de estados
        String[] estados = new String[12];
        estados[0] = "q0"; // estado inicial
        estados[1] = "q1"; // primeiro A
        estados[2] = "q2"; // segundo A
        estados[3] = "q3"; // terceiro A
        estados[4] = "q4"; // quarto A
        estados[5] = "q5"; // primeiro 9
        estados[6] = "q6"; // segundo 9
        estados[7] = "q7"; // terceiro 9
        estados[8] = "q8"; // quarto 9
        estados[9] = "q9"; // quinto 9
        estados[10] = "q10"; // sexto 9
        estados[11] = "q11"; // dígito verificador

        String estado_inicial = "q0";

        // estados finais
        String[] estados_finais = new String[1];
        estados_finais[0] = "q11";

        // tabela de transição de AFD para reconhecimento do padrão AAAA999999-9
        int[][] matriz = new int[12][37];

        // transições de q0 (estado inicial) para q1 (primeiro A)
        for (int i = 0; i < 26; i++) {
            matriz[get_string_ref(estados, "q0")][i] = get_string_ref(estados, "q1");
        }

        // transições de q1 (primeiro A) para q2 (segundo A)
        for (int i = 0; i < 26; i++) {
            matriz[get_string_ref(estados, "q1")][i] = get_string_ref(estados, "q2");
        }

        // transições de q2 (segundo A) para q3 (terceiro A)
        for (int i = 0; i < 26; i++) {
            matriz[get_string_ref(estados, "q2")][i] = get_string_ref(estados, "q3");
        }

        // transições de q3 (terceiro A) para q4 (quarto A)
        for (int i = 0; i < 26; i++) {
            matriz[get_string_ref(estados, "q3")][i] = get_string_ref(estados, "q4");
        }

// matriz[get_string_ref(estados, "q3")][get_char_ref(alfabeto, 'U')] = get_string_ref(estados, "q4");
// matriz[get_string_ref(estados, "q3")][get_char_ref(alfabeto, 'B')] = get_string_ref(estados, "q4");
// matriz[get_string_ref(estados, "q3")][get_char_ref(alfabeto, 'V')] = get_string_ref(estados, "q4");

        // transições de q4 (quarto A) para q5 (primeiro 9)
        for (int i = 26; i < 36; i++) {
            matriz[get_string_ref(estados, "q4")][i] = get_string_ref(estados, "q5");
        }

        // transições de q5 (primeiro 9) para q6 (segundo 9)
        for (int i = 26; i < 36; i++) {
            matriz[get_string_ref(estados, "q5")][i] = get_string_ref(estados, "q6");
        }

        // transições de q6 (segundo 9) para q7 (terceiro 9)
        for (int i = 26; i < 36; i++) {
            matriz[get_string_ref(estados, "q6")][i] = get_string_ref(estados, "q7");
        }

        // transições de q7 (terceiro 9) para q8 (quarto 9)
        for (int i = 26; i < 36; i++) {
            matriz[get_string_ref(estados, "q7")][i] = get_string_ref(estados, "q8");
        }

        // transições de q8 (quarto 9) para q9 (quinto 9)
        for (int i = 26; i < 36; i++) {
            matriz[get_string_ref(estados, "q8")][i] = get_string_ref(estados, "q9");
        }

        // transições de q9 (quinto 9) para q10 (sexto 9)
        for (int i = 26; i < 36; i++) {
            matriz[get_string_ref(estados, "q9")][i] = get_string_ref(estados, "q10");
        }

        // transições de q10 (sexto 9) para q11 (dígito verificador)
        matriz[get_string_ref(estados, "q10")][get_char_ref(alfabeto, '-')] = get_string_ref(estados, "q11");

// Transições de q11 (dígito verificador) para números
for (int i = 26; i < 36; i++) {
    if (alfabeto[i] >= '0' && alfabeto[i] <= '9') {
        matriz[get_string_ref(estados, "q11")][i] = get_string_ref(estados, "q11"); // Mantém no mesmo estado para números
    } else {
        matriz[get_string_ref(estados, "q11")][i] = -1; // Não aceita outros caracteres
    }
}

        int estado = get_string_ref(estados, estado_inicial);
        int estado_anterior = -1;
        ArrayList<String> palavras_reconhecidas = new ArrayList<String>();

        String palavra = "";

       // varre o código-fonte de um código
for (int i = 0; i < codigoHTML.length(); i++) {

    estado_anterior = estado;
    estado = proximo_estado(alfabeto, matriz, estado, codigoHTML.charAt(i));
    // se o não há transição
    if (estado == -1) {
        // pega estado inicial
        estado = get_string_ref(estados, estado_inicial);
        // se o estado anterior foi um estado final
        if (get_string_ref(estados_finais, estados[estado_anterior]) != -1) {
            // Verifica se a palavra tem um número após o hífen
            if (!palavra.equals("") && palavra.contains("-") && Character.isDigit(palavra.charAt(palavra.length() - 1))) {
                palavras_reconhecidas.add(palavra);
            }
            // se ao analisar este caracter não houve transição
            // teste-o novamente, considerando que o estado seja inicial
            i--;
        }
        // zera palavra
        palavra = "";

    } else {
        // se houver transição válida, adiciona caracter a palavra
        palavra += codigoHTML.charAt(i);
    }
}

// foreach no Java para exibir todas as palavras reconhecidas
for (String p : palavras_reconhecidas) {
    System.out.println(p);
}
    }
}