package buddy;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

public class Main{

	public static void main(String[] args){
	
		
		Queue<Integer> queueRequisicoes = new LinkedList<Integer>();
		Queue<Integer> queueAlocadas = new LinkedList<Integer>();

		BuddySystem buddy = new BuddySystem(Integer.parseInt(args[0]) , Integer.parseInt(args[1]) , Double.parseDouble(args[3]));
	
		 // Adiciona requisições na fila
      for (int i = 0; i <= Integer.parseInt(args[2]) ; i++) {
          int tamanho = Integer.parseInt(args[1]) + (i % 4) * Integer.parseInt(args[1]); // Ex: 32, 64, 96, 128, ...
          queueRequisicoes.add(tamanho);
      }
	
		while(!queueRequisicoes.isEmpty()){
			Integer n = queueRequisicoes.poll();
			if (n == null){
			  continue;
			}
			
			Integer addr = buddy.allocate(n);
		}
		
		//buddy.printTree();
		
	}

}