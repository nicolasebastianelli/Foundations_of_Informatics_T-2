package galliacapocciona.tests;
import galliacapocciona.model.CalcolatoreSeggi;
import galliacapocciona.model.CalcolatoreSeggiProporzionale;
import galliacapocciona.model.Partito;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class TestCalcSeggiProporzionale {

	//-------------mini-main di test------------------
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String[] nomiPartiti = {"Gialli", "Neri", "Blu", "Rossi", "Verdi"};
		int[] votiPartiti = {7460, 2040, 3482, 8748, 8922};
		
		List<Partito> listaPartiti1 = new ArrayList<Partito>(),
					  listaPartiti2 = new ArrayList<Partito>();
		for (int i=0; i< nomiPartiti.length; i++){
			listaPartiti1.add(new Partito(nomiPartiti[i], votiPartiti[i]));
			listaPartiti2.add(new Partito(nomiPartiti[i], votiPartiti[i]/2));
		}
		
		CalcolatoreSeggiProporzionale cs1 = (CalcolatoreSeggiProporzionale) CalcolatoreSeggi.getInstance("proporzionale");
		Map<String,Integer> mappaSeggi = cs1.assegnaSeggiGlobale(8, listaPartiti1);
		System.out.println("PROPORZIONALE (8 seggi) - da lista partiti");
		System.out.println(mappaSeggi);
		
	}
}
