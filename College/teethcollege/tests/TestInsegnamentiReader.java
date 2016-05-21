package teethcollege.tests;
import java.io.*;
import java.util.Map;

import teethcollege.model.Insegnamento;
import teethcollege.persistence.InsegnamentiReader;
import teethcollege.persistence.MyInsegnamentiReader;

public class TestInsegnamentiReader {
	public static void main(String[] args) {
		try	{
			InsegnamentiReader insReader = new MyInsegnamentiReader(new FileReader("Insegnamenti.txt"));
			Map<Long, Insegnamento> listaCorsi = insReader.caricaInsegnamenti();	
			System.out.println(listaCorsi);
		}
		catch (IOException e){
			System.err.println(e);
		}
	}
}
