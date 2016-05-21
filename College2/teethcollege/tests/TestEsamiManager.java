package teethcollege.tests;

import java.io.*;
import java.util.List;
import java.util.Map;

import teethcollege.model.Esame;
import teethcollege.model.Insegnamento;
import teethcollege.persistence.InsegnamentiReader;
import teethcollege.persistence.MalformedFileException;
import teethcollege.persistence.MyEsamiManager;
import teethcollege.persistence.MyInsegnamentiReader;

public class TestEsamiManager {
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
		MyEsamiManager myEsamiManager = new MyEsamiManager();
		List<Esame> tuttiGliEsami = null;
		// test lettura
		try {
			tuttiGliEsami = myEsamiManager.caricaEsami(new FileReader("Esami.txt"), mappaCorsi);
			System.out.println("--------------ELENCO ESAMI-----------");
			for (Esame e: tuttiGliEsami){
				System.out.println(e);
			}
		} catch (MalformedFileException e1) {
			System.out.println(e1.getMessage());
		} catch (IOException e) {
			System.err.println(e);
		}
		// test riscrittura
		try {
			myEsamiManager.salvaEsami(new FileWriter("Esami.txt"), tuttiGliEsami);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
