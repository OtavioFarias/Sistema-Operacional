package tools;
import tools.Semafaro;
import java.util.Date;

public class Produtor{

    private Requisicao[] requisicoes;
    private Buffer buffer;

    public Produtor(Requisicao[] requisicoes, Buffer buffer){
        this.requisicoes = requisicoes;
        this.buffer = buffer;
    }

    public void run(){
        Date message;
        int i = 0;
        while(true){
            message = new Date();
            System.out.println(message);
            buffer.insert(requisicoes[i]);
            i++;
        }

    }

}
