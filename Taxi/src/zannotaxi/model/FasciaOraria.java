package zannotaxi.model;

public enum FasciaOraria {
	DIURNA, NOTTURNA; 
	
	public static FasciaOraria getFasciaOraria(SimpleTime t){
		if (t.getOre()<6 || t.getOre()>=22) return NOTTURNA;
		else return DIURNA;
	}
}
