package buddy;

import buddy.MinhaThread;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.ArrayList;

public class Main{

	public static void main(String[] args){
	
		int numeroThreads = 4;
		Thread[] thread = new Thread[numeroThreads];
		
		Queue<Integer> queueRequisicoes = new ConcurrentLinkedQueue<>();
		Queue<Integer> queueAlocadas = new ConcurrentLinkedQueue<>();

		BuddySystem buddy = new BuddySystem(1024, 32);	
	
		 // Adiciona requisições na fila
      for (int i = 0; i < 20; i++) {
          int tamanho = 32 + (i % 4) * 32; // Ex: 32, 64, 96, 128, ...
          queueRequisicoes.add(tamanho);
      }
	
	
		for(int i = 0; i < numeroThreads; i++){
			
			thread[i] = new MinhaThread(buddy, queueRequisicoes, queueAlocadas);	
			thread[i].start();
			
		}
		
	}

}