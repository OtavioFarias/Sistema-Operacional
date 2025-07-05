package multiThread.buddy;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

	public static void main(String[] args) {

	  int tamanhoTotal = Integer.parseInt(args[0]);
	  int blocoMinimo = Integer.parseInt(args[1]);
	  int numeroRequisicoes = Integer.parseInt(args[2]);
	  int numeroThreads = Integer.parseInt(args[3]);
	  double cleaningPercent = Double.parseDouble(args[4]);

	  Thread[] threads = new Thread[numeroThreads];

	  // Fila de blocos alocados compartilhada
	 	Queue<Integer> queueAlocadas = new LinkedList<>();
	
	  // Inicializa o BuddySystem
	  BuddySystem buddy = new BuddySystem(tamanhoTotal, blocoMinimo, cleaningPercent, queueAlocadas);

	  // Inicializa uma fila de requisições por thread
	  List<Queue<Integer>> filasPorThread = new ArrayList<>();
	  for (int i = 0; i < numeroThreads; i++) {
	      filasPorThread.add(new LinkedList<>());
	  }

	  // Distribui requisições entre as filas (round-robin)
	  for (int i = 0; i < numeroRequisicoes; i++) {
	      int tamanho = blocoMinimo + (i % 4) * blocoMinimo; // Ex: 32, 64, 96, 128...
	      int threadIndex = i % numeroThreads;
	      filasPorThread.get(threadIndex).add(tamanho);
	  }

	  long inicio = System.nanoTime();

	  // Cria e inicia as threads
	  for (int i = 0; i < numeroThreads; i++) {
	      threads[i] = new MinhaThread(buddy, filasPorThread.get(i), i);
	      threads[i].start();
	  }

		long fim = System.nanoTime();
    long tempoExecucaoNs = fim - inicio;
		
		/*
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("multiThreadResultados/resultados_" + args[2] + "_requisicoes.csv", true))) {
      writer.write(tamanhoTotal + "," + blocoMinimo + "," + numeroRequisicoes + "," +  numeroThreads + "," + cleaningPercent + "," + tempoExecucaoNs);
      writer.newLine(); // pula para a próxima linha

    }
    catch (IOException e) {
      e.printStackTrace();
  	}
  	*/
  	
  	System.out.println("Número threads: " + numeroThreads + " Tempo: " + tempoExecucaoNs);
	}
}