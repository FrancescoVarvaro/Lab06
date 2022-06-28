package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private MeteoDAO meteo;
	private List <Citta> sequenzaMigliore;
	private double costoMigliore;
	private List<Citta> tutteLeCitta;
	
	public Model() {
		this.meteo = new MeteoDAO();
		costoMigliore = 100000;
		tutteLeCitta = meteo.getAllCitta();
		
	}

	// of course you can change the String output with what you think works best
	public List<Rilevamento> getUmiditaMedia(int mese) {
		return meteo.getMediaUmiditaPerMese(mese);
	}
	
	// of course you can change the String output with what you think works best
	public List<Citta> trovaSequenza(int mese) {
		
		for(Citta c : tutteLeCitta) {
			c.setRilevamenti(meteo.getAllRilevamentiLocalitaMese(mese, c.getNome()));
		}
		sequenzaMigliore = null;
		
		List<Citta> parziale = new LinkedList<Citta> ();
		ricorsione(parziale,0);
		
		return sequenzaMigliore;
	}
	private void ricorsione(List<Citta> parziale, int livello) {
		//terminazione
		if(parziale.size()==NUMERO_GIORNI_TOTALI) {
			double costo = CalcolaCosto(parziale);
			if(costo < costoMigliore) {
				sequenzaMigliore = new ArrayList<Citta>(parziale);
				costoMigliore = costo;
			}
		}
		// sol. VALIDA
		else {
			for(Citta c : tutteLeCitta) {
				if(Verifica(c,parziale) == true) {
					parziale.add(c);
					c.increaseCounter();
					ricorsione(parziale, livello+1);
				}
			}
		}
		
		
	}

	
	private boolean Verifica(Citta citta, List<Citta> parziale) {
		int cont = 0;
		for(Citta c : parziale) {
			
			if(c.equals(citta))
				cont++;
		}
		if(cont>=NUMERO_GIORNI_CITTA_MAX)
			return false;
		
		if(parziale.size()==0) 
			return true;
		
		if(parziale.size()==1||parziale.size()==2)
			return true;
			
		if(cont == 0)
			return true;
		
		if(cont == 1 && parziale.get(parziale.size()-1).equals(citta)) 
			return true;
		
		if(parziale.get(parziale.size()-1).equals(citta) && parziale.get(parziale.size()-2).equals(citta)) {
			return true;
		}
			
		return false;
	}

	private double CalcolaCosto(List<Citta> parziale) {
		
		
		return 0;
	}

}
