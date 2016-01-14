package photoshopkiller.utils;

import java.util.ArrayList;

import photoshopkiller.algo.Edge;

public class ArrayUtils {
	/**
	 * Affiche une image (tableau d'entiers) dans la console
	 * @param img : image à tracer
	 */
	public static void trace(int[][] img) {
		System.out.println("===================================");
		for(int i = 0; i < img.length; i++) {
			StringBuilder sb = new StringBuilder();
			for(int j = 0; j < img[i].length; j++) {
				sb.append(img[i][j]);
				sb.append(" ");
			}
			System.out.println(sb.toString());
		}
		System.out.println("===================================");
	}
	
	public static void trace(ArrayList<Edge> path) {
		StringBuilder sb = new StringBuilder("Chemin : ");
		
		for(Edge e : path) {
			sb.append(e.from);
			sb.append(", ");
		}
		System.out.println(sb.toString());
	}
}
