package pharmame.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pharmame.model.Farmaco;

public class MyMainView extends JFrame implements MainView, ActionListener, ListSelectionListener {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	private JComboBox<String> filterCombo;
	private JTextField chiaveField;
	private JButton filterButton;
	private JTextArea output;

	private FarmacoTable farmacoTable;

	public MyMainView() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setupGui();
	}

	private void setupGui() {
		setLayout(new BorderLayout());
		
		JPanel upperPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		{
			upperPanel.add(new JLabel("Cerca Per:"));
			
			filterCombo = new JComboBox<String>();
			upperPanel.add(filterCombo);
			
			upperPanel.add(new JLabel("Chiave:"));
			
			chiaveField = new JTextField();
			upperPanel.add(chiaveField);
			
			upperPanel.add(new JPanel()); //Padding
			
			filterButton = new JButton("Filtra");
			filterButton.addActionListener(this);
			chiaveField.addActionListener(this);
			upperPanel.add(filterButton);
			
			output = new JTextArea(6,20); output.setEditable(false);
		}
		getContentPane().add(upperPanel, BorderLayout.PAGE_START);
		
		farmacoTable = new FarmacoTable();
		JScrollPane scrollPane = new JScrollPane(farmacoTable);
		
		farmacoTable.getSelectionModel().addListSelectionListener(this);
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(output, BorderLayout.PAGE_END);
		pack();
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	@Override
	public void setFilterNames(Collection<String> names) {
		filterCombo.removeAllItems();
		for (String name : names) {
			filterCombo.addItem(name);
		}
	}

	@Override
	public void setFarmaci(List<Farmaco> farmaci) {
		farmacoTable.setFarmaci(farmaci);
	}
	
	@Override
	public void setOutput(String[] righe) {
		output.setText("");
		for(String s: righe) output.append(s + '\n');
	}

	@Override
	public void setController(Controller c) {
		this.controller = c;		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.filterBy(filterCombo.getSelectedItem().toString(), chiaveField.getText());
	}
	
	@Override
	public Farmaco getFarmacoAt(int index) {
		return farmacoTable.getFarmacoAt(index);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) return; // prevents the event being served twice
		//else
		controller.printSelected(farmacoTable.getSelectedRows());
	}
}
