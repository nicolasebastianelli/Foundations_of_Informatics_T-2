package gattovolpe.holidays.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static gattovolpe.utils.DateUtil.*;

public class Pacchetto {
	
	private TipoPacchetto tipo;
	private String nome;
	private Destinazione destinazione;
	private Date dataInizio;
	private int durataGiorni;
	private double costo;
	private String descrizione;
	
	public boolean isInPeriod(Date start, Date end)
	{
		if(tipo == TipoPacchetto.SOLOVOLO) {
			
			return normalizeDate(start).equals(normalizeDate(dataInizio));

		} else {
			
			boolean startValid = 
					getDateDifference(start, dataInizio)>=0;
			boolean endValid = true;
			
			boolean hasEndDate = end != null;
			
			if(hasEndDate) {
			
				GregorianCalendar dataFineCalendar =
						new GregorianCalendar();
				dataFineCalendar.setTime(dataInizio);
				dataFineCalendar.add(Calendar.DATE, durataGiorni);
				
				endValid =
						getDateDifference(dataFineCalendar.getTime(), end)>=0;
			}
			
			return startValid & endValid;
		}
	}

	public TipoPacchetto getTipo() {
		return tipo;
	}

	public void setTipo(TipoPacchetto tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Destinazione getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(Destinazione destinazione) {
		this.destinazione = destinazione;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public int getDurataGiorni() {
		return durataGiorni;
	}

	public void setDurataGiorni(int durataGiorni) {
		this.durataGiorni = durataGiorni;
	}


	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	@Override
	public String toString() {
		return nome + "-" + destinazione.getLuogo();
	}

}
