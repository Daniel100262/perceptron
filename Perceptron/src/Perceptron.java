public class Perceptron {
    public static void main(String args[]) {
        int N = 2;          // dois neurônios de entrada
        int H = 2;          // dois neurônios ocultos
        int M = 1;          // um neurônio de saída
        double eta = 0.2;   // Definir taxa de aprendizado

        double E = 100;        // Inicialize a função de custo com um valor grande para inserir a instrução while abaixo
        double Es = 0.01 ;   // Função de custo a ser usada ao parar o programa
        int count = 0;      // // Inicializa o contador de repetição

        // Etapa I-2
        // Defina o peso e o limite da conexão para um valor real aleatório de -0,5 a 0,5
        double wJi[][] = new double[2][3];
        double wKj[][] = new double[1][3];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                wJi[i][j] = (Math.random()) - 0.5;
            }
        }
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                wKj[i][j] = (Math.random()) - 0.5;
            }
        }

        double netPj[] = new double[H];
        double oPj[] = new double[H + 1];
        double netPk[] = new double[M];
        double oPk[] = new double[M];
        double eP[] = new double[4];
        double deltaPk[] = new double[M];
        double deltaPj[] = new double[H];

        while (E > Es) {
            int j = 0;

            count = count + 1;
            E = 0;          // Inicialize a função de custo para 0
            int[][] X = {{0, 0, -1}, {0, 1, -1}, {1, 0, -1}, {1, 1, -1}};   // Valor de entrada
            int[] D = {0, 1, 1, 0};                                         // valor alvo

            for (int p = 0; p < 4; p++) {
                for (j = 0; j < H; j++) {
                    netPj[j] = 0;
                    for (int i = 0; i < N + 1; i++) {
                        netPj[j] = netPj[j] + wJi[j][i] * X[p][i];
                    }
                    oPj[j] = 1 / (1 + Math.exp(-netPj[j]));
                }
                oPj[H] = -1;        // Configure para aprender o limite do nó de saída

                for (int k = 0; k < M; k++) {
                    netPk[k] = 0;
                    for (j = 0; j < H + 1; j++) {
                        netPk[k] = netPk[k] + wKj[k][j] * oPj[j];
                    }
                    oPk[k] = 1 / (1 + Math.exp(-netPk[k]));
                }
                eP[p] = 0;

                for (int k = 0; k < M; k++) {
                    deltaPk[k] = (D[p] - oPk[k]) * oPk[k] * (1 - oPk[k]);
                    eP[p] = eP[p] + 0.5 * Math.pow((D[p] - oPk[k]), 2);
                }

                E = E + eP[p];

                for (j = 0; j < H; j++) {
                    deltaPj[j] = 0;
                    for (int k = 0; k < M; k++) {
                        deltaPj[j] = deltaPj[j] + deltaPk[k] * wKj[k][j] * oPj[j] * (1 - oPj[j]);
                    }
                }

                for (int k = 0; k < M; k++) {
                    for (j = 0; j < H + 1; j++) {
                        wKj[k][j] = wKj[k][j] + eta * deltaPk[k] * oPj[j];
                    }
                }

                for (j = 0; j < H; j++) {
                    for (int i = 0; i < N + 1; i++) {
                        wJi[j][i] = wJi[j][i] + eta * deltaPj[j] * X[p][i];
                    }
                }
            }
            System.out.println("Count : " + count + " / Alteração no erro: " + E);
        }
        System.out.println();
        System.out.println("Peso do perceptron para aprender a operação do XOR corretamente ");
        System.out.println("========== w_ij output (peso da conexão entre a camada oculta e a camada de entrada) ==========");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("w" + i + "" + j + " = " + wJi[i][j] + " / ");
            }
        }

        System.out.println();
        System.out.println("========== saída w_jk (peso da conexão entre a camada oculta e a camada de saída) ==========");

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("w" + i + "" + j + " = " + wKj[i][j] + " / ");
            }
        }
        System.out.println();
    }
}