package photoshopkiller.tests;

import photoshopkiller.algo.Graph;
import photoshopkiller.algo.SeamCarving;

public class InterestTest {
	public InterestTest() {
		String nom = "ex2";
		int[][] tab = SeamCarving.readpgm(nom+".pgm");
		
		tab = SeamCarving.interest(tab);
		
		SeamCarving.writepgm(tab, nom+"_interest");
		Graph g = SeamCarving.tograph(tab);
		
		//SeamCarving.Dijkstra(g, 0, SeamCarving.getEdgeId(tab[0].length, tab[0].length, tab.length)+1);
	}
}