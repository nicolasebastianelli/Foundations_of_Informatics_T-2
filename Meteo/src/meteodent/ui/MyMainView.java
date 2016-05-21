package meteodent.ui;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.Collection;
import java.util.Locale;

import javax.swing.*;

import meteodent.model.*;

public class MyMainView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final DateFormat currentDateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
	
	private Controller controller;
	
	private JComboBox<String> localitaComboBox;
	private JComboBox<String> dateComboBox;
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
				
				upperLeftPanel.add(new JLabel("Località"));
				
				localitaComboBox = new JComboBox<String>(getLocalita());
				localitaComboBox.addActionListener(this);
				upperLeftPanel.add(localitaComboBox);				
				
				upperLeftPanel.add(new JLabel("Date"));
				
				dateComboBox = new JComboBox<String>();
				upperLeftPanel.add(dateComboBox);
				
				button = new JButton("Previsioni");
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
		
		fillDate();
		setSize(500, 400);
	}

	private String[] getLocalita() {
		return controller.getLocalita().toArray(new String[0]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			showPrevisione();
		} else if (e.getSource() == localitaComboBox) {
			fillDate();
		}
	}

	private void fillDate() {
		Collection<Date> datePerLocalita = controller.getDatePerLocalita((String) localitaComboBox.getSelectedItem()); 
		dateComboBox.removeAllItems();
		for (Date date : datePerLocalita) {
			dateComboBox.addItem(currentDateFormat.format(date));
		}
	}

	private void showPrevisione() {
		String selectedLocalita = (String) localitaComboBox.getSelectedItem();
		Date selectedDate = null;
		try {
			selectedDate = currentDateFormat.parse((String) dateComboBox.getSelectedItem());			
		} catch (Exception e) {			
		}
		
		if (selectedDate == null) 
			return;
		
		Collection<Previsione> previsioni = controller.getPrevisioni(selectedLocalita, selectedDate);
		Annuncio annuncio = controller.getAnnuncio(selectedLocalita, selectedDate);
		
		StringBuilder sb = new StringBuilder();
		for (Previsione previsione : previsioni) {
			sb.append(previsione);
			sb.append("\n");
		}
		sb.append(annuncio.getTesto());
		textArea.setText(sb.toString());
	}
}
