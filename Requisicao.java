package tools;
public class Requisicao{

    public Integer id;
    public int tamanho;
    public int tamanhoR;

    public Requisicao(int id, int tamanhoR, int bloco){
        this.id = id;
        this.tamanhoR = tamanhoR;
        this.tamanho = (((tamanhoR + bloco - 1)/bloco ) * bloco)/4;
    }

    public Requisicao(int id){
        this.id = id;
    }

    public Requisicao(){
        this.id = 0;
          }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTamanho(int tamanho){
        this.tamanho = tamanho;
    }

    public int getTamanho(){
        return tamanho;
    }

}
