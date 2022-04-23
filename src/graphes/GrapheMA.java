package graphes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GrapheMA implements IGraph {
	private Map<String, Integer> noeuds;
	private String[] labels; // pour toString
	private boolean[][] mab;
	private int[][] mav;

	/**
	 * @brief Constructeur de GrapheMA
	 * @param labels Tableau des labels du graphe
	 */
	public GrapheMA(String[] labels) {
		this.labels = labels.clone();
		this.noeuds = new HashMap<>();
		int nb = labels.length;
		for (int i =0; i< nb; ++i)
			this.noeuds.put(labels[i], i);
		mab = new boolean[nb][nb]; 
		mav = new int[nb][nb];
	}
	
	@Override
	public int getNbNoeuds() { return mab.length; }

	@Override
	public boolean estNoeudOK(String label) {
		return noeuds.containsKey(label);
	}

	@Override
	public boolean estArcOK(String n1, String n2) {
		return estNoeudOK(n1) && estNoeudOK(n2);
	}

	@Override
	public void ajouterArc(String label1, String label2, int valeur) throws RuntimeException {
		if (aArc(label1,label2))
			throw new RuntimeException("Il existe déjà un arc entre " + label1 + " et " + label2);
			
		int n1 = noeuds.get(label1);
		int n2 = noeuds.get(label2);
		mab[n1][n2] = true;
		mav[n1][n2] = valeur;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String label1 : noeuds.keySet()) {
			sb.append(label1 + " -> ");
			int n1 = noeuds.get(label1);
			for (int n2=0; n2< getNbNoeuds(); ++n2) {
				if (mab[n1][n2])
					sb.append(labels[n2] + "("+ mav[n1][n2] + ") ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public boolean aArc(String label1, String label2) throws IllegalArgumentException {
		if (!estArcOK(label1, label2))
			throw new IllegalArgumentException("L'un des labels entre " + label1 + " et " + label2 + "n'existent pas dans ce graphe");
		return mab[noeuds.get(label1)][noeuds.get(label2)];
	}
	
	@Override
	public int getValeur(String label1, String label2) throws IllegalArgumentException {
		if (!estArcOK(label1, label2))
			throw new IllegalArgumentException("L'un des labels entre " + label1 + " et " + label2 + "n'existent pas dans ce graphe");
		return mav[noeuds.get(label1)][noeuds.get(label2)];
	}

	@Override
	public int dOut(String label) throws IllegalArgumentException {
		if (!estNoeudOK(label))
			throw new IllegalArgumentException(label + " n'existe pas dans ce graphe");
		
		int n1 = noeuds.get(label);
		int degre = 0;
		for (int n2 = 0; n2 < getNbNoeuds(); ++n2)
			if (mab[n1][n2])
				++degre;
		return degre;
	}

	@Override
	public int dIn(String label) throws IllegalArgumentException {
		if (!estNoeudOK(label))
			throw new IllegalArgumentException(label + " n'existe pas dans ce graphe");
		
		int n2 = noeuds.get(label);
		int degre = 0;
		for (int n1 = 0; n1 < getNbNoeuds(); ++n1)
			if (mab[n1][n2])
				++degre;
		return degre;
	}

	@Override
	public Iterator<String> iterator() {
		return noeuds.keySet().iterator();
	}
}
