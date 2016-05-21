package zannotaxi.ui;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class JCorsaTaxiPane extends JScrollPane {
	
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	
	public final static int LINEE = 5;
	
	public JCorsaTaxiPane() {
		Object[] titoliColonne = {"Voce", "Valore" };
		
		Object[][] rowData = new Object[LINEE][2];
		for(int i=0; i<LINEE; i++) {
			rowData[i][0] = "";
			rowData[i][1] = "";
		}	
		
		table = new JTable(rowData, titoliColonne);
		getViewport().add(table, null);
		setPreferredSize(new Dimension(300,105));
		((JLabel) table.getDefaultRenderer(Object.class)).setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public void update(String[][] lineeDiCosto) {
		
		for(int i=0; i<LINEE; i++) {
			if(i==lineeDiCosto.length) break;
			table.setValueAt(lineeDiCosto[i][0], i, 0);
			table.setValueAt(lineeDiCosto[i][1], i, 1);
		}		
	}
}
