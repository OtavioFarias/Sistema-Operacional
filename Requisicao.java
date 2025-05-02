package tools;
public class Requisicao{

    private int id;
    private int tamanho;

    public Requisicao(int id, int tamanho){
        this.id = id;
        this.tamanho = tamanho;
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
