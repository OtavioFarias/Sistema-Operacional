package singleThread.buddy;

import java.util.LinkedList;
import java.util.Queue;
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
		double cleaningPercent = Double.parseDouble(args[4]);
		
		Queue<Integer> queueRequisicoes = new LinkedList<Integer>();
		Queue<Integer> queueAlocadas = new LinkedList<Integer>();

		BuddySystem buddy = new BuddySystem(tamanhoTotal, blocoMinimo, cleaningPercent, queueAlocadas);
    
		 // Adiciona requisições na fila
      for (int i = 0; i <= numeroRequisicoes ; i++) {
          int tamanho = blocoMinimo + (i % 4) * blocoMinimo; // Ex: 32, 64, 96, 128, ...
          queueRequisicoes.add(tamanho);
      }
	
		long inicio = System.nanoTime();
	
		while(!queueRequisicoes.isEmpty()){
					Integer n = queueRequisicoes.poll();
					if (n == null){
						return;
					}
	
			    Integer addr = buddy.allocate(n);

			    while (addr == null) {
			    
			    		
								  buddy.freePercent();
			        
			       
			        addr = buddy.allocate(n);
			    }
					
			    //buddy.printTree(); // Se quiser visualizar
				}
		
		//buddy.printTree();
		long fim = System.nanoTime();
    long tempoExecucaoNs = fim - inicio;
		/*
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("singleThreadResultados/resultados_" + args[2] + "_requisicoes.csv", true))) {
      writer.write(tamanhoTotal + "," + blocoMinimo + "," + numeroRequisicoes + "," +  0 + "," + cleaningPercent + "," + tempoExecucaoNs);
      writer.newLine(); // pula para a próxima linha

    }
    catch (IOException e) {
      e.printStackTrace();
  	}*/
  	
  	System.out.println(" Tempo: " + tempoExecucaoNs/1000);
	
	}

}