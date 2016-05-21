package oroscopo.model;

public class OroscopoMensile implements Oroscopo, Comparable<Oroscopo> {

	private Previsione amore, lavoro, salute;
	private SegnoZodiacale segnoZodiacale;

	public OroscopoMensile(String nomeSegnoZodiacale, Previsione amore,
			Previsione lavoro, Previsione salute) {
		this(SegnoZodiacale.valueOf(nomeSegnoZodiacale.toUpperCase()), amore,
				lavoro, salute);
	}

	public OroscopoMensile(SegnoZodiacale segno, Previsione amore,
			Previsione lavoro, Previsione salute) {
		
		if(amore == null || lavoro == null || salute == null)
			throw new IllegalArgumentException("previsioni NULL non ammesse");
		
		if(!amore.validaPerSegno(segno) ||
				!lavoro.validaPerSegno(segno) ||
				!salute.validaPerSegno(segno)) 
			throw new IllegalArgumentException("previsioni devono essere valide per: " + segno);
		
		this.amore = amore;
		this.lavoro = lavoro;
		this.salute = salute;
		this.segnoZodiacale = segno;
	}

	public SegnoZodiacale getSegnoZodiacale() {
		return segnoZodiacale;
	}

	public Previsione getPrevisioneAmore() {
		return amore;
	}

	public Previsione getPrevisioneSalute() {
		return salute;
	}

	public Previsione getPrevisioneLavoro() {
		return lavoro;
	}
	
	public int getFortuna() {
		long val = Math.round((amore.getValore() + lavoro.getValore() + salute
				.getValore()) / 3.0);
		return (int) val;
	}

	public String toString() {
		return "Amore:\t" + amore.getPrevisione() + "\n" + "Lavoro:\t"
				+ lavoro.getPrevisione() + "\n" + "Salute:\t"
				+ salute.getPrevisione() + "\n";
	}

	public int compareTo(Oroscopo that) {
		int z1 = this.getSegnoZodiacale().ordinal(), z2 = that
				.getSegnoZodiacale().ordinal();
		return z1 < z2 ? -1 : (z1 == z2 ? 0 : 1);
	}

}
