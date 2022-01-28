package aplicacao;

import grafo.core.Grafo;
import grafo.core.Vertice;
import grafo.search.BuscaEmLargura;
import grafo.search.BuscaEmProfundidade;

import java.util.List;

public class Aplicacao {

    public static void main(String[] args) throws Exception {

        Grafo grafo = new Grafo();

        grafo.adicionarVertice("A");
        grafo.adicionarVertice("B");
        grafo.adicionarVertice("C");
        grafo.adicionarVertice("D");
        grafo.adicionarVertice("E");
        grafo.adicionarVertice("F");
        grafo.adicionarVertice("G");
        grafo.adicionarVertice("H");
        grafo.adicionarVertice("I");

        grafo.conectarVertices("A", "B");
        grafo.conectarVertices("A", "C");
        grafo.conectarVertices("A", "D");
        grafo.conectarVertices("B", "F");
        grafo.conectarVertices("B", "I");
        grafo.conectarVertices("D", "E");
        grafo.conectarVertices("D", "I");
        grafo.conectarVertices("D", "G");
        grafo.conectarVertices("I", "A");
        grafo.conectarVertices("I", "D");
        grafo.conectarVertices("I", "C");
        grafo.conectarVertices("I", "H");
        grafo.conectarVertices("E", "A");

        List<String> caminho = BuscaEmProfundidade.getInstance().buscar(grafo, "D", "H");
        System.out.print("Caminho feito por uma busca em profundidade: ");
        for (String passo : caminho){
            System.out.print(passo + " ");
        }

        caminho = BuscaEmLargura.getInstance().buscar(grafo, "B", "G");
        System.out.print("\nCaminho feito por uma busca em largura: ");
        for (String passo : caminho){
            System.out.print(passo + " ");
        }

        Grafo arvore = grafo.arvoreGeradoraPorProfundidade();
        System.out.println("\n\n--- Árvore geradora ---");

        System.out.println("Vértices");
        for(Vertice v : arvore.getVertices()){
            System.out.println("\t" + v.getRotulo());
        }

        System.out.println("Arestas");
        for(Vertice v : arvore.getVertices()){
            for(Vertice adj : arvore.getAdjacencias(v.getRotulo())){
                System.out.println("\t" + v.getRotulo() + adj.getRotulo());
            }
        }
    }
}
