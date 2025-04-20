package tools;
import tools.Semafaro;
import tools.Requisicao;

public class Buffer{

    private int size;
    private Semafaro full;
    private Semafaro empty;
    private Semafaro mutex;
    private Requisicao[] buffer;
    private int in;
    private int out;

    public Buffer(int size){
        this.size = size;
        full = new Semafaro(size);
        empty = new Semafaro(0);
        mutex = new Semafaro(0);
        buffer = new Requisicao[size];
    }

    public Buffer(){
        this.size = 0;
        full = new Semafaro(0);
        empty = new Semafaro(0);
        mutex = new Semafaro(0);
        buffer = new Requisicao[0];
    }

    public void insert(Requisicao item){
        full.acquire();
        mutex.acquire();
        buffer[in] = item;
        in = (in + 1) % size;
        mutex.release();
        empty.release();
    }

    public Requisicao remove(){
        Requisicao item;
        empty.acquire();
        mutex.acquire();
        item = buffer[out];
        out = (out + 1) % size;
        mutex.release();
        full.release();
        return item;
    }
}
