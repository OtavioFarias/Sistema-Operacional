package tools;
import java.util.concurrent.ThreadLocalRandom;

public class Requisicao{

    private int id;
    private int tamanhoDaRequisicao;
    private int max;
    private int min;


    public Requisicao(int id, int tamanhoDaRequisicao, int min, int max){
        this.id = id;
        this.min = min;
        this.max = max;
        this.tamanhoDaRequisicao = tamanhoDaRequisicao;
    }

    public Requisicao(int id, int min, int max){
        this.id = id;
        this.min = min;
        this.max = max;
        this.tamanhoDaRequisicao = ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public Requisicao(){
        this.id = 0;
        this.tamanhoDaRequisicao = ThreadLocalRandom.current().nextInt(min, max + 1);
    }

     public Requisicao(int id){
        this.id = id;
        this.tamanhoDaRequisicao = ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTamanhoDaRequisicao(int tamanhoDaRequisicao){
        this.tamanhoDaRequisicao = tamanhoDaRequisicao;
    }

    public int getTamanhoDaRequisicao(){
        return tamanhoDaRequisicao;
    }

    public int getMin(){
        return min;
    }

    public void setMin(int min){
        this.min = min;
    }

    public int getMax(){
        return max;
    }

    public void setMax(){
        this.max = max;
    }

}
