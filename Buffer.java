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
    private int garbagePercent;

    public Buffer(int MaxSize, int garbagePercent) {
        this.MaxSize = MaxSize;
        this.garbagePercent = garbagePercent;
        this.heap = new Integer[MaxSize];
        this.fila = new LinkedList<>();
    }

    public void insert(Requisicao item) {
        if (inserirHeap(item) == 1){
            size += item.tamanho;
            System.out.println("id " + item.id + " tamanho " + item.tamanho + " adicionado com sucesso!");
            System.out.println("Espaço usado da heap:" + size);
        } else {
            System.out.println("Buffer cheio");
            garbageCollector(item.tamanho);
            insert(item);
        }
    }

    private int inserirHeap(Requisicao item){
        int count = 0;
        int i = 0;
        if(item.tamanho > MaxSize - size) return 0;
        while(true){
            if(heap[i] == null && i < MaxSize){
                count++;
                if(count == item.tamanho){
                    fila.add(item.id);     // adiciona o id à fila
                    while(count > 0){
                        heap[i] = item.id;
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

    public void remove() {
        Integer id = fila.poll();    // remove da fila
        // remove da heap
        for(int i = 0; i < MaxSize; i++){
            //printHeap();
            if(heap[i] == id) {
                heap[i] = null;
                size--;
            }
        }
        System.out.println("id " + id + " removido com sucesso!");
        System.out.println("Espaço usado da heap: " + (size));

    }

    public void garbageCollector(int tamanho){//verificar se if para chamar garbage não é mais eficiente

        while(100-size*100/MaxSize < garbagePercent || (MaxSize - size) < tamanho){
            remove();
        }
        compactador();
    }

    public void compactador(){

        for(int i = MaxSize - 1; i > 0; i--){
            if(heap[i] == null){

                heap[i] = heap[i-1];
                heap[i-1] = null;

            }
        }
    }

    // Métodos auxiliares
    public void printHeap() {
        System.out.println("Heap: ");
        for(int i = 0; i < MaxSize; i++){
            System.out.print(" " + heap[i]);
         }
    }

    public void printFila() {
        System.out.println("Fila: " + fila);
    }
}
