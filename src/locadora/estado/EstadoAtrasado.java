package locadora.estado;

import locadora.nucleo.Aluguel;

/**
 * Estado "ATRASADO": o prazo foi excedido e há multa por dia de atraso.
 */
public class EstadoAtrasado implements EstadoAluguel {

    private static final double MULTA_POR_DIA = 5.0;

    @Override
    public void devolver(Aluguel aluguel) {
        double multa = aluguel.getDiasAtraso() * MULTA_POR_DIA;

        aluguel.registrarMulta(multa);
        aluguel.setEstado(new EstadoDevolvido());

        System.out.printf("   [State] Devolução realizada! Multa: R$ %.2f%n", multa);
    }

    @Override
    public void atrasar(Aluguel aluguel, int diasAtraso) {
        System.out.println("   [State] O aluguel já está atrasado.");
    }

    @Override
    public String getNome() {
        return "ATRASADO";
    }
}
