package locadora.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import locadora.modelo.Jogo;
import locadora.modelo.JogoDigital;
import locadora.modelo.JogoFisico;
import locadora.observador.ObservadorLocadora;
import locadora.servico.Locadora;

/**
 * View + Controller da aba "Catálogo".
 *
 * Observa a {@link Locadora} e se atualiza automaticamente quando o catálogo
 * muda (padrão Observer). O botão "Cadastrar" abre o {@link CadastroJogoDialog}.
 */
public class CatalogoPanel extends JPanel implements ObservadorLocadora {

    private final Locadora locadora;
    private final DefaultTableModel modeloTabela;
    private final JTable tabela;

    public CatalogoPanel(Locadora locadora) {
        this.locadora = locadora;
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(
                new Object[]{"Título", "Gênero", "Mídia", "R$/dia", "Class.", "Detalhe"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton botaoCadastrar = new JButton("Cadastrar jogo (Factory Method)");
        botaoCadastrar.addActionListener(e -> new CadastroJogoDialog(locadora).setVisible(true));
        add(botaoCadastrar, BorderLayout.SOUTH);

        atualizar();
    }

    @Override
    public void atualizar() {
        modeloTabela.setRowCount(0);
        for (Jogo jogo : locadora.getCatalogo()) {
            modeloTabela.addRow(new Object[]{
                jogo.getTitulo(),
                jogo.getGenero(),
                jogo.getTipoMidia(),
                String.format("R$ %.2f", jogo.getPrecoBase()),
                jogo.getClassificacaoIndicativa() + "+",
                detalhe(jogo)
            });
        }
    }

    private String detalhe(Jogo jogo) {
        if (jogo instanceof JogoFisico fisico) {
            return fisico.getCopias() + " cópias";
        }
        if (jogo instanceof JogoDigital digital) {
            return "código " + digital.getCodigoAtivacao();
        }
        return "";
    }
}
