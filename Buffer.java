package tools;
import tools.Semafaro;
import tools.Heap;
import tools.Requisicao;

public class Buffer{

    private int size;
    private Semafaro full;
    private Semafaro empty;
    private Semafaro mutex;
    private Heap buffer;
    private int in;
    private int out;

    public Buffer(int size){
        this.size = size;
        full = new Semafaro(size);
        empty = new Semafaro(0);
        mutex = new Semafaro(0);
        buffer = new Heap(size);
    }

    public Buffer(){
        this.size = 0;
        full = new Semafaro(0);
        empty = new Semafaro(0);
        mutex = new Semafaro(0);
        buffer = new Heap(0);
    }

    public void insert(int item){
        full.acquire();
        mutex.acquire();
        //buffer.heap[in] = item;
        in = (in + 1) % size;
        mutex.release();
        empty.release();
    }

    public Requisicao insert(){
        Requisicao item;
        item = null;
        empty.acquire();
        mutex.acquire();
        //item = buffer.heap(out);
        out = (out + 1) % size;
        mutex.release();
        full.release();
        return item;
    }
}
