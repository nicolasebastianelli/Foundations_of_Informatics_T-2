package teethcollege.model;

import java.util.ArrayList;
import java.util.List;

public class PianoDiStudi {

	private String nome, cognome, matricola;
	private long matricolaAsLong;
	private int sommaCrediti;
	private List<Insegnamento> pianoDiStudi;

	public PianoDiStudi(String nome, String cognome, String matricola) {
		if (nome == null || nome.isEmpty())
			throw new IllegalArgumentException("nome");
		if (cognome == null || cognome.isEmpty())
			throw new IllegalArgumentException("cognome");
		if (matricola == null || matricola.isEmpty())
			throw new IllegalArgumentException("matricola");
		try {
			matricolaAsLong = Long.parseLong(matricola);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("matricola", e);
		}

		this.cognome = cognome;
		this.nome = nome;
		this.matricola = matricola;
		this.pianoDiStudi = new ArrayList<Insegnamento>();
		sommaCrediti = 0;
	}

	public List<Insegnamento> getInsegnamenti() {
		return pianoDiStudi;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getMatricola() {
		return matricola;
	}

	public long getMatricolaAsLong() {
		return matricolaAsLong;
	}

	public void aggiungiInsegnamento(Insegnamento corso) {
		if (this.pianoDiStudi.contains(corso))
			throw new IllegalArgumentException();
		
		this.pianoDiStudi.add(corso);
		this.sommaCrediti += corso.getCfu();
	}

	public boolean isValid() {
		return sommaCrediti() >= 180;
	}

	private int sommaCrediti() {
		int somma = 0;
		for (Insegnamento i : pianoDiStudi)
			somma += i.getCfu();
		return somma;
	}

	public int getSommaCrediti() {
		return sommaCrediti;
	}

	public String toString() {
		return matricola + " " + cognome + " " + nome;
	}

	public String getCognomeNome() {
		return cognome + " " + nome;
	}

	public String toFullString() {
		StringBuilder sb = new StringBuilder();
		sb.append("STUDENTE: " + getCognome() + " " + getNome()
				+ " (matricola: " + getMatricola() + ")\n");
		for (Insegnamento i : getInsegnamenti())
			sb.append(i.toString() + "\n");
		sb.append("Totale crediti in carriera: " + this.getSommaCrediti());
		return sb.toString();
	}

	public void sostituisciInsegnamento(Insegnamento corrente,
			Insegnamento futuro) throws IllegalArgumentException {
		if (!getInsegnamenti().contains(corrente))
			throw new IllegalArgumentException(
					"Insegnamento da sostituire non presente nel piano di studi");
		if (getInsegnamenti().contains(futuro))
			throw new IllegalArgumentException(
					"Insegnamento da inserire già presente nel piano di studi");
		if (corrente.getCategoria() == Categoria.OBBLIGATORIO)
			throw new IllegalArgumentException(
					"Un insegnamento obbligatorio non può essere sostituito");
		if (corrente.getCfu() != futuro.getCfu())
			throw new IllegalArgumentException(
					"L'insegnamento da sostituire deve avere egual numero di crediti");
		if (futuro.getCategoria() == Categoria.OBBLIGATORIO)
			throw new IllegalArgumentException(
					"L'insegnamento con cui sostituire deve essere a scelta");

		getInsegnamenti().remove(corrente);
		getInsegnamenti().add(futuro);
	}
}
