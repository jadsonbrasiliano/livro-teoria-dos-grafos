package aplicacao;

import grafo.core.Digrafo;
import grafo.core.Grafo;
import grafo.core.Vertice;
import grafo.util.AlgoritmoDijkstra;
import grafo.util.AlgoritmoFloydWarshall;
import grafo.util.AlgoritmoPrim;

import java.util.Map;
import java.util.Set;

public class Aplicacao {

    public static void main(String[] args) throws Exception {

        Grafo grafo = new Grafo();

        grafo.adicionarVertice("A");
        grafo.adicionarVertice("B");
        grafo.adicionarVertice("C");
        grafo.adicionarVertice("D");

        grafo.conectarVertices("A", "B", 1);
        grafo.conectarVertices("A", "C", 2);
        grafo.conectarVertices("B", "D", 1);
        grafo.conectarVertices("C", "D", 2);

        // Algoritmo de Dijkstra
        System.out.println("\n--- Algoritmo de Dijkstra ---\n");
        Map<String, AlgoritmoDijkstra.Info> menoresCaminhos = AlgoritmoDijkstra.getInstance().processar("A", "D", grafo);
        Set<String> keys = menoresCaminhos.keySet();
        for (String key : keys){
            AlgoritmoDijkstra.Info info = menoresCaminhos.get(key);
            String predecessor = info.predecessor == null ? " " : info.predecessor.getRotulo();
            System.out.println(key + " " + info.distancia + " - " + predecessor);
        }

        Digrafo digrafo = new Digrafo();

        digrafo.adicionarVertice("X");
        digrafo.adicionarVertice("V");
        digrafo.adicionarVertice("U");
        digrafo.adicionarVertice("Y");
        digrafo.adicionarVertice("Z");

        digrafo.conectarVertices("X", "U", 3);
        digrafo.conectarVertices("U", "V", 8);
        digrafo.conectarVertices("U", "Z", 1);
        digrafo.conectarVertices("V", "X", 5);
        digrafo.conectarVertices("V", "U", 10);
        digrafo.conectarVertices("V", "Z", 2);
        digrafo.conectarVertices("V", "Y", 7);
        digrafo.conectarVertices("Z", "Y", 9);
        digrafo.conectarVertices("Y", "Z", 5);
        digrafo.conectarVertices("Y", "U", 8);

        // Algoritmo de Floyd e Warshall
        System.out.println("\n--- Algoritmo de Floyd e Warshall ---\n");
        Map<String, Map<String, AlgoritmoFloydWarshall.Info>> matriz = AlgoritmoFloydWarshall.getInstance().processar(digrafo);
        for (String v : matriz.keySet()){
            System.out.println("Vértice " + v);
            Map<String, AlgoritmoFloydWarshall.Info> linha = matriz.get(v);

            for (String u : linha.keySet()){
                AlgoritmoFloydWarshall.Info info = linha.get(u);
                System.out.println(u + " com distância " + info.distancia + " por " + info.porQualVertice.getRotulo());
            }
            System.out.println();
        }

        Grafo grafoPonderado = new Grafo();

        grafoPonderado.adicionarVertice("A");
        grafoPonderado.adicionarVertice("B");
        grafoPonderado.adicionarVertice("C");
        grafoPonderado.adicionarVertice("D");
        grafoPonderado.adicionarVertice("E");
        grafoPonderado.adicionarVertice("F");

        grafoPonderado.conectarVertices("A", "B", 6);
        grafoPonderado.conectarVertices("A", "C", 1);
        grafoPonderado.conectarVertices("A", "D", 5);
        grafoPonderado.conectarVertices("B", "C", 2);
        grafoPonderado.conectarVertices("B", "E", 5);
        grafoPonderado.conectarVertices("C", "E", 6);
        grafoPonderado.conectarVertices("C", "F", 4);
        grafoPonderado.conectarVertices("C", "D", 2);
        grafoPonderado.conectarVertices("D", "F", 4);
        grafoPonderado.conectarVertices("E", "F", 3);

        // Algoritmo de Prim
        System.out.println("\n--- Algoritmo de Prim ---\n");
        String raiz = "A";
        Digrafo mst = AlgoritmoPrim.getInstance().processar(raiz, grafoPonderado);
        for (Vertice v : mst.getVertices()){
            System.out.println("O vértice " + v.getRotulo() + " é adjacente aos vértices:");

            for (Vertice adj : mst.getAdjacencias(v.getRotulo())){
                System.out.println(adj.getRotulo() + " com peso " + mst.getPeso(v.getRotulo(), adj.getRotulo()));
            }
            System.out.println();
        }
    }
}
