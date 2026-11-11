package locadora.contrato;

/**
 * Decorador concreto: adiciona um seguro contra danos ao aluguel.
 */
public class ExtraSeguro extends DecoradorContrato {

    private static final double VALOR = 3.0;

    public ExtraSeguro(Contrato contrato) {
        super(contrato);
    }

    @Override
    public double calcularPreco() {
        return contrato.calcularPreco() + VALOR;
    }

    @Override
    public String getDescricao() {
        return contrato.getDescricao() + " + seguro contra danos";
    }
}
