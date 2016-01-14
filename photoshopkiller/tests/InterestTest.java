package photoshopkiller.tests;

import photoshopkiller.algo.SeamCarving;
import photoshopkiller.utils.ArrayUtils;

public class InterestTest {
	public InterestTest() {
		int[][] tab = SeamCarving.readpgm("ex1.pgm");
		
		tab = SeamCarving.interest(tab);
		
		SeamCarving.writepgm(tab, "ex1_interest");
	}
}
