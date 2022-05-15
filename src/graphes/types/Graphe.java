package graphes.types;

import graphes.IGraphe;

public abstract class Graphe implements IGraphe {
	/**
	 * @param n Numéro du noeud à tester
	 * @return true si le noeud peut exister, false sinon
	 */
	public boolean estNoeudOK(int n) {
		return n >= 1 && n <= getNbSommets();
	}
	
	/**
	 * @param a Numéro du noeud de départ de l'arc à tester
	 * @param b Numéro du noeud de destination de l'arc à tester
	 * @return true si l'arc peut exister, false sinon
	 */
	public boolean estArcOK(int a, int b) {
		return estNoeudOK(a) && estNoeudOK(b);
	}
	
	@Override
	public boolean aArc(int a, int b) {
		return getValuation(a,b) != INFINI;
	}
}