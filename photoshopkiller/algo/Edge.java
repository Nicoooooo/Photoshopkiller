package photoshopkiller.algo;
public class Edge
{
   public int from;
   public int to;
   int cost;
   Edge parent;
   
   Edge(int x, int y, int cost)
	 {
		this.from = x;
		this.to = y;
		this.cost = cost;
	 }
   
}
