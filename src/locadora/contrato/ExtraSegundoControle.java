package locadora.contrato;

/**
 * Decorador concreto: adiciona um segundo controle ao aluguel.
 */
public class ExtraSegundoControle extends DecoradorContrato {

    private static final double VALOR = 5.0;

    public ExtraSegundoControle(Contrato contrato) {
        super(contrato);
    }

    @Override
    public double calcularPreco() {
        return contrato.calcularPreco() + VALOR;
    }

    @Override
    public String getDescricao() {
        return contrato.getDescricao() + " + segundo controle";
    }
}
