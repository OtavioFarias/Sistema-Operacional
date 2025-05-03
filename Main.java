package tools;
import tools.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        if (args.length != 6) {
            System.out.println("Uso correto: java Main <tamanho_buffer> <min_requisicao> <max_requisicao> <num_requisicoes> <garbagePercent> <tamanhoBloco>");
            return;
        }

        int tamanhoBuffer = Integer.parseInt(args[0]);
        int min = Integer.parseInt(args[1]);
        int max = Integer.parseInt(args[2]);
        int numeroDeRequisicoes = Integer.parseInt(args[3]);
        int garbagePercent = Integer.parseInt(args[4]);
        int bloco = Integer.parseInt(args[5]);
        int id = 0;

        if(garbagePercent > 100 || garbagePercent < 0) garbagePercent = 30;
        if(bloco < 4) bloco = 4;
        if(min < 16) min = 16;
        if(tamanhoBuffer < min) tamanhoBuffer = min + 1;
        if(max > tamanhoBuffer) max = tamanhoBuffer;
        if(max < min) max = min + 1;
        if(bloco > tamanhoBuffer) bloco = tamanhoBuffer;


        // Criar buffer
        Buffer buffer = new Buffer(tamanhoBuffer, garbagePercent);

        int requisicoesEnviadas = 1;

        long startTime = System.nanoTime();

        while (requisicoesEnviadas < numeroDeRequisicoes) {

            System.out.println();
            buffer.insert(new Requisicao(id, ThreadLocalRandom.current().nextInt(min, max + 1), bloco));
            id++;
            requisicoesEnviadas++;

        }

        long endTime = System.nanoTime();

        System.out.println();
        buffer.printHeap();
        System.out.println();

        long duration = (endTime - startTime);

        CsvLogger.salvarDados(tamanhoBuffer, min, max, numeroDeRequisicoes, buffer.numCompac, buffer.numRemocao, garbagePercent, bloco, duration);
    }
}
