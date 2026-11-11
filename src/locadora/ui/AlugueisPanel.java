package locadora.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import locadora.nucleo.Aluguel;
import locadora.observador.ObservadorLocadora;
import locadora.servico.Locadora;

/**
 * View + Controller da aba "Aluguéis".
 *
 * Demonstra o padrão State: os botões disparam "atrasar" e "devolver" e o
 * comportamento muda conforme o estado atual do aluguel. Observa a
 * {@link Locadora} para atualizar a tabela automaticamente.
 */
public class AlugueisPanel extends JPanel implements ObservadorLocadora {

    private final Locadora locadora;
    private final DefaultTableModel modeloTabela;
    private final JTable tabela;

    public AlugueisPanel(Locadora locadora) {
        this.locadora = locadora;
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(
                new Object[]{"Cliente", "Jogo", "Dias", "Estado", "Preço", "Descrição"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton botaoAtraso = new JButton("Simular atraso (State)");
        botaoAtraso.addActionListener(e -> acao(true));

        JButton botaoDevolver = new JButton("Devolver (State)");
        botaoDevolver.addActionListener(e -> acao(false));

        JPanel botoes = new JPanel(new FlowLayout());
        botoes.add(botaoAtraso);
        botoes.add(botaoDevolver);
        add(botoes, BorderLayout.SOUTH);

        atualizar();
    }

    private void acao(boolean atrasar) {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um aluguel na tabela.");
            return;
        }
        Aluguel aluguel = locadora.getAlugueis().get(linha);
        if (atrasar) {
            locadora.atrasar(aluguel);
        } else {
            locadora.devolver(aluguel);
        }
        // A Locadora notifica os observadores, e esta tabela se atualiza.
    }

    @Override
    public void atualizar() {
        modeloTabela.setRowCount(0);
        for (Aluguel aluguel : locadora.getAlugueis()) {
            modeloTabela.addRow(new Object[]{
                aluguel.getCliente(),
                aluguel.getJogo().getTitulo(),
                aluguel.getDias(),
                aluguel.getEstado(),
                String.format("R$ %.2f", aluguel.calcularPreco()),
                aluguel.getDescricao()
            });
        }
    }
}
