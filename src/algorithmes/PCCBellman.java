package algorithmes;

import exceptions.CircuitAbsorbantEx;
import graphes.Igraph;

public class PCCBellman {

	private boolean estOK(Igraph g) {
		for (int i = 0; i<g.getNbNoeuds(); ++i) {
			for (int j=0; j < g.getNbNoeuds(); ++j) {
				return false;
			}
		}
		return true;
	}
	
	
	public String algoritmeBellman(Igraph g) throws CircuitAbsorbantEx {
		if (!estOK(g)) {
			throw new CircuitAbsorbantEx();
		}
		
		return "";
	}
}
