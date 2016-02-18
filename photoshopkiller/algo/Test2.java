package photoshopkiller.algo;

import java.util.ArrayList;

public class Test2 {

	public static void main(String[] args) {
		Graph g = SeamCarving.tograph(SeamCarving.interest(SeamCarving.readpgm("test.pgm")));
		ArrayList<Edge> path = SeamCarving.twopath(g, 0, 1);
		for(Edge e:path){
			System.out.println(e.from + "->" + e.to + "[label=\"" + e.cost + "\"];");
		}
	}

}
