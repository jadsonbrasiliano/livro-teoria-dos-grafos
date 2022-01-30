package grafo.core;

public class Vertice {

    private String rotulo;
    private int grau;

    Vertice(String rotulo) throws Exception {
        boolean isRouloNullOrBlank = rotulo == null || "".equals(rotulo.trim());
        if (isRouloNullOrBlank){
            throw new Exception("Não é permitida a inclusão de vértices com rótulo em branco ou nulo!");
        }
        this.rotulo = rotulo;
    }

    void addGrau(){
        grau++;
    }

    public String getRotulo() {
        return this.rotulo;
    }

    public int getGrau() {
        return grau;
    }
}
