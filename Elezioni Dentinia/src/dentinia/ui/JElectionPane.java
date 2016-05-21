package dentinia.ui;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import dentinia.model.*;


public class JElectionPane extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	
	public JElectionPane(List<Partito> listaPartiti) {
		Object[] titoliColonne = {"Partito", "Voti", "Seggi"};
		Object[][] rowData = new Object[listaPartiti.size()][3];
		for (int i=0; i<listaPartiti.size(); i++){
			Partito p = listaPartiti.get(i);
			rowData[i][0] = p.getNome();
			rowData[i][1] = p.getVoti();
			rowData[i][2] = p.getSeggi();
		}
		
		table = new JTable(rowData, titoliColonne);
		getViewport().add(table, null);
		setPreferredSize(new Dimension(300,105));
		((JLabel) table.getDefaultRenderer(Object.class)).setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public void update(List<Partito> listaPartiti) {
		for (int i=0; i<listaPartiti.size(); i++){
			Partito p = listaPartiti.get(i);
			table.setValueAt(p.getNome(), i,0);
			table.setValueAt(p.getVoti(), i,1);
			table.setValueAt(p.getSeggi(),i,2);
		}
	}
}
