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
    @Override
		public void run(){
				
				while(!queueRequisicoes.isEmpty()){
						Integer n = queueRequisicoes.poll();
						if (n == null){
						  continue;
						}
						
						Integer addr = buddy.allocateWithCleaning(n, queueAlocadas);
						
						if (addr != null) {
						  queueAlocadas.add(addr);
						  System.out.println("Thread " + numeroDaThread + ": SUCESSO ao alocar " + n + " bytes no endereço " + addr);
						  buddy.printTree();
						} else {
						  System.err.println("Thread " + numeroDaThread + ": FALHA FINAL ao alocar " + n + " bytes.");
						}
 				};
		};
};
