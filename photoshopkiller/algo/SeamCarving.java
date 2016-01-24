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
				sb.append("\n");
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
		return w * x + y + 2;
	}
	
	public static Graph tograph(int[][] itr) {
		int width = itr[0].length, height = itr.length;
		Graph r = new Graph(width*width+height*2);
		for(int j = 0; j < width; j++) {
			r.addEdge(new Edge(0,getEdgeId(width,j,0),0));
		}
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height - 1; j++) {
				r.addEdge(new Edge(getEdgeId(width,i,j),getEdgeId(width,i,j+1),itr[j][i]));
				
				if(i < width - 1) {
					r.addEdge(new Edge(getEdgeId(width,i,j),getEdgeId(width,i+1,j+1),itr[j][i]));
				}
				
				if(i > 0) {
					r.addEdge(new Edge(getEdgeId(width,i,j),getEdgeId(width,i-1,j+1),itr[j][i]));
				}
			}
			r.addEdge(new Edge(getEdgeId(width,i,height-1),1,itr[height-1][i]));
		}
		
		return r;
	}
	
	public static ArrayList<Edge> Dijkstra(Graph g, int s, int t) {
		Heap heap = new Heap(g.vertices());
		int[] previous = new int[g.vertices()];
		ArrayList<Edge> nexts, path = new ArrayList<Edge>();
		heap.decreaseKey(s, 0);
				
		int current = heap.pop(), distance;
		while(current != t){
			nexts = (ArrayList<Edge>) g.next(current);
			for(Edge e:nexts){
				distance = heap.priority(current) + e.cost;
				if(distance < heap.priority(e.to)){
					heap.decreaseKey(e.to, distance);
					previous[e.to] = current;
				}
			}
			current = heap.pop();
		}
		
		while(current != s){
			nexts = (ArrayList<Edge>) g.adj(current);
			for(Edge e:nexts){
				if(e.from == previous[current]){
					path.add(e);
				}
			}
			current = previous[current];
		}
		
		return path;
	}
	
	public static int[][] removeCols(int[][]tab) {
		if(tab[0].length == 1) {
			return tab;
		}
		int[][] res = new int[tab.length][tab[0].length-1];
		
		Graph g = tograph(tab);
		ArrayList<Edge> path = Dijkstra(g, 0, 1);
		
		int im = res.length, jm = res[0].length;
		for(int i = 0; i < im; i++) {
			int dec = 0;
			for(int j = 0; j < jm; j++) {
				if(isRemoved(path,i,j, tab[0].length)) {
					dec = 1;
				}
				res[i][j] = tab[i][j+dec];
			}
		}
		
		return res;
	}
	
	public static boolean isRemoved(ArrayList<Edge> path, int x, int y, int w) {
		for(Edge e : path) {
			if(e.to == getEdgeId(w,y,x)) {
				return true;
			}
		}
		return false;
	}
}