package zannonia;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import zannonia.model.Autostrada;
import zannonia.model.Tratta;
import zannonia.persistence.AutostradeReader;
import zannonia.persistence.MalformedFileException;
import zannonia.persistence.MyAutostradeReader;
import zannonia.persistence.MyTratteReader;
import zannonia.persistence.TratteReader;
import zannonia.ui.MainController;

public class Program
{

	/**
	 * @param args
	 * @throws MalformedFileException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, MalformedFileException
	{
		AutostradeReader reader = new MyAutostradeReader();
		TratteReader tratteReader = new MyTratteReader();
		List<Autostrada> autostrade;
//		try
//		{
			Map<String, Tratta> tratteMap = tratteReader.readTratte(new FileReader("Tratte.txt"));
			autostrade = reader.readAutostrade(new FileReader("Autostrade.txt"), tratteMap);
			MainController controller = new MainController();
			controller.start(autostrade);
//		}
//		catch (FileNotFoundException e)
//		{
//			JOptionPane.showMessageDialog(null, "File non trovato.");
//		}
//		catch (IOException e)
//		{
//			JOptionPane.showMessageDialog(null, "Errore di I/O.");
//		}
//		catch (MalformedFileException e)
//		{
//			JOptionPane.showMessageDialog(null, "Errore nel file.");
//		}
	}

}
