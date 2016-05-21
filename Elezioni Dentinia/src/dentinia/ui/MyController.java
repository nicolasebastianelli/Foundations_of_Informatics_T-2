package dentinia.ui;

import java.io.FileWriter;
import java.io.IOException;

import dentinia.model.CalcolatoreSeggi;
import dentinia.persistence.BadFileFormatException;
import dentinia.persistence.SeggiWriter;
import dentinia.persistence.VotiReader;

public class MyController extends AbstractController {
	
	public MyController(VotiReader myReader, SeggiWriter myWriter) throws IOException, BadFileFormatException {
		
		super(myReader, myWriter);	
	}
	
	@Override
	public void ricalcola(String meccanismoSelezionato) {
		CalcolatoreSeggi cs = CalcolatoreSeggi.getInstance(meccanismoSelezionato);
		cs.assegnaSeggi(myReader.getSeggi(), listaPartiti);
	}

	@Override
	public void salvaSuFile(String nomeFile) throws IOException {
		myWriter.stampaSeggi(listaPartiti, new FileWriter(nomeFile));
	}

}
