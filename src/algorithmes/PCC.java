package algorithmes;

import graphes.IGraphe;

public interface PCC {
	boolean estOK(IGraphe g);
	String algorithme(IGraphe g, Integer noeudDepart, Integer noeudArrivee);
}
