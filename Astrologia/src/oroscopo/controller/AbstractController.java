package oroscopo.controller;

import java.io.IOException;

import oroscopo.model.Oroscopo;
import oroscopo.model.SegnoZodiacale;
import oroscopo.persistence.OroscopoRepository;

public abstract class AbstractController {

	public static final int NUMERO_SEGNI = SegnoZodiacale.values().length;

	protected OroscopoRepository myRepo;

	public AbstractController(OroscopoRepository myRepo) throws IOException {
		this.myRepo = myRepo;
	}

	public abstract SegnoZodiacale[] getSegni();

	public abstract Oroscopo[] generaOroscopoAnnuale(SegnoZodiacale segno,
			int fortunaMin);

	public abstract Oroscopo generaOroscopoCasuale(SegnoZodiacale segno);

}
