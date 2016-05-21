package compromeglio.ui;

import java.util.Set;

import compromeglio.model.Bene;
import compromeglio.model.Categoria;

public interface IController {

	Set<Bene> getBeniPerCategoria(Categoria c);

	Set<Categoria> getCategorie();

	String getPrezzoMedioPerCategoria(Categoria c);

	String getMigliorPrezzoPerBene(Bene b);

	String getPrezzoMedioPerBene(Bene b);

	String getMigliorRilevazionePerBene(Bene b);

}