public class Perceptron {
    public static void main(String args[]) {
        int N_Entrada = 2;          // dois neurônios de entrada
        int N_Ocultos = 2;          // dois neurônios ocultos
        int N_Saida = 1;          // um neurônio de saída
        double TX_Aprendizado = 0.8;   // Definir taxa de aprendizado

        double Custo = 100;        // Inicializa a função de custo com um valor grande para inserir a instrução while abaixo
        double limiarCusto = 0.01 ;   // Função de custo a ser usada ao parar o programa
        int contador = 0;      // // Inicializa o contador de repetição

        // Etapa I-2
        // Define o peso e o limite da conexão para um valor real aleatório de -0,5 a 0,5
        double[][] wJi = new double[2][3];
        double[][] wKj = new double[1][3];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                wJi[i][j] = (Math.random()) - 0.5; //Valores aleatorios entre -0,5 e 0,5
            }
        }
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                wKj[i][j] = (Math.random()) - 0.5; //Valores aleatorios entre -0,5 e 0,5
            }
        }

        double[] netPj = new double[N_Ocultos];
        double[] oPj = new double[N_Ocultos + 1];
        double[] netPk = new double[N_Saida];
        double[] oPk = new double[N_Saida];
        double[] eP = new double[4];
        double[] deltaPk = new double[N_Saida];
        double[] deltaPj = new double[N_Ocultos];

        while (Custo > limiarCusto) {
            int j = 0;

            contador++;
            Custo = 0;          // Inicialize a função de custo para 0
            int[][] X = {
            			 {0, 0, -1}, 
            		     {0, 1, -1}, // XOR de entrada
            		     {1, 0, -1}, 
            		     {1, 1, -1}
            		    };   
            
            int[] D = {0, 1, 1, 0};  // Valor do resultado

            for (int p = 0; p < 4; p++) {
                for (j = 0; j < N_Ocultos; j++) { //sempre 0 ou 1
                    netPj[j] = 0; 
                    for (int i = 0; i < N_Entrada + 1; i++) {
                        netPj[j] = netPj[j] + wJi[j][i] * X[p][i]; //USA X
                    }
                    oPj[j] = 1 / (1 + Math.exp(-netPj[j]));
                }
                oPj[N_Ocultos] = -1;        // Configure para aprender o limite do nó de saída

                for (int k = 0; k < N_Saida; k++) {
                    netPk[k] = 0;
                    for (j = 0; j < N_Ocultos + 1; j++) {
                        netPk[k] = netPk[k] + wKj[k][j] * oPj[j];
                    }
                    oPk[k] = 1 / (1 + Math.exp(-netPk[k]));
                }
                eP[p] = 0;

                for (int k = 0; k < N_Saida; k++) {
                    deltaPk[k] = (D[p] - oPk[k]) * oPk[k] * (1 - oPk[k]);
                    eP[p] = eP[p] + 0.5 * Math.pow((D[p] - oPk[k]), 2);
                }

                Custo = Custo + eP[p];

                for (j = 0; j < N_Ocultos; j++) {
                    deltaPj[j] = 0;
                    for (int k = 0; k < N_Saida; k++) {
                        deltaPj[j] = deltaPj[j] + deltaPk[k] * wKj[k][j] * oPj[j] * (1 - oPj[j]);
                    }
                }

                for (int k = 0; k < N_Saida; k++) {
                    for (j = 0; j < N_Ocultos + 1; j++) {
                        wKj[k][j] = wKj[k][j] + TX_Aprendizado * deltaPk[k] * oPj[j];
                    }
                }

                for (j = 0; j < N_Ocultos; j++) {
                    for (int i = 0; i < N_Entrada + 1; i++) {
                        wJi[j][i] = wJi[j][i] + TX_Aprendizado * deltaPj[j] * X[p][i]; // USA X
                    }
                }
            }
            System.out.println("Count : " + contador + " / Alteração no erro: " + Custo);
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