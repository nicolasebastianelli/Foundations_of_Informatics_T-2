package mm.model;

import java.awt.Color;

public enum Colore
{
	Rosso(Color.red),
	Verde(Color.green),
	Grigio(Color.gray),
	Magenta(Color.magenta),
	Blu(Color.blue),
	Ciano(Color.cyan),
	Giallo(Color.yellow),
	Arancio(Color.orange);
	
	private Color innerColor;
	
	private Colore(Color innerColor)
	{
		this.innerColor = innerColor;
	}
	
	public Color getInnerColor()
	{
		return innerColor;
	}
}
