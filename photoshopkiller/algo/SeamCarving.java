package photoshopkiller.algo;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
public class SeamCarving
{
	public static int[][] readpgm(String fn) {		
		try {
			InputStream f = ClassLoader.getSystemClassLoader().getResourceAsStream(fn);
			BufferedReader d = new BufferedReader(new InputStreamReader(f));
			String magic = d.readLine();
			String line = d.readLine();
			while (line.startsWith("#")) {
				line = d.readLine();
			}
			Scanner s = new Scanner(line);
			int width = s.nextInt();
			int height = s.nextInt();		   
			line = d.readLine();
			s = new Scanner(line);
			int maxVal = s.nextInt();
			int[][] im = new int[height][width];
			s = new Scanner(d);
			int count = 0;
			
			while (count < height*width) {
				im[count / width][count % width] = s.nextInt();
				count++;
			}
			return im;
		} catch(Throwable t) {
			t.printStackTrace(System.err) ;
			return null;
		}
	}

	public static void writepgm(int[][] image, String filename) {
		try {
			File file = new File(filename+".pgm");
			Writer output = new BufferedWriter(new FileWriter(file));
			StringBuilder sb = new StringBuilder();
			sb.append("P2\n");
			sb.append(image[0].length+"\t"+image.length+"\n");
			sb.append("255\n");
			
			for (int height=0; height<image.length; height++){
				for (int width=0; width<image[0].length; width++){
					sb.append(image[height][width]+"\t");
				}	
			}
			output.write(sb.toString());
			output.close();
		} catch(Throwable t) {
			t.printStackTrace(System.err) ;
		}
	}
	
	public static int[][] interest (int[][] image) {
		int width = image[0].length, height = image.length;
		int[][] res = new int[height][width];
		
		for(int i = 0; i < height; i++) {
			res[i][0] = Math.abs(image[i][1] - image[i][0]);
			
			for(int j = 1; j < width - 1; j++) {
				res[i][j] = Math.abs(image[i][j] - ((image[i][j-1] + image[i][j+1])/2));
			}
			
			res[i][width-1] = Math.abs(image[i][width-1] - image[i][width-2]);
		}
		
		return res;
	}
	
	public static int getEdgeId(int w, int x, int y) {
		return w * x + y + 1;
	}
	
	public static Graph tograph(int[][] itr) {
		int width = itr[0].length, height = itr.length;
		Graph r = new Graph(width*width+height*2,width,height);
		for(int j = 0; j < width; j++) {
			r.addEdge(new Edge(0,getEdgeId(width,j,0),0));
		}
		
		for(int i = 0; i < width; i++) {
			for(int j = 1; j < height - 1; j++) {
				r.addEdge(new Edge(getEdgeId(width,i,j),getEdgeId(width,i,j+1),itr[j][i]));
				
				if(i < width - 1) {
					r.addEdge(new Edge(getEdgeId(width,i,j),getEdgeId(width,i+1,j+1),itr[j][i]));
				}
				
				if(i > 0) {
					r.addEdge(new Edge(getEdgeId(width,i,j),getEdgeId(width,i-1,j+1),itr[j][i]));
				}
			}
			r.addEdge(new Edge(getEdgeId(width,i,height-1),getEdgeId(width,width,height)+1,itr[height-1][i]));
		}
		
		return r;
	}
	
	public static int[] Dijkstra(Graph g, int s, int t) {
		int[] path = {};
		
		visite(g,0);
		
		return path;
	}
	
	public static void visite(Graph g, int e) {
		Iterable<Edge> adj = g.adj(e);
		for(Edge ed : adj) {
		}
	}
}