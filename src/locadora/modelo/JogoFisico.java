package locadora.modelo;

/**
 * Produto concreto criado pela {@link locadora.fabrica.FabricaJogoFisico}.
 * Jogo em mídia física, com um estoque de cópias.
 */
public class JogoFisico extends Jogo {

    private int copias;

    public JogoFisico(String titulo, String genero, double precoBase, int classificacaoIndicativa, int copias) {
        super(titulo, genero, precoBase, classificacaoIndicativa);
        this.copias = copias;
    }

    public int getCopias() {
        return copias;
    }

    public void setCopias(int copias) {
        this.copias = copias;
    }

    @Override
    public String getTipoMidia() {
        return "FÍSICO";
    }

    @Override
    public String toString() {
        return super.toString() + " (" + copias + " cópias)";
    }
}
