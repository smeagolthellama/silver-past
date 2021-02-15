package silver;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class GameMap implements MapWatcher {
	protected BufferedImage image;
	protected Boolean[][] land;
	protected int width, height;

	public GameMap(String filename) {
		// TODO Auto-generated constructor stub
		try {
			image=ImageIO.read(new File(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean checkland(int x, int y) throws Exception{
		return land[x][y];
	}
}
