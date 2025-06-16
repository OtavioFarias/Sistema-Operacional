package buddy;

import java.util.Queue;
import buddy.BuddySystem;

public class MinhaThread extends Thread{

		BuddySystem buddy;
		Queue<Integer> queueRequisicoes;
		Queue<Integer> queueAlocadas;
	
		public MinhaThread(BuddySystem buddy, Queue<Integer> queueRequisicoes, Queue<Integer> queueAlocadas){
				
				this.buddy = buddy;
				this.queueRequisicoes = queueRequisicoes;
				this.queueAlocadas = queueAlocadas;
		};

		public void run(){
				
				while(!queueRequisicoes.isEmpty()){
						Integer n = queueRequisicoes.remove();
						
						if(buddy.allocate(n) == null){
								buddy.free(queueAlocadas.remove());
								buddy.allocate(n); 
						}
						
						queueAlocadas.add(n);
						
						buddy.printStatus();
				};
		};
};