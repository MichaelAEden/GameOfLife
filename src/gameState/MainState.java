package gameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.GamePanel;

import cellMap.CellMap;

public class MainState extends GameState {
	
	private CellMap cellMap;
	
	private boolean isPaused;
	private int updateRate = 15;
	private int updateTick;


	public MainState(GameStateManager gsm) {
		super(gsm);
		
		cellMap = new CellMap(2, GamePanel.WIDTH, GamePanel.HEIGHT);
	}

	public void update() {
		if (isPaused)
		{
			updateTick--;
			if(updateTick <= 0)
			{
				updateTick = updateRate;
				cellMap.update();
			}
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		cellMap.draw(g);
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_P) isPaused = !isPaused;
		if(k == KeyEvent.VK_RIGHT) {
			if (updateRate >= 3) {
				updateRate--;
			}
		}
		if(k == KeyEvent.VK_LEFT) {
			if (updateRate <= 60) {
				updateRate++;
			}
		}
	}

	public void mouseClicked(MouseEvent m) {
		Point cell = cellMap.getCellFromMouse(m);
		cellMap.spawnCellNow(cell.x, cell.y);
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent m) {
		mouseClicked(m);
	}

	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub	
	}
}
