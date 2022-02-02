package aplicacao;

import grafo.core.Digrafo;
import grafo.core.Grafo;
import grafo.core.Vertice;
import grafo.search.BuscaEmLargura;
import grafo.search.BuscaEmProfundidade;
import grafo.util.AlgoritmoDijkstra;
import grafo.util.AlgoritmoFloydWarshall;
import grafo.util.AlgoritmoPrim;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Aplicacao {

    private final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Aplicacao app = new Aplicacao();
        Grafo grafo = null;

        app.menu();

        while(true){
            String opcao = app.ler("-* Digite a opção desejada: ").toUpperCase();

            try {
                switch (opcao) {
                    case "G" -> grafo = app.novoGrafo();
                    case "D" -> grafo = app.novoDigrafo();
                    case "V" -> app.novoVertice(grafo);
                    case "A" -> app.novaAresta(grafo);
                    case "PE" -> app.novaArestaPonderada(grafo);
                    case "P" -> app.buscaPorProfundidade(grafo);
                    case "L" -> app.buscaPorLargura(grafo);
                    case "AG" -> app.arvoreGeradora(grafo);
                    case "PR" -> app.arvoreGeradoraMinimaPorPrim(grafo);
                    case "DIJ" -> app.algoritmoDijkstra(grafo);
                    case "FL" -> app.algoritmoFloydWarshall(grafo);
                    case "VIZ" -> app.imprimir(grafo);
                    case "S" -> app.sair();
                    default -> {
                        System.out.println("Opção inválida.");
                        app.input.close();
                        app.sair();
                    }
                }

            } catch (Exception e){
                System.out.println("Erro: " + e.getMessage());
                System.out.println("Abortando...");
                System.exit(1);
            }
        }
    }

    private void menu(){
        StringBuilder texto = new StringBuilder();
        texto.append("*********** Bem vindo(a) ***********\n")
                .append("-* Escolha uma das opções abaixo: -*\n")
                .append("- [G]rafo \n")
                .append("- [D]ígrafo \n")
                .append("- [V]értice \n")
                .append("- [A]resta \n")
                .append("- Aresta com [Pe]so \n")
                .append("- Busca por [P]rofundidade \n")
                .append("- Busca por [L]argura \n")
                .append("- [AG] - Árvore Geradora \n")
                .append("- Árvore Geradora Mínima por [Pr]im \n")
                .append("- Algoritmo de [Dij]kstra \n")
                .append("- Algoritmo de [Fl]oydWarshall \n")
                .append("- Gerar representação Graph[Viz] \n")
                .append("- [S]air \n")
                .append("************************************");
        System.out.println(texto);
    }

    private String ler(String mensagem){
        System.out.print(mensagem);
        return input.next();
    }

    private Grafo novoGrafo(){
        Grafo grafo = new Grafo();
        System.out.println("Novo grafo criado.");
        return grafo;
    }

    private Digrafo novoDigrafo(){
        Digrafo digrafo = new Digrafo();
        System.out.println("Novo dígrafo criado.");
        return digrafo;
    }

    private void novoVertice(Grafo grafo) throws Exception {
        String nome = ler("Defina o nome do vértice: ");
        grafo.adicionarVertice(nome);
        System.out.println("Novo vértice '" + nome + "' criado.");
    }

    private void novaAresta(Grafo grafo) throws Exception {
        String verticeOrigem = ler("Informe o vértice de origem: ");
        String verticeDestino = ler("Informe o vértice de destino: ");
        grafo.conectarVertices(verticeOrigem, verticeDestino, null);
        System.out.println("Nova aresta criada.");
    }

    private void novaArestaPonderada(Grafo grafo) throws Exception {
        String verticeOrigem = ler("Informe o vértice de origem: ");
        String verticeDestino = ler("Informe o vértice de destino: ");
        String peso = ler("Informe o peso da aresta: ");
        grafo.conectarVertices(verticeOrigem, verticeDestino, Integer.valueOf(peso));
        System.out.println("Nova aresta ponderada criada.");
    }

    private void buscaPorProfundidade(Grafo grafo){
        String verticeOrigem = ler("Informe o vértice de origem: ");
        String verticeDestino = ler("Informe o vértice de destino: ");
        List<String> caminho = BuscaEmProfundidade.getInstance().buscar(grafo, verticeOrigem, verticeDestino);
        graphVizParaBuscas(grafo, caminho);
    }

    private void buscaPorLargura(Grafo grafo){
        String verticeOrigem = ler("Informe o vértice de origem: ");
        String verticeDestino = ler("Informe o vértice de destino: ");
        List<String> caminho = BuscaEmLargura.getInstance().buscar(grafo, verticeOrigem, verticeDestino);
        graphVizParaBuscas(grafo, caminho);
    }

    private void arvoreGeradora(Grafo grafo) throws Exception {
        Grafo arvore = grafo.arvoreGeradoraPorProfundidade();
        String graphViz = this.graphViz(grafo, false);
        boolean isDigrafo = grafo instanceof Digrafo;
        graphVizParaArvores(isDigrafo, arvore, graphViz, false);
    }

    private void arvoreGeradoraMinimaPorPrim(Grafo grafo) throws Exception {
        String raiz = ler("Informe a raiz da árvore: ");
        Digrafo arvore = AlgoritmoPrim.getInstance().processar(raiz, grafo);
        String graphViz = this.graphViz(arvore, true);
        boolean isDigrafo = grafo instanceof Digrafo;
        graphVizParaArvores(isDigrafo, arvore, graphViz, true);
    }

    private void algoritmoDijkstra(Grafo grafo){
        String verticeOrigem = ler("Informe o vértice de origem: ");
        String verticeDestino = ler("Informe o vértice de destino: ");

        Map<String, AlgoritmoDijkstra.Info> caminho = AlgoritmoDijkstra.getInstance().processar(verticeOrigem, verticeDestino, grafo);
        String graphViz = this.graphViz(grafo, true);

        for(String key : caminho.keySet()){
            AlgoritmoDijkstra.Info info = caminho.get(key);

            if(info.predecessor != null){
                String conexaoAsString = info.predecessor.getRotulo() + " -> " + key;
                int peso = grafo.getPeso(info.predecessor.getRotulo(), key);
                String regex = conexaoAsString + "\\[.*\\]";
                String replacement = conexaoAsString + "[color=red, label=" + peso + "]";
                graphViz = graphViz.replaceAll(regex, replacement);
            }
        }
        System.out.println(graphViz);
    }

    private void algoritmoFloydWarshall(Grafo grafo){
        if(grafo instanceof Digrafo digrafo){
            Map<String, Map<String, AlgoritmoFloydWarshall.Info>> matriz = AlgoritmoFloydWarshall.getInstance().processar(digrafo);
            this.graphVizParaFloydWarshall(matriz);
        } else {
            System.out.println("Operação não permitida para grafos somente para dígrafos!");
        }
    }

    private String graphViz(Grafo grafo, boolean isPonderado){
        System.out.println("\nPara visualizar o grafo acesse o site http://magjac.com/graphviz-visual-editor/ " +
                "e cole o conteúdo gerado no painel da esquerda OU http://www.webgraphviz.com/ e cole o conteúdo " +
                "gerado na textarea e clique em \"Generate Graph\".\n");
        return grafo.graphViz(isPonderado);
    }

    private void graphVizParaBuscas(Grafo grafo, List<String> caminho){
        boolean isDigrafo = grafo instanceof Digrafo;

        String graphViz = this.graphViz(grafo, false);
        String anterior = null;
        String proximo;

        for(String v : caminho){

            if(anterior == null){
                anterior = v;
            } else {
                proximo = v;
                String conexao = anterior + " -> " + proximo;
                String regex = conexao + "\\[.*\\]";

                if(!Pattern.compile(regex).matcher(graphViz).find()){
                    conexao = proximo + " -> " + anterior;
                    regex = conexao + "\\[.*\\]";
                }

                String replacement = conexao + "[";
                replacement = replacement.concat(!isDigrafo ? "arrowhead=none, color=red]" : "rolor=red]");
                graphViz = graphViz.replaceAll(regex, replacement);
                anterior = proximo;
            }
        }
        System.out.println(graphViz);
    }

    private void graphVizParaArvores(boolean isDigrafo, Grafo arvore, String graphViz, boolean isPonderado){
        for (Vertice v : arvore.getVertices()){

            for (Vertice adj : arvore.getAdjacencias(v.getRotulo())){
                String conexaoAsString = v.getRotulo() + " -> " + adj.getRotulo();
                String regex = conexaoAsString + "\\[.*\\]";
                String replacement = conexaoAsString + "[";

                if(isPonderado){
                    int peso = arvore.getPeso(v.getRotulo(), adj.getRotulo());
                    replacement = replacement.concat("label=" + peso).concat(",");
                }

                replacement = replacement.concat(!isDigrafo ? "arrowhead=none, color=red]" : "color=red]");
                graphViz = graphViz.replaceAll(regex, replacement);
            }
        }
        System.out.println(graphViz);
    }

    private void graphVizParaFloydWarshall(Map<String, Map<String, AlgoritmoFloydWarshall.Info>> matriz){
        System.out.println("\nPara visualizar o resultado do algoritmo acesse o site " +
                "http://magjac.com/graphviz-visual-editor/ e cole o conteúdo gerado no painel da esquerda.\n");

        StringBuilder graphViz = new StringBuilder();
        graphViz.append("digraph D {\n")
                .append("\taHtmlTable [\n")
                .append("\t\tshape=plaintext\n")
                .append("\t\tcolor=black\n")
                .append("\t\tlabel=<\n")
                .append("\t\t\t<table style='border: 1px solid black; text-align: center;' cellspacing='0'>\n");

        graphViz.append("\t\t\t\t<tr>\n\t\t\t\t\t<td></td>\n");

        for (String v : matriz.keySet()) {
            graphViz.append("\t\t\t\t\t<td style='font-weight: bold;'>").append(v).append("</td>\n");
        }

        graphViz.append("\t\t\t\t</tr>\n");

        for (String v : matriz.keySet()){
            graphViz.append("\t\t\t\t<tr>\n")
                    .append("\t\t\t\t\t<td style='font-weight: bold;'>").append(v).append("</td>\n");

            Map<String, AlgoritmoFloydWarshall.Info> celulas = matriz.get(v);

            for (String u : celulas.keySet()){
                AlgoritmoFloydWarshall.Info info = celulas.get(u);
                graphViz.append("\t\t\t\t\t<td>").append(info.porQualVertice.getRotulo()).append(" (");

                if (info.distancia == Integer.MAX_VALUE){
                    graphViz.append("∞");
                } else {
                    graphViz.append(info.distancia);
                }
                graphViz.append(")").append("</td>\n");
            }
            graphViz.append("\t\t\t\t</tr>\n");
        }
        graphViz.append("\t\t\t</table>").append("\n\t>];").append("\n}");
        System.out.println(graphViz);
    }

    private void imprimir(Grafo grafo) {
        System.out.println("Imprimindo grafo...");
        String resposta = ler("O grafo é ponderado (s/n)? ");
        String graphViz = this.graphViz(grafo, "s".equalsIgnoreCase(resposta));
        System.out.println(graphViz);
    }

    private void sair(){
        System.out.println("Programa finalizado.");
        System.exit(0);
    }
}
