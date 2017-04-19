package gameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class GameStateManager 
{
	private GameState currentState;
	
	public static final int MENUSTATE = 0;
	public static final int MAINSTATE = 1;

	
	public GameStateManager()
	{
		setState(MAINSTATE);
	}
	
	public void setState(int state)
	{
		if(state == MAINSTATE)
		{
			currentState = new MainState(this);
		}
	}
	
	public void update()
	{
		currentState.update();
	}
	
	public void draw(Graphics2D g)
	{
		currentState.draw(g);
	}
	
	public void keyPressed(int k)
	{
		currentState.keyPressed(k);
	}

	public void mouseClicked(MouseEvent m) 
	{
		currentState.mouseClicked(m);
	}

	public void mousePressed(MouseEvent m) 
	{
		currentState.mousePressed(m);
	}

	public void mouseReleased(MouseEvent m) 
	{
		currentState.mouseReleased(m);
	}
	
	public void mouseMoved(MouseEvent m) 
	{
		currentState.mouseMoved(m);
	}
	
	public void mouseDragged(MouseEvent m) 
	{
		currentState.mouseDragged(m);
	}
}
