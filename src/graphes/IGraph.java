package graphes;

import java.util.Iterator;

public interface IGraph extends Iterable<String>{

	int getNbNoeuds();

	boolean estNoeudOK(String label);

	boolean estArcOK(String n1, String n2);

	void ajouterArc(String label1, String label2, int valeur);

	boolean aArc(String label1, String label2);

	String toString();

	int dOut(String label);

	int dIn(String label);

	int getValeur(String n1, String n2);
	
	@Override
	Iterator<String> iterator();
}