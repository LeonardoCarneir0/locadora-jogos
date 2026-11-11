package locadora.fabrica;

import locadora.modelo.Jogo;

/**
 * Creator abstrato do padrão Factory Method.
 *
 * O código cliente chama {@link #criarJogo(DadosJogo)} e a fábrica concreta
 * decide QUAL subclasse de Jogo será instanciada (físico ou digital).
 */
public abstract class FabricaJogo {

    /** Factory Method implementado pelas fábricas concretas. */
    protected abstract Jogo fabricar(DadosJogo dados);

    /** Operação "molde" usada pelo cliente. */
    public Jogo criarJogo(DadosJogo dados) {
        Jogo jogo = fabricar(dados);
        System.out.println("   [Factory] Jogo '" + jogo.getTipoMidia() + "' criado: " + jogo.getTitulo());
        return jogo;
    }
}
