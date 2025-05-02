package tools;
import tools.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Uso correto: java Main <tamanho_buffer> <min_requisicao> <max_requisicao> <num_requisicoes> <garbagePercent>");
            return;
        }

        int tamanhoBuffer = Integer.parseInt(args[0]);
        int min = Integer.parseInt(args[1]);
        int max = Integer.parseInt(args[2]);
        int numeroDeRequisicoes = Integer.parseInt(args[3]);
        int garbagePercent = Integer.parseInt(args[4]);
        int id = 0;

        // Criar buffer
        Buffer buffer = new Buffer(tamanhoBuffer, garbagePercent);

        int requisicoesEnviadas = 0;

        while (requisicoesEnviadas < numeroDeRequisicoes) {

            System.out.println();
            buffer.insert(new Requisicao(id, ThreadLocalRandom.current().nextInt(min, max + 1)));
            id++;
            requisicoesEnviadas++;

        }


        System.out.println();
        buffer.printHeap();
        System.out.println();

    }
}
