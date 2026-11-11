package locadora.contrato;

/**
 * Decorador concreto: adiciona entrega em domicílio ao aluguel.
 */
public class ExtraEntrega extends DecoradorContrato {

    private static final double VALOR = 10.0;

    public ExtraEntrega(Contrato contrato) {
        super(contrato);
    }

    @Override
    public double calcularPreco() {
        return contrato.calcularPreco() + VALOR;
    }

    @Override
    public String getDescricao() {
        return contrato.getDescricao() + " + entrega em domicílio";
    }
}
