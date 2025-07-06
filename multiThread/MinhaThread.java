package multiThread.buddy;

import java.util.Queue;
import java.util.concurrent.Semaphore;

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
		public void run() {
				while(!queueRequisicoes.isEmpty()){
					Integer n = queueRequisicoes.poll();
					if (n == null){
						return;
					}
	
			    Integer addr = buddy.allocate(n);

			    while (addr == null) {
			    		try{
			    		
									buddy.mutexFree.acquire();
								  buddy.freePercent();
			        
			        }catch (InterruptedException e) {
									e.printStackTrace();
							}finally {
							
									buddy.mutexFree.release();
									
							}
			        addr = buddy.allocate(n);
			    }
					
			    //buddy.printTree(); // Se quiser visualizar
				}
				
				Main.threadsAcabaram.release();
				
		}

};