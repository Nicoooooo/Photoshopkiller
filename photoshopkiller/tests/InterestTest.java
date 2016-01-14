package photoshopkiller.tests;

import photoshopkiller.algo.Graph;
import photoshopkiller.algo.SeamCarving;

public class InterestTest {
	public InterestTest() {
		int[][] tab = SeamCarving.readpgm("ex1.pgm");
		
		tab = SeamCarving.interest(tab);
		
		SeamCarving.writepgm(tab, "ex1_interest");
		
		Graph g = SeamCarving.tograph(tab);
	}
}