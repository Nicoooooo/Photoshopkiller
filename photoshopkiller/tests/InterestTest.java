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
	}
}