package locadora.modelo;

/**
 * Produto abstrato do padrão Factory Method.
 * Representa um jogo disponível para aluguel na locadora.
 */
public abstract class Jogo {

    private final String titulo;
    private final String genero;
    private final double precoBase;
    private final int classificacaoIndicativa;

    public Jogo(String titulo, String genero, double precoBase, int classificacaoIndicativa) {
        this.titulo = titulo;
        this.genero = genero;
        this.precoBase = precoBase;
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public int getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public abstract String getTipoMidia();

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - R$ %.2f/dia - %d+",
                getTipoMidia(), titulo, genero, precoBase, classificacaoIndicativa);
    }
}
