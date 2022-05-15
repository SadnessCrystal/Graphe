package graphes.types;

public class GrapheMA extends Graphe{
	private int[][] ma;

	public GrapheMA(int nbNoeuds) {
		ma = new int[nbNoeuds][nbNoeuds];
		for (int a = 0; a < nbNoeuds; ++a)
			for (int b = 0; b < nbNoeuds; ++b)
				ma[a][b] = INFINI;
	}
	
	@Override
	public int getNbSommets() { return ma.length; }
	
	@Override
	public int getValuation(int a, int b) throws IllegalArgumentException {
		if (!estArcOK(a,b)) throw new IllegalArgumentException();
		return ma[a-1][b-1];
	}
	
	@Override
	public void ajouterArc(int a, int v, int b) throws IllegalArgumentException {
		if (!estArcOK(a,b)) throw new IllegalArgumentException();
		ma[a-1][b-1] = v;
	}
	

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		int v;
		for(int i = 1; i<= getNbSommets(); ++i) {
			str.append((i) + " =>");
			for (int j = 1; j <= getNbSommets(); ++j)
				if ((v= getValuation(i,j)) != INFINI) 
					str.append(" " + j + "(" + v + ")");
			str.append("\n");
		}
		return str.toString();
	}
}
