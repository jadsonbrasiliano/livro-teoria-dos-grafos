package aplicacao;

import grafo.core.Grafo;
import grafo.core.Vertice;

import java.util.List;

public class Aplicacao {

    public static void main(String[] args) throws Exception {

        Grafo grafo = new Grafo();

        grafo.adicionarVertice("A");
        grafo.adicionarVertice("B");
        grafo.adicionarVertice("C");
        grafo.adicionarVertice("D");

        grafo.conectarVertices("A", "B");
        grafo.conectarVertices("A", "C");
        grafo.conectarVertices("A", "D");

        System.out.println("Grau do vértice A: " + grafo.getVertice("A").getGrau());
        System.out.println("Grau do vértice B: " + grafo.getVertice("B").getGrau());
        System.out.println("Grau do vértice C: " + grafo.getVertice("C").getGrau());

        System.out.print("\nO vértice A possui as seguintes adjacências: ");
        List<Vertice> adjacentes = grafo.getAdjacencias("A");
        for(Vertice vertice : adjacentes){
            System.out.print(vertice.getRotulo() + " ");
        }

        System.out.print("\nO vértice B possui as seguintes adjacências: ");
        adjacentes = grafo.getAdjacencias("B");
        for(Vertice vertice : adjacentes){
            System.out.print(vertice.getRotulo() + " ");
        }

        System.out.print("\nO vértice C possui as seguintes adjacências: ");
        adjacentes = grafo.getAdjacencias("C");
        for(Vertice vertice : adjacentes){
            System.out.print(vertice.getRotulo() + " ");
        }

        System.out.print("\nO vértice D possui as seguintes adjacências: ");
        adjacentes = grafo.getAdjacencias("D");
        for(Vertice vertice : adjacentes){
            System.out.print(vertice.getRotulo() + " ");
        }
    }
}
