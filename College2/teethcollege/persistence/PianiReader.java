package teethcollege.persistence;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import teethcollege.model.PianoDiStudi;
import teethcollege.model.Insegnamento;

public interface PianiReader {
	public List<PianoDiStudi> caricaPianiDiStudi(Map<Long,Insegnamento> listaInsegnamenti) throws IOException;
}
