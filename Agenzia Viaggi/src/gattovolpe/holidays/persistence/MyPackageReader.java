package gattovolpe.holidays.persistence;

import static gattovolpe.utils.DateUtil.*;

import gattovolpe.holidays.model.Destinazione;
import gattovolpe.holidays.model.Pacchetto;
import gattovolpe.holidays.model.TipoPacchetto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class MyPackageReader implements PackageReader {

	private BufferedReader innerReader;
	public MyPackageReader(Reader reader) {
		innerReader = new BufferedReader(reader);
	}
	
	@Override
	public List<Pacchetto> readPackages() throws InvalidPackageFormatException {
		
		List<Pacchetto> pacchetti = new ArrayList<Pacchetto>();
		try {
			
			do {
				pacchetti.add( readPacchetto() );
			} while(true);
			
		} catch( IOException e1 ) {
			try {
				innerReader.close();
			} catch (IOException e) {
				throw new InvalidPackageFormatException(e.getMessage());
			}
		}
	
		return pacchetti;
	}
	
	private StringTokenizer processLine(String separator) throws IOException {
		String line = innerReader.readLine();
		
		if(line==null)
			throw new IOException("end of file");
		
		StringTokenizer tokenizer = 
				new StringTokenizer(line, separator);
		
		return tokenizer;
	}
	
	private Pacchetto readPacchetto() throws InvalidPackageFormatException, IOException {
		
		Pacchetto pacchetto = new Pacchetto();
		
		StringTokenizer tokenizer = processLine(" ");
		
		try {
			if(!tokenizer.nextToken().equalsIgnoreCase("startpackage"))
				throw new InvalidPackageFormatException("Atteso STARTPACKAGE");
			
			pacchetto.setNome( tokenizer.nextToken() );
			
			pacchetto.setTipo(TipoPacchetto.valueOf( tokenizer.nextToken() ));
			
		} catch(NoSuchElementException  e1) {
			throw new InvalidPackageFormatException("Numero di token errato - riga non valida");
		} catch(IllegalArgumentException e2) {
			throw new InvalidPackageFormatException("Tipo di pacchetto non riconosciuto");
		}
		
		readDestinazione(pacchetto);
		
		readDurata(pacchetto);
		
		readCosti(pacchetto);
		
		readDescrizione(pacchetto);
		
		return pacchetto;
	}
	
	private void readDescrizione(Pacchetto pacchetto) throws IOException, InvalidPackageFormatException {
		String line = null, descrizione = "";
		
		try {
			while((line = innerReader.readLine())!=null && !line.equalsIgnoreCase("endpackage")) 
			{
				descrizione += line + System.getProperty("line.separator");
			}
		} catch (IOException e) {
			throw new InvalidPackageFormatException("errore nella lettura della descrizaione");
		}
		
		pacchetto.setDescrizione( descrizione );
	}

	private void readDurata(Pacchetto pacchetto) throws InvalidPackageFormatException, IOException {
		
		StringTokenizer tokenizer = processLine(",");
	
		Date startDate = 
				parseDate( tokenizer.nextToken() );

		pacchetto.setDataInizio( startDate );
		
		try {
			
			StringTokenizer st = 
					new StringTokenizer(tokenizer.nextToken());
			
			pacchetto.setDurataGiorni( Integer.parseInt(st.nextToken()) );
			
			if(!st.nextToken().equalsIgnoreCase("giorni"))
				throw new InvalidPackageFormatException("Durata deve essere in giorni");
		
		} catch (NumberFormatException e1) {
			throw new InvalidPackageFormatException("Durata non riconosciuta");
		} catch(NoSuchElementException e2) {
			throw new InvalidPackageFormatException("Durata pacchetto attesa");
		}
		
	}

	private void readCosti(Pacchetto pacchetto) throws InvalidPackageFormatException, IOException {

		StringTokenizer tokenizer = processLine(" ");
		
		try {
			if(!tokenizer.nextToken().equalsIgnoreCase("EUR"))
				throw new InvalidPackageFormatException("Valuta deve essere EUR");
			pacchetto.setCosto( Double.parseDouble( tokenizer.nextToken().trim() ) );
		} catch (NumberFormatException e1) {
			throw new InvalidPackageFormatException("Costi non riconosciuti");
		} catch(NoSuchElementException e2) {
			throw new InvalidPackageFormatException("Valore di costo atteso");
		}
		
	}

	private void readDestinazione(Pacchetto pacchetto) throws InvalidPackageFormatException, IOException {
		
		StringTokenizer tokenizer = processLine(",");
		
		Destinazione destinazione = new Destinazione();
		
		try {
		
		destinazione.setStato( tokenizer.nextToken().trim() );
		destinazione.setLuogo( tokenizer.nextToken().trim() );
		
		} catch( NoSuchElementException e1 ) {
			throw new InvalidPackageFormatException("Destinazione non riconosciuta");
		}
		
		pacchetto.setDestinazione(destinazione);
	}

	public void close() {
		try {
			innerReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
