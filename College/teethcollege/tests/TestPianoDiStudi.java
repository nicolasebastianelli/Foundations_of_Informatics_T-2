package teethcollege.tests;
import java.io.*;
import java.util.Map;

import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;
import teethcollege.persistence.InsegnamentiReader;
import teethcollege.persistence.MyInsegnamentiReader;

public class TestPianoDiStudi {
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
		PianoDiStudi c = null;
		try	{
			// ora costruisco il piano di studi di prova
			c = new PianoDiStudi("PAOLINO", "PAPERINO", "123456789");
			long[] tuttiGliEsami = {27991, 28004, 29228, 27993, 27996, 28006, 28011, 26338,
									28012, 28000, 28032, 28027, 28030, 28029, 28014, 28020,
									28024, 28016, 28015, 28021, 17268, 28072, 28659
									};
			for (long codice : tuttiGliEsami) c.aggiungiInsegnamento(mappaCorsi.get(codice));
			if (!c.isValid()) throw new IllegalArgumentException("Piano di studi non valido per " + c.getCognomeNome() + ": crediti = "+ c.getSommaCrediti());
			else System.out.println(c.toFullString());
		}
		catch (IllegalArgumentException e1){
			System.out.println(e1.getMessage());
		}
		try	{
			// ora sostituisco un esame: Tirocinio (28074) al posto di Amministrazione di Sistemi (28072)
			c.sostituisciInsegnamento(mappaCorsi.get(28072L), mappaCorsi.get(28074L));
			System.out.println(c.toFullString());
		}
		catch (IllegalArgumentException e2){
			System.out.println("Sostituzione insegnamento negata: " + e2);
		}
		try	{
			// ora provo a sostituire un esame obbligatorio: Affidabilità (38378) al posto di Elettrotecnica (28029)
			// EXCEPTION: Un insegnamento obbligatorio non può essere sostituito
			c.sostituisciInsegnamento(mappaCorsi.get(28029L), mappaCorsi.get(38378L));
			System.out.println(c);
		}
		catch (IllegalArgumentException e2){
			System.out.println("Sostituzione insegnamento negata: " + e2);
		}
		try	{
			// ora provo a sostituire un esame già presente: Ing. sw (28021) al posto di Tirocinio (28074)
			// EXCEPTION: Insegnamento da inserire già presente in carriera
			c.sostituisciInsegnamento(mappaCorsi.get(28074L), mappaCorsi.get(28021L));
			System.out.println(c);
		}
		catch (IllegalArgumentException e2){
			System.out.println("Sostituzione insegnamento negata: " + e2);
		}
		try	{
			// ora provo a sostituire un esame con diverso #CFU: Affidabilità (38378) al posto di Tecn. web (28659)
			// EXCEPTION: L'insegnamento da sostituire deve avere egual numero di crediti
			c.sostituisciInsegnamento(mappaCorsi.get(28659L), mappaCorsi.get(38378L));
			System.out.println(c);
		}
		catch (IllegalArgumentException e2){
			System.out.println("Sostituzione insegnamento negata: " + e2);
		}
	}
}
