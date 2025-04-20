package tools;
import tools.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Uso correto: java Main <tamanho_buffer> <min_requisicao> <max_requisicao> <num_requisicoes>");
            return;
        }

        int tamanhoBuffer = Integer.parseInt(args[0]);
        int minRequisicao = Integer.parseInt(args[1]);
        int maxRequisicao = Integer.parseInt(args[2]);
        int numeroDeRequisicoes = Integer.parseInt(args[3]);

        // Criar buffer
        Buffer buffer = new Buffer(tamanhoBuffer);

        // Gerar requisições
        GerarRequisicoes gerador = new GerarRequisicoes();
        gerador.GerarRequisicao(numeroDeRequisicoes, minRequisicao, maxRequisicao);
        List<Requisicao> listaRequisicoes = gerador.gerar();

        // Converter lista para array
        Requisicao[] requisicoes = listaRequisicoes.toArray(new Requisicao[0]);

        // Criar produtor e consumidor
        Produtor produtor = new Produtor(requisicoes, buffer);
        Consumidor consumidor = new Consumidor(buffer);

        // Criar e iniciar threads
        Thread threadProdutor = new Thread(() -> produtor.run());
        Thread threadConsumidor = new Thread(() -> consumidor.run());

        threadProdutor.start();
        threadConsumidor.start();
    }
}
