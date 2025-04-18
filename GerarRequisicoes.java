package tools;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.ArrayList;
import tools.Requisicao;

public class GerarRequisicoes{

    private int numeroDeRequisicoes;
    private int max;
    private int min;


    public void GerarRequisicao(int numeroDeRequisicoes, int min, int max){
        this.numeroDeRequisicoes = numeroDeRequisicoes;
        this.min = min;
        this.max = max;
    }

    public void GerarRequisicao(int numeroDeRequisicoes){
        this.numeroDeRequisicoes = numeroDeRequisicoes;
    }

    public void GerarRequisicao(){
        this.numeroDeRequisicoes = 1;
    }


    public void getNumeroDeRequisicos(int numeroDeRequisicoes){
        this.numeroDeRequisicoes = numeroDeRequisicoes;
    }

    public int getNumeroDeRequisicoes(){
        return numeroDeRequisicoes;
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

    public List<Requisicao> gerar(){
        List<Requisicao> requisicoes = new ArrayList<>();
        for(int i = 0; i < numeroDeRequisicoes; i++){
            requisicoes.add(new Requisicao(i, min, max));
        }
        return requisicoes;
    }

}
