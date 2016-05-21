package gattovolpe.holidays.model;

public class Destinazione {
	
	private String stato;
	private String luogo;
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public String getLuogo() {
		return luogo;
	}
	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Destinazione) {
			return ((Destinazione)o).toString().toLowerCase()
					.equals(this.toString().toLowerCase());
		} else return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.toString().toLowerCase().hashCode();
	}
	
	@Override
	public String toString() {
		return stato + "," + luogo; 
	}

}