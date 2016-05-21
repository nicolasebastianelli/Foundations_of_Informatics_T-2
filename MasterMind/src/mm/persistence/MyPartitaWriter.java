package mm.persistence;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

import mm.model.Codice;
import mm.model.CodiceRisposta;
//import mm.model.ColoreRisposta;
import mm.model.Partita;
//import mm.model.Risposta;

public class MyPartitaWriter implements PartitaWriter
{

	@Override
	public void write(Partita partita, PrintWriter dest) throws IOException
	{
		if (partita == null)
			throw new IllegalArgumentException();
		
		BufferedWriter writer = new BufferedWriter(dest);
		
		writeCodice(partita.getSegreto(), writer);
		writer.newLine();
		writer.newLine();
		
		for (CodiceRisposta giocata : partita.getGiocate())
		{
			writeCodice(giocata.getTentativo(), writer);
//			writeRisposta(giocata.getRisposta(), writer);
			writer.newLine();
		}
		
		writer.close();
	}
	
	private void writeCodice(Codice codice, BufferedWriter writer) throws IOException
	{
		for (int i = 0; i < codice.getCount(); i++)
		{
			writer.write(codice.getColore(i).toString());
			if (i < codice.getCount() - 1)
			{
				writer.write(", ");
			}
		}
	}
	
//	private void writeRisposta(Risposta risposta, BufferedWriter writer) throws IOException
//	{
//		for (int i = 0; i < risposta.getCount(); i++)
//		{
//			ColoreRisposta coloreRisposta = risposta.getColoreRisposta(i);
//			writer.write(coloreRisposta.toString());
//			if (i < risposta.getCount() - 1)
//			{
//				writer.write(" | ");
//			}
//		}
//		if (risposta.allMatch())
//		{
//			writer.write(" ****");
//		}
//	}

}
