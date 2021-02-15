package silver;

import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

public abstract class Entity extends JPanel implements MouseListener{
	private static final long serialVersionUID = 6456829963586921495L;
	protected int x;
	protected int y;
	protected String filename;
	protected BufferedImage sprite;
	//protected TODO Inventory;
	

	public Entity(String fn) {
		// TODO Auto-generated constructor stub
		filename=fn;
		x=y=0;
		try {
			sprite=ImageIO.read(new File(filename+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int GetX() {
		return x;
	}
	
	public int GetY() {
		return y;
	}
	
	public abstract void mouseClicked(MouseEvent me);
	
	protected abstract void transform(String newFile);
	
}
