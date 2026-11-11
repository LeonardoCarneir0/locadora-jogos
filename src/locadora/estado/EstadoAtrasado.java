package locadora.estado;

import locadora.nucleo.Aluguel;

/**
 * Estado "ATRASADO": o prazo foi excedido e há multa.
 */
public class EstadoAtrasado implements EstadoAluguel {

    private static final double MULTA_POR_DIA = 5.0;

    @Override
    public void devolver(Aluguel aluguel) {
        double multa = aluguel.getDias() * MULTA_POR_DIA;
        aluguel.setEstado(new EstadoDevolvido());
        System.out.printf("   [State] Aluguel devolvido com multa de R$ %.2f.%n", multa);
    }

    @Override
    public void atrasar(Aluguel aluguel) {
        System.out.println("   [State] O aluguel já está atrasado.");
    }

    @Override
    public String getNome() {
        return "ATRASADO";
    }
}
