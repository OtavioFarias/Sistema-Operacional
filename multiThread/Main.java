package buddy;

import java.util.*;
import java.util.concurrent.*;

public class Main {

	public static Semaphore threadsAcabaram;

	public static void main(String[] args) {

		int tamanhoTotal = Integer.parseInt(args[0]);
		int blocoMinimo = Integer.parseInt(args[1]);
		int numeroRequisicoes = Integer.parseInt(args[2]);
		int numeroThreads = Integer.parseInt(args[3]);
		double cleaningPercent = Double.parseDouble(args[4]);

		threadsAcabaram = new Semaphore(1 - numeroThreads);

		Thread[] threads = new Thread[numeroThreads];
		List<Queue<Integer>> filas = new ArrayList<>();

		// Distribui requisições entre threads (round-robin)
		for (int i = 0; i < numeroThreads; i++) {
			filas.add(new ConcurrentLinkedQueue<>());
		}

		for (int i = 0; i < numeroRequisicoes; i++) {
			int tam = blocoMinimo + (i % 4) * blocoMinimo;
			int idx = i % numeroThreads;
			filas.get(idx).add(tam);
		}

		long inicio = System.nanoTime();

		// Cria threads, cada uma com seu próprio BuddySystem
		for (int i = 0; i < numeroThreads; i++) {
			BuddySystem buddy = new BuddySystem(tamanhoTotal, blocoMinimo, cleaningPercent, new ConcurrentLinkedQueue<>());
			threads[i] = new MinhaThread(buddy, filas.get(i), i);
			threads[i].start();
		}

		try {
			threadsAcabaram.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long fim = System.nanoTime();
		double tempo = (fim - inicio) / 1_000_000_000.0;
		System.out.printf("Número de threads: %d | Tempo: %.3f segundos%n", numeroThreads, tempo);
	}
}
