package multiThread.buddy;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.*;

public class BuddySystem {
    private final int maxLevel;
    private final int minLevel;
    private final Queue<Integer> queueAlocadas;
    private final Map<Integer, List<Integer>> freeLists; // Nível -> blocos livres (endereços)
    private final Map<Integer, Integer> allocatedBlocks; // Endereço -> Nível
    private final int totalSize;
    private int tamAlocado;
    private double cleaningPercent;
    //private Semaphore mutexBuddy = new Semaphore(1);
    public Semaphore mutexFree = new Semaphore(1);
  	private final Semaphore[] levelLocks;

  	public BuddySystem(int totalSize, int minBlockSize, double cleaningPercent, Queue<Integer> queueAlocadas) {
        this.totalSize = totalSize;
        this.minLevel = (int) (Math.log(minBlockSize) / Math.log(2));
        this.maxLevel = (int) (Math.log(totalSize) / Math.log(2));
        //this.freeLists =  Collections.synchronizedMap(new HashMap<>());
        this.allocatedBlocks =  Collections.synchronizedMap(new HashMap<>());
        this.freeLists =  new HashMap<>();
        //this.allocatedBlocks =  new HashMap<>();
        this.cleaningPercent = cleaningPercent;
        //this.queueAlocadas = queueAlocadas;
        this.queueAlocadas = new ConcurrentLinkedQueue<>();
				this.tamAlocado = 0;
				
				levelLocks = new Semaphore[maxLevel + 1];
				for (int i = minLevel; i <= maxLevel; i++) {
						levelLocks[i] = new Semaphore(1);
						freeLists.put(i, new ArrayList<>());
				}
				

        for (int i = minLevel; i <= maxLevel; i++) {
            freeLists.put(i, new ArrayList<>());
        }

        // Começamos com um único bloco livre no maior nível
        freeLists.get(maxLevel).add(0);
    }

    public Integer allocate(int size) {
        int requiredLevel = calculateLevel(size);

        for (int level = requiredLevel; level <= maxLevel; level++) {
						try {
								levelLocks[level].acquire();
								if (!freeLists.get(level).isEmpty()) {
								    // Agora que já seguramos o lock, podemos chamar split com segurança
								    int address = split(level, requiredLevel);
								    allocatedBlocks.put(address, requiredLevel);
								    queueAlocadas.add(address);
								    tamAlocado += size;
								    return address;
								}
								
						} catch (InterruptedException e) {
								e.printStackTrace();
						} finally {
								levelLocks[level].release();
						}
        }
        return null; // Falha na alocação
    }

    public void free(int address) {
        Integer level = allocatedBlocks.remove(address);
        if (level == null) return;

        merge(address, level);
    }

    private int split(int fromLevel, int toLevel) {
			int address = 0;
			try {
			    //levelLocks[fromLevel].acquire();
			    address = freeLists.get(fromLevel).remove(0);
			    //levelLocks[fromLevel].release();

			    while (fromLevel > toLevel) {
			        fromLevel--;
			        int buddyAddress = address + (1 << fromLevel);
			        levelLocks[fromLevel].acquire();
			        freeLists.get(fromLevel).add(buddyAddress);
			        levelLocks[fromLevel].release();
			    }
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
			return address;
		}

		private void merge(int address, int level) {
		  try {
		      while (level < maxLevel) {
		          levelLocks[level].acquire();
		          int buddyAddress = getBuddyAddress(address, level);
		          List<Integer> list = freeLists.get(level);

		          if (list.remove((Integer) buddyAddress)) {
		              levelLocks[level].release();
		              address = Math.min(address, buddyAddress);
		              level++;
		          } else {
		              list.add(address);
		              levelLocks[level].release();
		              break;
		          }
		      }

		      if (level == maxLevel) {
		          levelLocks[level].acquire();
		          freeLists.get(level).add(address);
		          levelLocks[level].release();
		      }
		  } catch (InterruptedException e) {
		      e.printStackTrace();
		  }
	}

    private int getBuddyAddress(int address, int level) {
        return address ^ (1 << level);
    }

    private int calculateLevel(int size) {
        int level = minLevel;
        int blockSize = 1 << level;
        while (blockSize < size && level < maxLevel) {
            level++;
            blockSize <<= 1;
        }
        return level;
    }
  
  public void freePercent(){
	
		if (queueAlocadas.isEmpty()) return;
		
		free(queueAlocadas.remove());
		/*
		System.out.println("Tamanho Alocado" + tamAlocado);
		System.out.println("Tamanho total" + totalSize);
		System.out.println("Porcentagem liberada" + (100-100*tamAlocado/totalSize));
		System.out.println("CLeaning Percent:"  + cleaningPercent);
		System.out.println();
		
		if(cleaningPercent < (100-100*tamAlocado/totalSize)) {
			//System.out.println(100-100*tamAlocado/totalSize);
			free(queueAlocadas.remove());
			
		}
		*/
	}    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	
	public synchronized void printStatus(int numeroDaThread) {
    		System.out.println();
    		System.out.println("Thread em execução:" + numeroDaThread);
        System.out.println("Estado atual da memória:");
        for (int i = minLevel; i <= maxLevel; i++) {
            List<Integer> blocks = freeLists.get(i);
            if (!blocks.isEmpty()) {
                System.out.println("Nível " + i + " (bloco de " + (1 << i) + "): " + blocks);
            }
        }
    }
    
    public void printTree() {
    	System.out.println("\nVisualização da memória como árvore:");
    	printBlockRecursive(0, maxLevel, "");
		}
    
    private void printBlockRecursive(int address, int level, String indent) {
		  int size = 1 << level;

		  boolean isAllocated = allocatedBlocks.containsKey(address) && allocatedBlocks.get(address) == level;
		  boolean isFree = freeLists.get(level).contains(address);

		  String status;
		  if (isAllocated) {
		      status = "Ocupado";
		  } else if (isFree) {
		      status = "Livre";
		  } else {
		      status = "Dividido";
		  }

		  System.out.printf("%s[Endereço: %d, Tamanho: %d, Status: %s]%n", indent, address, size, status);

		  // Só mostra filhos se estiver dividido (nem alocado nem livre) e não for nível mínimo
		  if (status.equals("Dividido") && level > minLevel) {
		      int halfSize = size / 2;
		      printBlockRecursive(address, level - 1, indent + "  "); // Filho esquerdo
		      printBlockRecursive(address + halfSize, level - 1, indent + "  "); // Filho direito
		  }
	}
	
	
	
	*/
}
