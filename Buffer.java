package tools;
import tools.Requisicao;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Queue;

public class Buffer {

    private int size = 0; //tamanho usado
    private int MaxSize; //tamanho total
    private Integer heap[]; // heap de ids
    private Queue<Integer> fila;         // fila de ids

    public Buffer(int MaxSize) {
        this.MaxSize = MaxSize;
        this.heap = new Integer[MaxSize];
        this.fila = new LinkedList<>();
    }

    public void insert(Requisicao item) {
        if (inserirHeap(item) == 1){
            size += item.getTamanho();
            System.out.println("id " + item.getId() + " tamanho " + item.getTamanho() + " adicionado com sucesso!");
            System.out.println("Espaço atual da heap:" + (MaxSize - size));
            fila.add(item.getId());     // adiciona o id à fila
        } else {
            System.out.println("Buffer cheio");
            remove();
            insert(item);
        }
    }

    private int inserirHeap(Requisicao item){
        int count = 0;
        int i = 0;
        while(true){
            if(heap[i] == null && i < MaxSize){
                count++;
                if(count == item.getTamanho()){
                    while(count > 0){
                        heap[i] = item.getId();
                        i--;
                        count--;
                    }
                    return 1;
                }
            }
            else{
                count = 0;
            }
            i++;
            if (i == MaxSize) return 0;
        }
    }

    public Integer remove() {
        if (!fila.isEmpty()) {
            Integer id = fila.poll();    // remove da fila
            // remove da heap
            for(int i = 0; i < MaxSize; i++){
                if(heap[i] == id) {
                heap[i] = null;
                size--;
                }
            }
            System.out.println("id " + id + " removido com sucesso!");
            System.out.println("Espaço atual da heap: " + (MaxSize-size));
            return id;
        } else {
            System.out.println("Buffer vazio. Nada a remover.");
            return null;
        }
    }

    // Métodos auxiliares
    public void printHeap() {
        System.out.println("Heap: ");
        for(int i = 0; i < MaxSize; i++){
            System.out.println(heap[i]);
         }
    }

    public void printFila() {
        System.out.println("Fila: " + fila);
    }
}
