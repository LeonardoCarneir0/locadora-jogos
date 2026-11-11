package locadora.contrato;

import locadora.modelo.Jogo;

/**
 * Componente concreto do Decorator: o aluguel "puro", sem extras.
 */
public class ContratoBasico implements Contrato {

    private final Jogo jogo;
    private final int dias;

    public ContratoBasico(Jogo jogo, int dias) {
        this.jogo = jogo;
        this.dias = dias;
    }

    @Override
    public double calcularPreco() {
        return jogo.getPrecoBase() * dias;
    }

    @Override
    public String getDescricao() {
        return String.format("%s por %d dia(s)", jogo.getTitulo(), dias);
    }
}
