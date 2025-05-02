package tools;
import tools.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Uso correto: java Main <tamanho_buffer> <min_requisicao> <max_requisicao> <num_requisicoes>");
            return;
        }

        int tamanhoBuffer = Integer.parseInt(args[0]);
        int min = Integer.parseInt(args[1]);
        int max = Integer.parseInt(args[2]);
        int numeroDeRequisicoes = Integer.parseInt(args[3]);
        int id = 0;
        int i = 0;

        // Criar buffer
        Buffer buffer = new Buffer(tamanhoBuffer);

        // Gerar requisições
        /*while(i < numeroDeRequisicoes){
            if(ThreadLocalRandom.current().nextInt(0, 2) == 1){

                for(int j = 0; j<ThreadLocalRandom.current().nextInt(min, max + 1); j++){
                    i++;
                    System.out.println();
                    System.out.println("Requisicão enviada no instante: " + i);
                    buffer.insert(new Requisicao(id, ThreadLocalRandom.current().nextInt(min, max + 1)));
                    id++;
                }
            }
        }
        */
        int requisicoesEnviadas = 0;
        int tempo = 0;

        while (requisicoesEnviadas < numeroDeRequisicoes) {
            if (ThreadLocalRandom.current().nextInt(0, 2) == 1) {
                int quantidade = ThreadLocalRandom.current().nextInt(min, max + 1);

                for (int j = 0; j < quantidade && requisicoesEnviadas < numeroDeRequisicoes; j++) {
                    System.out.println();
                    System.out.println("Requisição enviada no instante: " + tempo);
                    buffer.insert(new Requisicao(id, ThreadLocalRandom.current().nextInt(min, max + 1)));
                    id++;
                    requisicoesEnviadas++;
                }
            }
            tempo++; // Avança o tempo a cada iteração do while
        }

        System.out.println();
        buffer.printHeap();

    }
}
