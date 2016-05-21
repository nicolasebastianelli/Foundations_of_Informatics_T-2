package galliacapocciona.tests;
import galliacapocciona.model.Collegio;
import galliacapocciona.persistence.BadFileFormatException;
import galliacapocciona.persistence.CollegiReader;
import galliacapocciona.persistence.MyCollegiReader;

import java.io.*;
import java.util.List;

public class TestReader {
	public static void main(String[] args) {
		try	{
			FileReader reader = new FileReader("RisultatiGallia.txt");
			CollegiReader myReader = new MyCollegiReader();
			List<Collegio> listaCollegi = myReader.caricaElementi(reader);	
			System.out.println(listaCollegi);
			reader.close();
		}
		catch (IOException e){
			e.printStackTrace();
		} catch (BadFileFormatException e) {
			e.printStackTrace();
		}
	}
}
