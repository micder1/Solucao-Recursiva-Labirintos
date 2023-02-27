
import java.util.*;
import java.io.*;

public class labirinto {
    public static void main(String[] args) throws IOException {

        /*
         * -----------------------------------------------------------------------------
         * -
         * leitura do arquivo
         * -----------------------------------------------------------------------------
         * -
         */

        //caminho do arquivo
        String arquivo = "/Users/Tulio/Desktop/tulio/ads/atp/trabalhoLabirinto/MAPA2DESAFIO2.txt";

        File lab = new File(arquivo);
        Scanner ler = new Scanner(lab);

        int qtdLinhas = contadorLinhas(arquivo);

        String[] vetLinhas = new String[qtdLinhas];
        int linha = 0;

        /*
         * -----------------------------------------------------------------------------
         * -
         * adiciona cada linha no vetor de string
         * -----------------------------------------------------------------------------
         * -
         */
        while (ler.hasNextLine()) {
            vetLinhas[linha] = ler.nextLine();
            linha++;
        }

        /*
         * -----------------------------------------------------------------------------
         * -
         * instancia da matriz
         * -----------------------------------------------------------------------------
         * -
         */
        char MatrizLab[][] = new char[vetLinhas.length][];

        /*
         * -----------------------------------------------------------------------------
         * -
         * pega o vetor de linhas, converte cada string em um vetor de
         * caracteres e adiciona cada caractere na matriz
         * -----------------------------------------------------------------------------
         * -
         */
        for (int i = 0; i < MatrizLab.length; i++) {
            MatrizLab[i] = vetLinhas[i].toCharArray();
        }

        System.out.print("print da matriz labirinto:");
        printaMatriz(MatrizLab);

        System.out.println();

        /*
         * -----------------------------------------------------------------------------
         * -
         * coordenadas do inicio:
         * -----------------------------------------------------------------------------
         * -
         */
        System.out.print("coordenadas do inicio: ");
        int[] vetCoordInicio = procuraInicio(MatrizLab);

        int coordLinha = vetCoordInicio[0];
        int coordColuna = vetCoordInicio[1];

        System.out.println(coordLinha + ", " + coordColuna);

        System.out.println();

        /*
         * -----------------------------------------------------------------------------
         * -
         * Chamada do método que resolve o labirinto
         * -----------------------------------------------------------------------------
         * -
         */

        System.out.println("Caminho percorrido: ");

        if (!testaCaminhos(MatrizLab, coordLinha, coordColuna, "")) {
            System.out.println("Não existe um caminho até a saída!");
        }

        System.out.println("\n\nprint do labirinto resolvido: ");
        printaMatriz(MatrizLab);

    }

    /*
     * -----------------------------------------------------------------------------
     * -
     * Método que printa a matriz labirinto
     * -----------------------------------------------------------------------------
     * -
     */
    public static void printaMatriz(char[][] matriz) {
        System.out.println();

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[1].length; j++) {
                System.out.print(matriz[i][j]);
            }
            System.out.println();
        }
    }

    /*
     * -----------------------------------------------------------------------------
     * -
     * Método que faz a contagem de linhas no arquivo do mapa
     * -----------------------------------------------------------------------------
     * -
     */
    public static int contadorLinhas(String arquivo) throws IOException {
        File labirinto = new File(arquivo);
        Scanner ler = new Scanner(labirinto);
        int contador = 0;
        String linhas;
        while (ler.hasNextLine()) {
            linhas = ler.nextLine();
            contador++;
        }
        return contador;
    }

    /*
     * -----------------------------------------------------------------------------
     * -
     * Método que acha e retorna as coordenadas do ponto start
     * -----------------------------------------------------------------------------
     * -
     */
    public static int[] procuraInicio(char[][] matrizLab) {
        int[] coord = new int[2];

        for (int i = 0; i < matrizLab.length; i++) {
            for (int j = 0; j < matrizLab[1].length; j++) {
                if (matrizLab[i][j] == 'S') {
                    coord[0] = i;
                    coord[1] = j;
                }
            }
        }
        return coord;
    }

    /*
     * -----------------------------------------------------------------------------
     * -
     * Método que checa se a posição atual na matriz labirinto é válida.
     * -----------------------------------------------------------------------------
     * -
     */
    public static boolean indexEValido(char[][] matrizLab, int linha, int coluna) {
        if ((linha >= 0 && linha < matrizLab.length) && (coluna >= 0 && coluna < matrizLab[1].length)) {
            if (matrizLab[linha][coluna] == ' ' || matrizLab[linha][coluna] == 'S' || matrizLab[linha][coluna] == 'E') {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /*
     * -----------------------------------------------------------------------------
     * -
     * Método que passa recursivamente pelos caminhos validos do labirinto
     * -----------------------------------------------------------------------------
     * -
     */
    public static boolean testaCaminhos(char[][] matrizLab, int linha, int coluna, String direcao) {
        // checa o index chamando o metodo indexEValido
        if (indexEValido(matrizLab, linha, coluna)) {
            // checa se o elemento no index é igual a E (saida)
            if (matrizLab[linha][coluna] == 'E') {
                String[] arrDirecao = direcao.split("");
                for (int i = 0; i < arrDirecao.length; i++) {
                    System.out.print(arrDirecao[i] + " ");
                }
                return true;
            }

            matrizLab[linha][coluna] = '+';

            // direita
            boolean novoIndice = testaCaminhos(matrizLab, linha, coluna + 1, direcao + "D");

            // baixo
            if (!novoIndice) {
                novoIndice = testaCaminhos(matrizLab, linha + 1, coluna, direcao + "B");
            }

            // esquerda
            if (!novoIndice) {
                novoIndice = testaCaminhos(matrizLab, linha, coluna - 1, direcao + "E");
            }

            // cima
            if (!novoIndice) {
                novoIndice = testaCaminhos(matrizLab, linha - 1, coluna, direcao + "C");
            }

            if (novoIndice) {
                matrizLab[linha][coluna] = '*';
            }
            return novoIndice;
        }
        return false;
    }
}