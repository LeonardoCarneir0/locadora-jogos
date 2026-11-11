package locadora.fabrica;

import locadora.modelo.Jogo;
import locadora.modelo.JogoFisico;

/**
 * Creator concreto: cria {@link JogoFisico}.
 */
public class FabricaJogoFisico extends FabricaJogo {

    @Override
    protected Jogo fabricar(DadosJogo d) {
        return new JogoFisico(d.titulo, d.genero, d.precoBase, d.classificacaoIndicativa, d.copias);
    }
}
