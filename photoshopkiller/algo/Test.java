package photoshopkiller.algo;

class Test
{
   static boolean visite[];
   public static void dfs(Graph g, int u)
	 {
		visite[u] = true;
		System.out.println("Je visite " + u);
		for (Edge e: g.next(u))
		  if (!visite[e.to])
			dfs(g,e.to);
	 }

   public static void testHeap()
	 {
		// Crée ue file de priorité contenant les entiers de 0 à 9, tous avec priorité +infty
		Heap h = new Heap(10);
		h.decreaseKey(3,1664);
		h.decreaseKey(4,5);
		h.decreaseKey(3,8);
		h.decreaseKey(2,3);
		// A ce moment, la priorité des différents éléments est:
		// 2 -> 3
		// 3 -> 8
		// 4 -> 5
		// tout le tabte -> +infini
		int x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		// La file contient maintenant uniquement les éléments 0,1,5,6,7,8,9 avec priorité +infini
	 }
   
   public static void testGraph()
	 {
		int n = 5;
		int i,j;
		Graph g = new Graph(n*n+2);
		
		for (i = 0; i < n-1; i++)
		  for (j = 0; j < n ; j++)
			g.addEdge(new Edge(n*i+j, n*(i+1)+j, 1664 - (i+j)));

		for (j = 0; j < n ; j++)		  
		  g.addEdge(new Edge(n*(n-1)+j, n*n, 666));
		
		for (j = 0; j < n ; j++)					
		  g.addEdge(new Edge(n*n+1, j, 0));
		
		g.addEdge(new Edge(13,17,1337));
		g.writeFile("test.dot");
		// dfs à partir du sommet 3
		visite = new boolean[n*n+2];
		dfs(g, 3);
	 }
   
   public static void main(String[] args) {
	   String command = "rmcols", filename = "ex3";
	   int N = 50;
	   
	   if(!(args.length == 3 || args.length == 2)) {
		   System.out.println("Usage : java -jar photoshopkiller.jar interest <filename>");
		   System.out.println("     or java -jar photoshopkiller.jar rmcols <filename> <number_of_cols>");
		   System.out.println("     or java -jar photoshopkiller.jar highlightcols <filename> <number_of_cols>");
	   }
	   
	   if(args.length == 3) {
		   command = args[0];
		   filename = args[1];
		   N = Integer.parseInt(args[2]);
	   }
	   int[][] tab = SeamCarving.readpgm(filename+".pgm");
	   
	   if(command.equals("rmcols")) {
		   for(int i = 0; i < N; i++) {
			   tab = SeamCarving.removeCols(tab);
			   System.out.print(".");
		   }		   
		   SeamCarving.writepgm(tab, filename+"_rmcols-"+N);
		   System.out.println("Done !");

	   } else if(command.equals("rm2cols")) {
		   for(int i = 0; i < N/2; i++) {
			   tab = SeamCarving.removeTwoCols(tab);
			   System.out.print(".");
		   }
		   SeamCarving.writepgm(tab, filename+"_rm2cols-"+(N/2)*2);	   
		   System.out.println("Done !");
		   
	   } else if(command.equals("addcols")) {
		   for(int i = 0; i < N; i++) {
			   tab = SeamCarving.addCols(tab);
			   System.out.print(".");
		   }		   
		   SeamCarving.writepgm(tab, filename+"_addcols-"+N);
		   System.out.println("Done !");

	   } else if(command.equals("highlightcols")) {
		   int[][] tmp = new int[tab.length][tab[0].length];
		   for(int i = 0; i < N; i++) {
			   tab = SeamCarving.highlightCols(tmp, tab);
			   System.out.print(".");
		   }
		   SeamCarving.writepgm(tab, filename+"_highlight"+N);
		   System.out.println("Done !");
		   
	   } else if(command.equals("interest")) {
		   tab = SeamCarving.interest(tab);
		   SeamCarving.writepgm(tab, filename+"_interest");
		   System.out.println("Done !");
		   
	   } else {
		   System.out.println("Incorrect command");
	   }
   }
}
