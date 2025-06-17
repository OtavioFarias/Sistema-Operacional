package buddy;

import java.util.Queue;
import buddy.BuddySystem;

public class MinhaThread extends Thread{

		BuddySystem buddy;
		Queue<Integer> queueRequisicoes;
		Queue<Integer> queueAlocadas;
		int numeroDaThread;
	
		public MinhaThread(BuddySystem buddy, Queue<Integer> queueRequisicoes, Queue<Integer> queueAlocadas, int numeroDaThread){
				this.numeroDaThread = numeroDaThread;
				this.buddy = buddy;
				this.queueRequisicoes = queueRequisicoes;
				this.queueAlocadas = queueAlocadas;
		};

		public void run(){
				
				while(!queueRequisicoes.isEmpty()){
						Integer n = queueRequisicoes.remove();
						
						Integer addr = buddy.allocate(n);
						
						while(addr == null){
								buddy.free(queueAlocadas.remove());
								addr = buddy.allocate(n); 
						};
						
						queueAlocadas.add(addr);
						
						buddy.printStatus(numeroDaThread);
 				};
		};
};