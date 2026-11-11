package locadora.nucleo;

import locadora.contrato.Contrato;
import locadora.estado.EstadoAlugado;
import locadora.estado.EstadoAluguel;
import locadora.modelo.Jogo;

/**
 * Contexto do padrão State e "cliente" do padrão Decorator.
 *
 * - State: mantém um {@link EstadoAluguel}; as ações "devolver" e "atrasar"
 *   têm comportamento diferente conforme o estado atual.
 * - Decorator: guarda um {@link Contrato} (básico + extras) e delega a ele
 *   o cálculo do preço e a descrição do aluguel.
 */
public class Aluguel {

    private final String cliente;
    private final Jogo jogo;
    private final int dias;
    private final Contrato contrato;
    private EstadoAluguel estado;

    public Aluguel(String cliente, Jogo jogo, int dias, Contrato contrato) {
        this.cliente = cliente;
        this.jogo = jogo;
        this.dias = dias;
        this.contrato = contrato;
        this.estado = new EstadoAlugado(); // todo aluguel começa ALUGADO
    }

    public double calcularPreco() {
        return contrato.calcularPreco();
    }

    public String getDescricao() {
        return contrato.getDescricao();
    }

    public void devolver() {
        estado.devolver(this);
    }

    public void atrasar() {
        estado.atrasar(this);
    }

    public void setEstado(EstadoAluguel estado) {
        this.estado = estado;
    }

    public String getCliente() {
        return cliente;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public int getDias() {
        return dias;
    }

    public String getEstado() {
        return estado.getNome();
    }

    @Override
    public String toString() {
        return String.format("%-12s -> %-22s | %2d dia(s) | %-9s | R$ %6.2f | %s",
                cliente, jogo.getTitulo(), dias, estado.getNome(),
                calcularPreco(), getDescricao());
    }
}
