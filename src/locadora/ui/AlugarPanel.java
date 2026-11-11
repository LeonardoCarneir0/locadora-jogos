package locadora.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import locadora.contrato.Contrato;
import locadora.contrato.ContratoBasico;
import locadora.contrato.ExtraEntrega;
import locadora.contrato.ExtraSegundoControle;
import locadora.contrato.ExtraSeguro;
import locadora.modelo.Jogo;
import locadora.nucleo.Aluguel;
import locadora.observador.ObservadorLocadora;
import locadora.servico.Locadora;
import locadora.servico.ServicoLocadora;

/**
 * View + Controller da aba "Alugar".
 *
 * Demonstra:
 *  - Decorator: os checkboxes montam a cadeia de extras sobre o contrato base.
 *  - Proxy: o aluguel passa pelo {@link ServicoLocadora}, que bloqueia +18.
 * Observa a {@link Locadora} para manter a lista de jogos atualizada.
 */
public class AlugarPanel extends JPanel implements ObservadorLocadora {

    private final Locadora locadora;
    private final ServicoLocadora servico;

    private final JComboBox<Jogo> comboJogos = new JComboBox<>();
    private final JTextField campoCliente = new JTextField();
    private final JTextField campoIdade = new JTextField("18");
    private final JTextField campoDias = new JTextField("3");
    private final JCheckBox chkControle = new JCheckBox("Segundo controle (+R$ 5)");
    private final JCheckBox chkSeguro = new JCheckBox("Seguro (+R$ 3)");
    private final JCheckBox chkEntrega = new JCheckBox("Entrega (+R$ 10)");
    private final JLabel labelStatus = new JLabel(" ");

    public AlugarPanel(Locadora locadora, ServicoLocadora servico) {
        this.locadora = locadora;
        this.servico = servico;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 6, 12));
        formulario.add(new JLabel("Jogo:"));
        formulario.add(comboJogos);
        formulario.add(new JLabel("Cliente:"));
        formulario.add(campoCliente);
        formulario.add(new JLabel("Idade:"));
        formulario.add(campoIdade);
        formulario.add(new JLabel("Dias:"));
        formulario.add(campoDias);

        JPanel extras = new JPanel(new FlowLayout(FlowLayout.LEFT));
        extras.setBorder(BorderFactory.createTitledBorder("Extras (Decorator)"));
        extras.add(chkControle);
        extras.add(chkSeguro);
        extras.add(chkEntrega);

        JButton botaoAlugar = new JButton("Alugar (Proxy + Decorator)");
        botaoAlugar.addActionListener(e -> alugar());

        JPanel painelBotao = new JPanel();
        painelBotao.add(botaoAlugar);

        labelStatus.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));

        add(formulario);
        add(extras);
        add(painelBotao);
        add(labelStatus);
    }

    private void alugar() {
        Jogo jogo = (Jogo) comboJogos.getSelectedItem();
        if (jogo == null) {
            JOptionPane.showMessageDialog(this, "Cadastre um jogo antes de alugar.");
            return;
        }
        String cliente = campoCliente.getText().trim();
        if (cliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome do cliente.");
            return;
        }
        try {
            int idade = Integer.parseInt(campoIdade.getText().trim());
            int dias = Integer.parseInt(campoDias.getText().trim());

            // Decorator: monta a cadeia contrato base -> extras
            Contrato contrato = new ContratoBasico(jogo, dias);
            if (chkControle.isSelected()) {
                contrato = new ExtraSegundoControle(contrato);
            }
            if (chkSeguro.isSelected()) {
                contrato = new ExtraSeguro(contrato);
            }
            if (chkEntrega.isSelected()) {
                contrato = new ExtraEntrega(contrato);
            }

            // Proxy: passa pelo serviço que verifica a idade
            Aluguel aluguel = servico.alugar(cliente, idade, jogo, dias, contrato);
            if (aluguel == null) {
                labelStatus.setText("Bloqueado: '" + jogo.getTitulo() + "' é +18 e " + cliente + " tem " + idade + " anos.");
            } else {
                labelStatus.setText("Alugado! Total: R$ " + String.format("%.2f", aluguel.calcularPreco())
                        + " - " + aluguel.getDescricao());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade e dias devem ser números.");
        }
    }

    @Override
    public void atualizar() {
        Jogo selecionado = (Jogo) comboJogos.getSelectedItem();
        comboJogos.removeAllItems();
        for (Jogo jogo : locadora.getCatalogo()) {
            comboJogos.addItem(jogo);
        }
        if (selecionado != null) {
            comboJogos.setSelectedItem(selecionado);
        }
    }
}
