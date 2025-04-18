package tools;

public class Semafaro{

    private int count;
    private int buffer_size;

    public Semafaro(int buffer_size){
        count = 0;
        this.buffer_size = buffer_size;
    }

    public Semafaro(){
        count = 0;
        buffer_size = 0;
    }

    public int getCount(){
        return count;
    }

    public void acquire(){
        if(count > 0){
            count--;
        }
        else{
            while(count <= 0){
                //wait
            }
        }
    }

    public void release(){
        if(count < buffer_size){
            count++;
        }
        else{
            while(count >= buffer_size){
                //wait
            }
        }
    }
}
