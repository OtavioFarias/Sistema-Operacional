package buddy;

import java.util.Queue;

public class MinhaThread extends Thread {

	private final BuddySystem buddy;
	private final Queue<Integer> fila;
	private final int numeroDaThread;

	public MinhaThread(BuddySystem buddy, Queue<Integer> fila, int numeroDaThread) {
		this.buddy = buddy;
		this.fila = fila;
		this.numeroDaThread = numeroDaThread;
	}

	@Override
	public void run() {
		while (!fila.isEmpty()) {
			Integer n = fila.poll();
			if (n == null) break;

			Integer addr = buddy.allocate(n);

			while (addr == null) {
				buddy.freePercent(); // limpa espaço
				addr = buddy.allocate(n);
			}
		}

		Main.threadsAcabaram.release();
	}
}
