package silver;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author mark
 *
 */
public class mapview extends JFrame implements KeyListener{
	private static final long serialVersionUID = 1L;

	private int x,y;
	private BufferedImage heightmap=null;
	private JPanel jp;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new mapview();
	}
	
	public mapview() {
		x=y=0;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,500);
		setVisible(true);
		try {
			heightmap = ImageIO.read(new File("world_dem.png"));
			System.out.println(heightmap);
		}catch(IOException e) {
			System.err.println(e);
		}
		jp=new JPanel();
		jp.setSize(500,500);
		add(jp);
		Graphics g=jp.getGraphics();
		g.drawImage(heightmap, 0, 0, null);
		setFocusable(true);
		addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());	
		if(e.getKeyCode()==KeyEvent.VK_UP) {
			y+=1;	
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			y-=1;	
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			x+=1;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			x-=1;
		}
		jp.getGraphics().drawImage(heightmap, x, y, null);
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
