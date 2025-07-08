package buddy;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.*;
import java.util.*;

public class BuddySystem {

	private final int maxLevel;
	private final int minLevel;
	private final ConcurrentLinkedQueue<Integer> queueAlocadas;
	private final Map<Integer, Queue<Integer>> freeLists;
	private final ConcurrentHashMap<Integer, Integer> allocatedBlocks;
	private final int totalSize;
	private final AtomicInteger tamAlocado = new AtomicInteger(0);
	private final double cleaningPercent;
	public final Semaphore mutexFree = new Semaphore(1);
	private final Semaphore[] levelLocks;

	public BuddySystem(int totalSize, int minBlockSize, double cleaningPercent, Queue<Integer> ignored) {
		this.totalSize = totalSize;
		this.minLevel = (int) (Math.log(minBlockSize) / Math.log(2));
		this.maxLevel = (int) (Math.log(totalSize) / Math.log(2));
		this.cleaningPercent = cleaningPercent;

		this.queueAlocadas = new ConcurrentLinkedQueue<>();
		this.allocatedBlocks = new ConcurrentHashMap<>();
		this.freeLists = new HashMap<>();

		this.levelLocks = new Semaphore[maxLevel + 1];

		for (int i = minLevel; i <= maxLevel; i++) {
			levelLocks[i] = new Semaphore(1);
			freeLists.put(i, new ConcurrentLinkedQueue<>());
		}

		// Começamos com um único bloco livre no maior nível
		freeLists.get(maxLevel).add(0);
	}

	public Integer allocate(int size) {
		int requiredLevel = calculateLevel(size);

		for (int level = requiredLevel; level <= maxLevel; level++) {
			try {
				levelLocks[level].acquire();

				Queue<Integer> list = freeLists.get(level);
				Integer block = list.poll(); // thread-safe

				if (block != null) {
					int address = split(block, level, requiredLevel);
					allocatedBlocks.put(address, requiredLevel);
					queueAlocadas.add(address);
					tamAlocado.addAndGet(size);
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

	private int split(int address, int fromLevel, int toLevel) {
		while (fromLevel > toLevel) {
			fromLevel--;
			int buddyAddress = address + (1 << fromLevel);

			try {
				levelLocks[fromLevel].acquire();
				freeLists.get(fromLevel).add(buddyAddress);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				levelLocks[fromLevel].release();
			}
		}
		return address;
	}

	private void merge(int address, int level) {
		while (level < maxLevel) {
			try {
				levelLocks[level].acquire();
				int buddyAddress = getBuddyAddress(address, level);
				Queue<Integer> list = freeLists.get(level);

				if (list.remove(buddyAddress)) {
					levelLocks[level].release();
					address = Math.min(address, buddyAddress);
					level++;
				} else {
					list.add(address);
					levelLocks[level].release();
					return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			levelLocks[level].acquire();
			freeLists.get(level).add(address);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			levelLocks[level].release();
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

	public void freePercent() {
		Integer address = queueAlocadas.poll();
		if (address == null) return;

		free(address);

		// Libera mais se alocação estiver muito baixa
		double alocadoPercent = 100.0 * tamAlocado.get() / totalSize;
		if (cleaningPercent < (100 - alocadoPercent)) {
			Integer extra = queueAlocadas.poll();
			if (extra != null) {
				free(extra);
			}
		}
	}
}
