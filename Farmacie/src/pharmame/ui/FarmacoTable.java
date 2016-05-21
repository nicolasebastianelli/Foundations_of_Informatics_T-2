package pharmame.ui;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import pharmame.model.Farmaco;

class FarmacoTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	private static final NumberFormat CurrencyFormat = NumberFormat.getCurrencyInstance(Locale.ITALY);
	
	private List<Farmaco> farmaci;

	public FarmacoTableModel(List<Farmaco> farmaci) {
		if (farmaci == null)
			throw new IllegalArgumentException("farmaci == null");
		
		this.farmaci = farmaci;
	}
	
	@Override
	public int getColumnCount() {
		return 7;
	}
	
	@Override
	public String getColumnName(int col) {
		switch (col)
		{
		case 0: return "Codice";
		case 1: return "Nome";
		case 2: return "Confezione";
		case 3: return "Principio Attivo";
		case 4: return "Ditta";
		case 5: return "Prezzo";
		case 6: return "Gruppo Equivalenza";	
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int getRowCount() {
		return farmaci.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Farmaco f = farmaci.get(row);
		switch (col)
		{
		case 0: return f.getCodice();
		case 1: return f.getNome();
		case 2: return f.getConfezione();
		case 3: return f.getPrincipioAttivo();
		case 4: return f.getDitta();
		case 5: return CurrencyFormat.format(f.getPrezzo());
		case 6: return f.getGruppoEquivalenza();	
		default:
			throw new IllegalArgumentException();
		}
	}

	public Farmaco getFarmacoAt(int row) {
		return farmaci.get(row);

  }
}

public class FarmacoTable extends JTable {

	private static final long serialVersionUID = 1L;

	public FarmacoTable() {
		super(new FarmacoTableModel(new ArrayList<Farmaco>()));
	}
	
	public void setFarmaci(List<Farmaco> farmaci) {
		setModel(new FarmacoTableModel(farmaci));
	}
	
	public Farmaco getFarmacoAt(int row) {
		return ((FarmacoTableModel)(this.getModel())).getFarmacoAt(row);
	}

}
