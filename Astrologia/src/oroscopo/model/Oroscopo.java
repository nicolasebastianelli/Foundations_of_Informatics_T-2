package oroscopo.model;

public interface Oroscopo {
	
	public SegnoZodiacale getSegnoZodiacale();

	public Previsione getPrevisioneAmore();

	public Previsione getPrevisioneSalute();

	public Previsione getPrevisioneLavoro();

	public int getFortuna();
}
