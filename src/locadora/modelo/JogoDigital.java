package locadora.modelo;

/**
 * Produto concreto criado pela {@link locadora.fabrica.FabricaJogoDigital}.
 * Jogo em mídia digital, com um código de ativação.
 */
public class JogoDigital extends Jogo {

    private final String codigoAtivacao;

    public JogoDigital(String titulo, String genero, double precoBase, int classificacaoIndicativa) {
        super(titulo, genero, precoBase, classificacaoIndicativa);
        this.codigoAtivacao = gerarCodigo();
    }

    private String gerarCodigo() {
        return "DIG-" + (10000 + (int) (Math.random() * 90000));
    }

    public String getCodigoAtivacao() {
        return codigoAtivacao;
    }

    @Override
    public String getTipoMidia() {
        return "DIGITAL";
    }

    @Override
    public String toString() {
        return super.toString() + " (código " + codigoAtivacao + ")";
    }
}
