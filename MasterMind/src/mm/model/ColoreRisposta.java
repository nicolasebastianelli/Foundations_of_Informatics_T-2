package mm.model;

import java.awt.Color;

public enum ColoreRisposta
{
	Nero(Color.black),
	Bianco(Color.white);
	
	private Color innerColor;
	
	private ColoreRisposta(Color innerColor)
	{
		this.innerColor = innerColor;
	}
	
	public Color getInnerColor()
	{
		return innerColor;
	}
}
