package locadora.ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import locadora.fabrica.DadosJogo;
import locadora.fabrica.FabricaJogo;
import locadora.fabrica.FabricaJogoDigital;
import locadora.fabrica.FabricaJogoFisico;
import locadora.modelo.Jogo;
import locadora.servico.Locadora;

/**
 * Diálogo de cadastro de jogo (Controller).
 *
 * Usa o padrão Factory Method: conforme o tipo escolhido (físico/digital),
 * seleciona a fábrica correta para criar o {@link Jogo}.
 */
public class CadastroJogoDialog extends JDialog {

    private final Locadora locadora;

    private final JRadioButton radioFisico = new JRadioButton("Físico", true);
    private final JRadioButton radioDigital = new JRadioButton("Digital");
    private final JTextField campoTitulo = new JTextField();
    private final JTextField campoGenero = new JTextField();
    private final JTextField campoPreco = new JTextField();
    private final JTextField campoClassificacao = new JTextField("10");
    private final JTextField campoCopias = new JTextField("1");

    public CadastroJogoDialog(Locadora locadora) {
        super((Frame) null, "Cadastrar jogo (Factory Method)", true);
        this.locadora = locadora;

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radioFisico);
        grupo.add(radioDigital);
        radioFisico.addActionListener(e -> campoCopias.setEnabled(true));
        radioDigital.addActionListener(e -> campoCopias.setEnabled(false));

        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        formulario.add(new JLabel("Tipo:"));
        JPanel painelTipo = new JPanel();
        painelTipo.add(radioFisico);
        painelTipo.add(radioDigital);
        formulario.add(painelTipo);
        formulario.add(new JLabel("Título:"));
        formulario.add(campoTitulo);
        formulario.add(new JLabel("Gênero:"));
        formulario.add(campoGenero);
        formulario.add(new JLabel("Preço base (R$/dia):"));
        formulario.add(campoPreco);
        formulario.add(new JLabel("Classificação indicativa:"));
        formulario.add(campoClassificacao);
        formulario.add(new JLabel("Cópias (só físico):"));
        formulario.add(campoCopias);

        JButton salvar = new JButton("Salvar");
        salvar.addActionListener(e -> salvar());
        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> dispose());

        JPanel botoes = new JPanel();
        botoes.add(salvar);
        botoes.add(cancelar);

        add(formulario, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void salvar() {
        try {
            String titulo = campoTitulo.getText().trim();
            String genero = campoGenero.getText().trim();
            if (titulo.isEmpty() || genero.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha título e gênero.");
                return;
            }
            double preco = Double.parseDouble(campoPreco.getText().trim().replace(',', '.'));
            int classificacao = Integer.parseInt(campoClassificacao.getText().trim());

            FabricaJogo fabrica = radioFisico.isSelected()
                    ? new FabricaJogoFisico()
                    : new FabricaJogoDigital();
            int copias = radioFisico.isSelected()
                    ? Integer.parseInt(campoCopias.getText().trim())
                    : 0;

            Jogo jogo = fabrica.criarJogo(new DadosJogo(titulo, genero, preco, classificacao, copias));
            locadora.adicionarJogo(jogo);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço, classificação e cópias devem ser números.");
        }
    }
}
