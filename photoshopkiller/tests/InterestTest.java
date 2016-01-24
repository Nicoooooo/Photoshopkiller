package photoshopkiller.tests;

import java.util.ArrayList;

import photoshopkiller.algo.Edge;
import photoshopkiller.algo.Graph;
import photoshopkiller.algo.SeamCarving;
import photoshopkiller.utils.ArrayUtils;

public class InterestTest {
	public InterestTest() {
		String nom = "ex2";
		int[][] tab = SeamCarving.readpgm(nom+".pgm");
		
		int[][] res = SeamCarving.removeCols(tab);
		for(int i = 0; i < 30; i++) {
			res = SeamCarving.removeCols(res);
			System.out.println("removeCols"+i);
		}
		
		SeamCarving.writepgm(res, nom+"_m30.pgm");
		
		//tab = SeamCarving.interest(tab);
		//SeamCarving.writepgm(tab, nom+"_interest");
		//Graph g = SeamCarving.tograph(tab);
		//g.writeFile(nom+"_graph");
		
		
		//ArrayList<Edge> path = SeamCarving.Dijkstra(g, 0, 1);
		//for(Edge e:path){
		//	System.out.print(e.from+"->"+e.to+";");
		//}
		
	}
}