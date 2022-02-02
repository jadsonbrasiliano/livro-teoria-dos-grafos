package grafo.search;

import java.util.*;

public class Caminho {

    private Map<String, String> caminho;

    Caminho(){
        this.caminho = new HashMap<>();
    }

    void ligar(String anterior, String proximo){
        this.caminho.put(anterior, proximo);
    }

    public List<String> gerar(String origem, String destino){
        List<String> resultado = new ArrayList<>();
        String no = destino;

        while(no != origem && this.caminho.containsKey(no)){
            resultado.add(no);
            no = this.caminho.get(no);
        }

        resultado.add(no);
        Collections.reverse(resultado);
        return resultado;
    }
}
