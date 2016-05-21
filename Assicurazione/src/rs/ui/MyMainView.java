package rs.ui;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.*;

import rs.model.*;

public class MyMainView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	private JComboBox<String> cittaComboBox;
	private JComboBox<Integer> annoComboBox;
	private JTextArea textArea;
	private JButton button;

	public MyMainView(Controller controller) {
		this.controller = controller;
		
		createGui();
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	private void createGui() {
		JPanel leftPanel = new JPanel();
		{
			JPanel upperLeftPanel = new JPanel();
			{
				upperLeftPanel.setLayout(new BoxLayout(upperLeftPanel, BoxLayout.PAGE_AXIS));
				
				upperLeftPanel.add(new JLabel("Città"));
				
				cittaComboBox = new JComboBox<String>(getCitta());
				cittaComboBox.addActionListener(this);
				upperLeftPanel.add(cittaComboBox);				
				
				upperLeftPanel.add(new JLabel("Anni"));
				
				annoComboBox = new JComboBox<Integer>();
				upperLeftPanel.add(annoComboBox);
				
				button = new JButton("Stime");
				button.addActionListener(this);
				upperLeftPanel.add(button);
			}
			leftPanel.add(upperLeftPanel, BorderLayout.PAGE_START);			
		}
		getContentPane().add(leftPanel, BorderLayout.LINE_START);
		
		JPanel contentPanel = new JPanel();
		{
			contentPanel.setLayout(new BorderLayout());
			textArea = new JTextArea();
			textArea.setWrapStyleWord(true);
			textArea.setLineWrap(true);
			contentPanel.add(textArea, BorderLayout.CENTER);
		}
		getContentPane().add(contentPanel, BorderLayout.CENTER);	
		
		fillAnni();
		setSize(500, 400);
	}

	private String[] getCitta() {
		return controller.getCitta().toArray(new String[0]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			showPrevisione();
		} else if (e.getSource() == cittaComboBox) {
			fillAnni();
		}
	}

	private void fillAnni() {
		Collection<Integer> anniPerLocalita = controller.getAnniPerCitta((String) cittaComboBox.getSelectedItem()); 
		annoComboBox.removeAllItems();
		for (Integer anno : anniPerLocalita) {
			annoComboBox.addItem(anno);
		}
	}

	private void showPrevisione() {
		String selectedCitta = (String) cittaComboBox.getSelectedItem();
		Integer selectedAnno = (Integer) annoComboBox.getSelectedItem();
		if (selectedAnno == null) 
			return;
		
		Collection<StimaRischio> stimeRischio = controller.getStimeRischio(selectedCitta, selectedAnno);
		StimaSintetica stimaSintetica = controller.getStimaSintetica(selectedCitta, selectedAnno);
		
		StringBuilder sb = new StringBuilder();
		for (StimaRischio stimaRischio : stimeRischio) {
			sb.append(stimaRischio);
			sb.append("\n");
		}
		sb.append(stimaSintetica);
		textArea.setText(sb.toString());
	}
}
