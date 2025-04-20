package tools;
import tools.Semafaro;
import java.util.ArrayList;
import java.util.Date;

public class Consumidor{

    private ArrayList<Requisicao> executados;
    private Buffer buffer;

    public Consumidor(Buffer buffer){
        executados = new ArrayList<Requisicao>();
        this.buffer = buffer;
    }

    public void run(){
        Date message;
        int i = 0;
        while(true){
            message = new Date();
            System.out.println(message);
            executados.add(buffer.remove());

        }

    }

}

