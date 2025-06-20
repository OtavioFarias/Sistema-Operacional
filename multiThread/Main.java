package buddy;

import buddy.MinhaThread;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.ArrayList;

public class Main{

	public static void main(String[] args){
	
		int numeroThreads = Integer.parseInt(args[0]);
		Thread[] thread = new Thread[numeroThreads];
		
		Queue<Integer> queueRequisicoes = new ConcurrentLinkedQueue<>();
		Queue<Integer> queueAlocadas = new ConcurrentLinkedQueue<>();

		BuddySystem buddy = new BuddySystem(Integer.parseInt(args[0]) , Integer.parseInt(args[1]) , Double.parseDouble(args[3]));
	
		 // Adiciona requisições na fila
      for (int i = 0; i <= Integer.parseInt(args[2]) ; i++) {
          int tamanho = Integer.parseInt(args[1]) + (i % 4) * Integer.parseInt(args[1]); // Ex: 32, 64, 96, 128, ...
          queueRequisicoes.add(tamanho);
      }
	
	
		for(int i = 0; i < numeroThreads; i++){
			
			thread[i] = new MinhaThread(buddy, queueRequisicoes, queueAlocadas, i);	
			thread[i].start();
			
		}
		
	}

}