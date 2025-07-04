package buddy;

import java.util.Queue;
import buddy.BuddySystem;

public class MinhaThread extends Thread{

		BuddySystem buddy;
		Queue<Integer> queueRequisicoes;
		int numeroDaThread;
	
		public MinhaThread(BuddySystem buddy, Queue<Integer> queueRequisicoes, int numeroDaThread){
				this.numeroDaThread = numeroDaThread;
				this.buddy = buddy;
				this.queueRequisicoes = queueRequisicoes;
		};
    @Override
		public void run(){
					
					try{
						Integer n = queueRequisicoes.remove();
						
						if(n != null){
						
							Integer addr = buddy.allocate(n);
							
							while(addr == null) {
						
								buddy.freePercent();
								addr = buddy.allocate(n);
						
								
							} 
							
							//buddy.printTree();
							
						}
				}catch(java.util.NoSuchElementException e){
   						 // Ignorado intencionalmente
				}

		};
};