package dentinia.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JPanel mainPanel;
	private JElectionPane electionTable;
	private JComboBox<String> meccanismo;
	private JButton save;

	public MainFrame(Controller controller) {
		this.controller = controller;
		initGUI();
		setSize(500, 350);
	}

	private void initGUI() {

		mainPanel = new JPanel();
		getContentPane().add(mainPanel);

		setTitle("Elezioni di Dentinia");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JLabel label = new JLabel("Elezioni Dentinia - Assegnazione Seggi");
		mainPanel.add(label, BorderLayout.NORTH);

		electionTable = new JElectionPane(controller.getListaPartiti());
		mainPanel.add(electionTable, BorderLayout.CENTER);

		JPanel commandPanel = new JPanel();
		GridLayout commandPanelLayout = new GridLayout(1, 3);
		commandPanelLayout.setHgap(5);
		commandPanelLayout.setVgap(5);
		commandPanel.setLayout(commandPanelLayout);

		JLabel label2 = new JLabel("Metodo di calcolo:");
		commandPanel.add(label2);

		meccanismo = new JComboBox<String>(controller.getCalcolatoriSeggi());
		meccanismo.addActionListener(this);
		commandPanel.add(meccanismo);

		save = new JButton("Salva");
		save.addActionListener(this);
		commandPanel.add(save);

		mainPanel.add(commandPanel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == meccanismo) {

			controller.ricalcola((String) meccanismo.getSelectedItem());
			electionTable.update(controller.getListaPartiti());

		} else if (e.getSource() == save) {

			JFileChooser chooser = new JFileChooser((String) null);
			int res = chooser.showSaveDialog(null);
			if (res == JFileChooser.APPROVE_OPTION) {
				try {
					controller.salvaSuFile(chooser.getSelectedFile()
							.getAbsolutePath());
				} catch (IOException e1) {
					return;
				}
			}
		}

	}
}
