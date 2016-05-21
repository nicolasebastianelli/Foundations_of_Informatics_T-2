package teethcollege.pianidistudi.ui;

import java.util.Collection;

import teethcollege.model.PianoDiStudi;
import teethcollege.model.Insegnamento;

public interface Controller {
	String sostituisci(PianoDiStudi pianoDiStudi, Insegnamento daTogliere, Insegnamento daMettere);
	Collection<PianoDiStudi> getPianiDiStudi();
	Collection<Insegnamento> getInsegnamenti();
}
