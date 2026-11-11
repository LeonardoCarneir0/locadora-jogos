package locadora.fabrica;

/**
 * Objeto de transporte com os dados necessários para criar um jogo.
 * Evita passar muitos parâmetros para o Factory Method.
 */
public class DadosJogo {

    public final String titulo;
    public final String genero;
    public final double precoBase;
    public final int classificacaoIndicativa;
    /** Usado apenas por jogos físicos. */
    public final int copias;

    public DadosJogo(String titulo, String genero, double precoBase, int classificacaoIndicativa, int copias) {
        this.titulo = titulo;
        this.genero = genero;
        this.precoBase = precoBase;
        this.classificacaoIndicativa = classificacaoIndicativa;
        this.copias = copias;
    }
}
