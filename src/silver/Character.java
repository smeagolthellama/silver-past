package silver;

import java.awt.event.MouseEvent;

public abstract class Character extends Entity {

	private static final long serialVersionUID = 7826302939961500150L;
	
	protected MapWatcher map;
	protected float speed;

	public Character(String fn) {
		// TODO Auto-generated constructor stub
		super(fn);
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	protected void transform(String newFile) {
		// TODO Auto-generated method stub

	}
	
	protected void moveLeft() {}
	protected void moveRight() {}
	protected void climb() {}
	protected void jumpLeft() {}
	protected void jumpRight() {}
	
}
