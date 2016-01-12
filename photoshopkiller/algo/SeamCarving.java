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
}