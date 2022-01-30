package aplicacao;

import grafo.core.Digrafo;
import grafo.core.Grafo;
import grafo.core.Vertice;

import java.util.List;

public class Aplicacao {

    public static void main(String[] args) throws Exception {

        Digrafo digrafo = new Digrafo();

        digrafo.adicionarVertice("RJ");
        digrafo.adicionarVertice("SP");
        digrafo.adicionarVertice("BH");
        digrafo.adicionarVertice("PT");
        digrafo.adicionarVertice("OS");
        digrafo.adicionarVertice("SV");
        digrafo.adicionarVertice("CR");
        digrafo.adicionarVertice("PA");

        digrafo.conectarVertices("RJ", "SP", null);
        digrafo.conectarVertices("RJ", "BH", null);
        digrafo.conectarVertices("RJ", "PT", null);
        digrafo.conectarVertices("RJ", "PA", null);
        digrafo.conectarVertices("SP", "BH", null);
        digrafo.conectarVertices("SP", "OS", null);
        digrafo.conectarVertices("SP", "SV", null);
        digrafo.conectarVertices("SP", "CR", null);
        digrafo.conectarVertices("SP", "PA", null);
        digrafo.conectarVertices("SV", "PA", null);
        digrafo.conectarVertices("CR", "PA", null);

        Grafo arvore = digrafo.arvoreGeradoraPorProfundidade("PT");

        System.out.println("--- Árvore geradora via busca pro profundidade usando raiz ---\n");
        for (Vertice v : arvore.getVertices()){
            System.out.print("Vértice " + v.getRotulo() + " conectado a: ");
            List<Vertice> adjacencias = arvore.getAdjacencias(v.getRotulo());

            if(!adjacencias.isEmpty()){
                for(Vertice adj : adjacencias){
                    System.out.print(adj.getRotulo() + " ");
                }
            } else {
                System.out.print("-");
            }
            System.out.println();
        }

        Grafo grafoPonderado = new Grafo();

        grafoPonderado.adicionarVertice("A");
        grafoPonderado.adicionarVertice("B");
        grafoPonderado.adicionarVertice("C");
        grafoPonderado.adicionarVertice("D");
        grafoPonderado.adicionarVertice("E");

        grafoPonderado.conectarVertices("A", "B", 12);
        grafoPonderado.conectarVertices("C", "E", 10);
        grafoPonderado.conectarVertices("B", "D", 5);
        grafoPonderado.conectarVertices("D", "A", 2);
        grafoPonderado.conectarVertices("B", "E", 1);
        grafoPonderado.conectarVertices("A", "C", 7);

        System.out.println("\n--- Grafo Ponderado  ---\n");
        System.out.println("Vértices:");
        for (Vertice v : grafoPonderado.getVertices()){
            System.out.println("\t" + v.getRotulo());
        }

        System.out.println("\nArestas:");
        for (Vertice v : grafoPonderado.getVertices()){
            for (Vertice adj : grafoPonderado.getAdjacencias(v.getRotulo())){
                int peso = grafoPonderado.getPeso(v.getRotulo(), adj.getRotulo());
                System.out.println("\t" + v.getRotulo() + adj.getRotulo() + " : " + peso);
            }
        }

        Digrafo digrafoPonderado = new Digrafo();

        digrafoPonderado.adicionarVertice("X");
        digrafoPonderado.adicionarVertice("Y");
        digrafoPonderado.adicionarVertice("Z");
        digrafoPonderado.adicionarVertice("W");
        digrafoPonderado.adicionarVertice("V");

        digrafoPonderado.conectarVertices("X", "V", 44);
        digrafoPonderado.conectarVertices("Y", "W", 37);
        digrafoPonderado.conectarVertices("W", "Z", 38);
        digrafoPonderado.conectarVertices("X", "V", 16);
        digrafoPonderado.conectarVertices("V", "X", 22);
        digrafoPonderado.conectarVertices("V", "Y", 57);

        System.out.println("\n--- Dígrafo Ponderado ---\n");
        System.out.println("Vértices:");
        for (Vertice v : digrafoPonderado.getVertices()){
            System.out.println("\t" + v.getRotulo());
        }

        System.out.println("\nArestas:");
        for (Vertice v : digrafoPonderado.getVertices()){
            for (Vertice adj : digrafoPonderado.getAdjacencias(v.getRotulo())){
                int peso = digrafoPonderado.getPeso(v.getRotulo(), adj.getRotulo());
                System.out.println("\t" + v.getRotulo() + adj.getRotulo() + " : " + peso);
            }
        }
    }
}
