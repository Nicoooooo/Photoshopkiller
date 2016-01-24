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
		
		tab = SeamCarving.interest(tab);
		SeamCarving.writepgm(tab, nom+"_interest");
		
		//Graph g = SeamCarving.tograph(tab);
		//g.writeFile(nom+"_graph");
		
		
		/*ArrayList<Edge> path = SeamCarving.Dijkstra(g, 0, 1);
		System.out.println(path.size());
		for(Edge e:path){
			System.out.print(e.from+"->"+e.to+";");
		}*/
		
	}
}