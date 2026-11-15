
package locadora.estado;

import locadora.nucleo.Aluguel;

public class EstadoAlugado implements EstadoAluguel {

    @Override
    public void devolver(Aluguel aluguel) {
        aluguel.setEstado(new EstadoDevolvido());
        System.out.println("   [State] Aluguel devolvido sem multa.");
    }

    @Override
    public void atrasar(Aluguel aluguel, int diasAtraso) {
        if (diasAtraso <= 0) {
            System.out.println("Informe uma quantidade de dias válida.");
            return;
        }

        aluguel.setDiasAtraso(diasAtraso);
        aluguel.setEstado(new EstadoAtrasado());

        System.out.println("   [State] Aluguel marcado como ATRASADO.");
    }

    @Override
    public String getNome() {
        return "ALUGADO";
    }
}
