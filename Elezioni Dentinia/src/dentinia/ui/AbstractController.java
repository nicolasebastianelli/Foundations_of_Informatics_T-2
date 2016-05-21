package dentinia.ui;

import java.io.IOException;
import java.util.List;

import dentinia.model.CalcolatoreSeggi;
import dentinia.model.Partito;
import dentinia.persistence.BadFileFormatException;
import dentinia.persistence.SeggiWriter;
import dentinia.persistence.VotiReader;

public abstract class AbstractController implements Controller {

	 protected VotiReader myReader;
     protected SeggiWriter myWriter;
     protected List<Partito> listaPartiti;
    
     @Override
     public List<Partito> getListaPartiti() {
           return listaPartiti;
     }

     public AbstractController (VotiReader myReader, SeggiWriter myWriter) throws IOException, BadFileFormatException {
           this.myReader = myReader;
           this.myWriter = myWriter;
           listaPartiti= myReader.caricaElementi();
     }
    
     @Override
     public String[] getCalcolatoriSeggi() {
           return CalcolatoreSeggi.getCalcolatoriSeggi().toArray(new String[0]);
     }

}
