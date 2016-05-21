package trekking.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import trekking.model.Difficulty;
import trekking.model.Trail;
import trekking.model.TrailHead;

public class MyTrailReader implements TrailReader {

	private BufferedReader innerReader;
	public MyTrailReader(Reader reader) {
		innerReader = new BufferedReader(reader);
	}
	
	@Override
	public List<Trail> readTrails() throws InvalidTrailFormatException {
		List<Trail> trails = new ArrayList<Trail>();
		try {
			
			String line = null;
			while((line = innerReader.readLine())!=null)
				trails.add( readTrail(line) );
			
		} catch( IOException e1 ) {
			throw new InvalidTrailFormatException(e1.getMessage());
		} finally {
			try {
				innerReader.close();
			} catch (IOException e) {
				throw new InvalidTrailFormatException(e.getMessage());
			}
		}
	
		return trails;
	}
	
	private Trail readTrail(String line) throws InvalidTrailFormatException {
		
		StringTokenizer tok =
				new StringTokenizer(line, ",");
		
		Trail result = null;
		
		try {
			//---1o token, nome sentiero
			String nome = tok.nextToken().trim();
		
			//---2o token, stazione di partenza
			TrailHead partenza = readTrailHead( tok.nextToken().trim() );
			
			//---3o token, stazione di arrivo
			TrailHead arrivo = readTrailHead( tok.nextToken().trim() );
			
			//---4o token, lunghezza in km ed indicazione "km"
			double lunghezza = 
					readLength(tok.nextToken().trim());
			
			//---5o e ultimo token, Difficolta seguita da numero
			Difficulty difficolta =
					readDifficulty(tok.nextToken());
			
			result = new Trail(partenza, arrivo, difficolta);
			result.setName(nome);
			result.setLength(lunghezza);
			
		} catch(NoSuchElementException e) {
			throw new InvalidTrailFormatException(e.getMessage());
		}
		
		return result;
	}
	
	private TrailHead readTrailHead(String token) throws InvalidTrailFormatException {
		
		TrailHead risultato = new TrailHead();
		StringTokenizer tokenizer =
				new StringTokenizer(token, "()");
		risultato.setName(tokenizer.nextToken().trim());
		
		try {
		
		risultato.setAltitude(Double.parseDouble(tokenizer.nextToken().trim()));
		
		} catch(NumberFormatException e) {
			throw new InvalidTrailFormatException(e.getMessage());
		}
		
		return risultato;
	}
	
	private double readLength(String token) throws InvalidTrailFormatException {
		
		StringTokenizer tokenizer = new StringTokenizer(token, " ");
		double risultato = 0.0;
		try {
		
			risultato = Double.parseDouble(tokenizer.nextToken().trim());
			
			if(!tokenizer.nextToken().trim().equals("km"))
				throw new InvalidTrailFormatException("Indicazione km mancante");
		
		} catch(Exception e) {
			throw new InvalidTrailFormatException(e.getMessage());
		}
		return risultato;
	}
	
	private Difficulty readDifficulty(String token) throws InvalidTrailFormatException {
		
		StringTokenizer tokenizer = new StringTokenizer(token, " ");
		try {

			if(!tokenizer.nextToken().trim().equals("Difficolta"))
				throw new InvalidTrailFormatException("Indicazione Difficolta mancante");
		
			int valore = 
					Integer.parseInt(tokenizer.nextToken().trim());
			
			for(Difficulty d : Difficulty.values()) {
				if(d.getValue()==valore) return d;
			}
			
			throw new InvalidTrailFormatException("Valore di difficolta non previsto");
			
		} catch(Exception e) {
			throw new InvalidTrailFormatException(e.getMessage());
		}
	}
	
	public void close() {
		try {
			innerReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
