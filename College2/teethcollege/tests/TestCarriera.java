package teethcollege.tests;
import java.io.*;
import java.util.Date;
import java.util.Map;

import teethcollege.model.Carriera;
import teethcollege.model.Esame;
import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;
import teethcollege.persistence.InsegnamentiReader;
import teethcollege.persistence.MyInsegnamentiReader;

public class TestCarriera {
	public static void main(String[] args) {
		InsegnamentiReader insReader = null;
		Map<Long,Insegnamento> mappaCorsi = null;
		try	{
			// carico comunque gli insegnamenti perché mi servono
			insReader = new MyInsegnamentiReader(new FileReader("Insegnamenti.txt"));
			mappaCorsi = insReader.caricaInsegnamenti();	
		}
		catch (IOException e){
			System.err.println(e);
		}
		PianoDiStudi piano = null;
		try	{
			// ora costruisco il piano di studi di prova
			piano = new PianoDiStudi("PAOLINO", "PAPERINO", "123456789");
			long[] tuttiGliEsami = {27991, 28004, 29228, 27993, 27996, 28006, 28011, 26338,
									28012, 28000, 28032, 28027, 28030, 28029, 28014, 28020,
									28024, 28016, 28015, 28021, 17268, 28072, 28659
									};
			for (long codice : tuttiGliEsami) piano.aggiungiInsegnamento(mappaCorsi.get(codice));
			if (!piano.isValid()) throw new IllegalArgumentException("Piano di studi non valido per " + piano.getCognomeNome() + ": crediti = "+ piano.getSommaCrediti());
			else { 
				//System.out.println(piano.toFullString());
			}
		}
		catch (IllegalArgumentException e1){
			System.out.println(e1.getMessage());
		}
		// ora costruisco la carriera vera e propria
		Carriera c = new Carriera(piano);
		c.addEsame(new Esame(mappaCorsi.get(27991),31,new Date()));
		c.addEsame(new Esame(mappaCorsi.get(28004),18,new Date()));
		c.addEsame(new Esame(mappaCorsi.get(29228),16,new Date()));
		c.addEsame(new Esame(mappaCorsi.get(29228),22,new Date()));
		System.out.println(c.toFullString(true)); // true = mostra dettagli; false = non mostrare dettagli 
		// ora tento di inserire un esame già superato
		try{
			c.addEsame(new Esame(mappaCorsi.get(28004),22,new Date()));
		}
		catch (IllegalArgumentException e2){
			System.out.println(e2.getMessage());
		}
		// ora tento di inserire un esame per un insegnamento inesistente in carriera
		try{
			c.addEsame(new Esame(mappaCorsi.get(99999),22,new Date()));
		}
		catch (IllegalArgumentException e2){
			System.out.println(e2.getMessage());
		}
	}
}
