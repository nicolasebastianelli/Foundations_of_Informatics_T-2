package teethcollege.tests;

import java.io.*;
import java.util.Collection;
import java.util.Map;

import teethcollege.model.PianoDiStudi;
import teethcollege.model.Insegnamento;
import teethcollege.persistence.InsegnamentiReader;
import teethcollege.persistence.MalformedFileException;
import teethcollege.persistence.MyInsegnamentiReader;
import teethcollege.persistence.MyPianiReader;
import teethcollege.persistence.PianiReader;

public class TestPianiReader {
	public static void main(String[] args) {
		try {
			// carico comunque gli insegnamenti perché mi servono
			InsegnamentiReader insReader = new MyInsegnamentiReader(
					new FileReader("Insegnamenti.txt"));
			Map<Long, Insegnamento> mappaCorsi = insReader.caricaInsegnamenti();
			// ora carico le carriere
			PianiReader carReader = new MyPianiReader(new FileReader(
					"PianiDiStudi.txt"));
			Collection<PianoDiStudi> listaCarriere = carReader.caricaPianiDiStudi(mappaCorsi);
			System.out.println("--------------LISTA CARRIERE-----------");
			System.out.println(listaCarriere);
			System.out.println("--------------DETTAGLI CARRIERE-----------");
			for (PianoDiStudi c : listaCarriere) {
				System.out.println(c.toFullString());
			}
		} catch (MalformedFileException e1) {
			System.out.println(e1.getMessage());
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
