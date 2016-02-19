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
	
	public static Graph tograph(int[][] itr) {
		int width = itr[0].length, height = itr.length;
		Graph r = new Graph(2*width*height+2);
		
		// Edges from start to first line 
		for(int j = 0; j < width; j++) {
			r.addEdge(new Edge(0,j+2,0));
		}
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < (height-1)*2; j+=2) {
				//Edges that go one down
				r.addEdge(new Edge(j*width+i+2,(j+1)*width+i+2,itr[j/2][i]));
				
				//Edges that go one down to the right
				if(i < width - 1) {
					r.addEdge(new Edge(j*width+i+2,(j+1)*width+i+3,itr[j/2][i]));
				}
				
				//Edges that go one down to the left
				if(i > 0) {
					r.addEdge(new Edge(j*width+i+2,(j+1)*width+i+1,itr[j/2][i]));
				}
				
				//Edges that cut the vertex below in two vertices with value 0
				if(j+1 < (height-2)*2) {
					r.addEdge(new Edge((j+1)*width+i+2,(j+2)*width+i+2,0));	
				}
			}
			// Edges from last line to end
			r.addEdge(new Edge(((height-2)*2+1)*width+i+2,1,itr[height-1][i]));
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
		if(tab.length == 1) {
			return tab;
		}
		int[][] res = new int[tab.length][tab[0].length-1];
		
		Graph g = tograph(interest(tab));
		ArrayList<Edge> path = Dijkstra(g, 0, 1);
				
		int width = res[0].length, height = res.length;
		int vertexRemoved = 0, posRemoved;
		for(int y = 0; y < height; y++) {
			vertexRemoved = nextRemovedVertex(path, vertexRemoved);
			posRemoved = (vertexRemoved-2)%(width+1);
			for(int x = 0; x < posRemoved; x++){
				res[y][x] = tab[y][x];
			}
			for(int x = posRemoved; x < width; x++){
				res[y][x] = tab[y][x+1];
			}
		}
		
		return res;
	}
	
	public static int[][] addCols(int[][]tab) {
		int[][] res = new int[tab.length][tab[0].length + 1];
		
		Graph g = tograph(interest(tab));
		ArrayList<Edge> path = Dijkstra(g, 0, 1);
		
		int im = res.length, jm = res[0].length;
		for(int i = 0; i < im; i++) {
			int dec = 0;
			for(int j = 0; j < jm; j++) {
				if(isRemoved(path,i,j, tab[0].length) || j == tab[i].length) {
					dec = 1;
				}
				res[i][j] = tab[i][j-dec];
			}
		}
		
		return res;
	}
	
	public static int[][] highlightCols(int[][]tab, int[][] res) {
		if(tab[0].length == 1) {
			return tab;
		}
		
		Graph g = tograph(tab);
		ArrayList<Edge> path = Dijkstra(g, 0, 1);
		
		int im = res.length, jm = res[0].length;
		for(int i = 0; i < im; i++) {
			for(int j = 0; j < jm; j++) {
				if(isRemoved(path,i,j, tab[0].length)) {
					res[i][j] = 255;
				} else {
					res[i][j] = tab[i][j];
				}
			}
		}
		
		return res;
	}
	
	public static int nextRemovedVertex(ArrayList<Edge> path, int s) {
		Iterator<Edge> ite = path.iterator();
		while(ite.hasNext()) {
			Edge e = ite.next();
			if (e.from == s){
				ite.remove();
				return e.to;
			}
		}
		return -1;
	}
	
	public static void findAllShortestsWays(Graph g, int s) {
		Heap heap = new Heap(g.vertices());
		ArrayList<Edge> nexts = new ArrayList<Edge>();
		heap.decreaseKey(s, 0);
		g.setValue(s, 0);
				
		int current = heap.pop(), distance;
		while(!heap.isEmpty()){
			nexts = (ArrayList<Edge>) g.next(current);
			for(Edge e:nexts){
				distance = heap.priority(current) + e.cost;
				if(distance < heap.priority(e.to)){
					heap.decreaseKey(e.to, distance);
					g.setValue(e.to, distance);
				}
			}	
			current = heap.pop();
		}
	}
	
	public static ArrayList<Edge> twopath(Graph g, int s, int t){
		findAllShortestsWays(g,0);
		
		//Modify the edges values
		for(Edge e: g.edges()){
			e.cost += g.getValue(e.from) - g.getValue(e.to);
		}
		
		//Find the first shortest way and inverse it
		ArrayList<Edge> path = Dijkstra(g, s, t);
		for(Edge e: path){
			int tmp = e.to;
			e.to = e.from;
			e.from = tmp;
		}
		
		//Find shortest way in the resulting graph
		ArrayList<Edge> secondPath = Dijkstra(g, s, t);
		
		//Remove common edges
		Iterator<Edge> pathIte = path.iterator();
		while(pathIte.hasNext()){
			Edge e = pathIte.next();
			Iterator<Edge> secondPathIte = secondPath.iterator();
			while(secondPathIte.hasNext()){
				Edge f = secondPathIte.next();
				if(e.from == f.from && e.to == f.to){
					pathIte.remove();
					secondPathIte.remove();
				}
			}
		}
		
		//Reverse the remaining from the first path
		for(Edge e: path){
			int tmp = e.to;
			e.to = e.from;
			e.from = tmp;
		}
		path.addAll(secondPath);
		return path;
	}
}