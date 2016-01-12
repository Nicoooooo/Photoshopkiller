package photoshopkiller.tests;

import photoshopkiller.algo.SeamCarving;
import photoshopkiller.utils.ArrayUtils;

public class InterestTest {
	public InterestTest() {
		int[][] tab = {
			{3, 11, 24, 39},
			{8, 21, 29, 39},
			{200, 60, 25, 0}
		};
		
		ArrayUtils.trace(tab);
		ArrayUtils.trace(SeamCarving.interest(tab));
	}
}
