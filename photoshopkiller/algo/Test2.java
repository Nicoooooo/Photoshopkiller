package photoshopkiller.algo;

public class Test2 {

	public static void main(String[] args) {
		Graph g = SeamCarving.tograph(SeamCarving.interest(SeamCarving.readpgm("test.pgm")));
		g.writeFile("test.dot");
	}

}
