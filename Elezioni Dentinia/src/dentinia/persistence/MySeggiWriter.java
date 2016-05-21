package dentinia.persistence;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import dentinia.model.Partito;

public class MySeggiWriter implements SeggiWriter {

	@Override
	public void stampaSeggi(List<Partito> partiti, Writer writer)
			throws IOException {
		
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(writer);
			for(Partito p : partiti) {
				pw.println(p.getNome() + ": " + p.getSeggi());
			}
		}
		finally {
			if (pw != null)
		        pw.close();
		}
		
	}

}
