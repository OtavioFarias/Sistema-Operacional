package multiThread.buddy;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main{

	public static void main(String[] args){
	
		int tamanhoTotal = Integer.parseInt(args[0]);
		int blocoMinimo =  Integer.parseInt(args[1]);
		int numeroRequisicoes = Integer.parseInt(args[2]);
		int numeroThreads = Integer.parseInt(args[3]);
		double cleaningPercent = Double.parseDouble(args[4]);
		
		Thread[] thread = new Thread[numeroThreads];
		
		Queue<Integer> queueRequisicoes = new ConcurrentLinkedQueue<>();
		Queue<Integer> queueAlocadas = new ConcurrentLinkedQueue<>();

		BuddySystem buddy = new BuddySystem(tamanhoTotal, blocoMinimo, cleaningPercent, queueAlocadas);
	
		 // Adiciona requisições na fila
      for (int i = 0; i <=  numeroRequisicoes; i++) {
          int tamanho = blocoMinimo + (i % 4) * blocoMinimo; // Ex: 32, 64, 96, 128, ...
          queueRequisicoes.add(tamanho);
      }
	
	
		long inicio = System.nanoTime();
	
		for(int i = 0; i < numeroThreads; i++){
			
			thread[i] = new MinhaThread(buddy, queueRequisicoes, i);	
			thread[i].start();
			
		}
		
		long fim = System.nanoTime();
    long tempoExecucaoNs = fim - inicio;
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("../multiThreadResultados/resultados_" + args[2] + "_requisicoes.csv", true))) {
      writer.write(tamanhoTotal + "," + blocoMinimo + "," + numeroRequisicoes + "," +  numeroThreads + "," + cleaningPercent + "," + tempoExecucaoNs);
      writer.newLine(); // pula para a próxima linha

    }
    catch (IOException e) {
      e.printStackTrace();
  	}
	}
}