
package locadora.modelo;

/**
 * Produto concreto criado pela FabricaJogoFisico.
 * Jogo em mídia física, com estoque de cópias.
 */
public class JogoFisico extends Jogo {

    private int copias;

    public JogoFisico(
            String titulo,
            String genero,
            double precoBase,
            int classificacaoIndicativa,
            int copias) {

        super(titulo, genero, precoBase, classificacaoIndicativa);

        if (copias < 0) {
            throw new IllegalArgumentException(
                "A quantidade de cópias não pode ser negativa."
            );
        }

        this.copias = copias;
    }

    public int getCopias() {
        return copias;
    }

    // Verifica se existe alguma cópia disponível.
    public boolean temCopiaDisponivel() {
        return copias > 0;
    }

    // Retira uma cópia quando o jogo é alugado.
    public void retirarCopia() {
        if (!temCopiaDisponivel()) {
            throw new IllegalStateException(
                "Não existem cópias disponíveis."
            );
        }

        copias--;
    }

    // Devolve uma cópia quando o aluguel é encerrado.
    public void devolverCopia() {
        copias++;
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
