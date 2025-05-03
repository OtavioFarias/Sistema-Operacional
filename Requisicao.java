package tools;
public class Requisicao{

    public int id;
    public int tamanho;

    public Requisicao(int id, int tamanho, int bloco){
        this.id = id;
        this.tamanho = ((tamanho + bloco - 1)/bloco ) * bloco;
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
