package gameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class GameState 
{
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm)
	{
		this.gsm = gsm;
	}
	
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void mouseClicked(MouseEvent m);
	public abstract void mousePressed(MouseEvent m);
	public abstract void mouseReleased(MouseEvent m);
	public abstract void mouseMoved(MouseEvent m);
	public abstract void mouseDragged(MouseEvent m);
}
