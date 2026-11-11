package locadora.fabrica;

import locadora.modelo.Jogo;
import locadora.modelo.JogoDigital;

/**
 * Creator concreto: cria {@link JogoDigital}.
 */
public class FabricaJogoDigital extends FabricaJogo {

    @Override
    protected Jogo fabricar(DadosJogo d) {
        // O campo "copias" não se aplica a jogos digitais.
        return new JogoDigital(d.titulo, d.genero, d.precoBase, d.classificacaoIndicativa);
    }
}
