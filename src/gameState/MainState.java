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
	
	private boolean update;
	private int updateRate = 15;
	private int updateTick;


	public MainState(GameStateManager gsm) {
		super(gsm);
		
		cellMap = new CellMap(25, GamePanel.WIDTH, GamePanel.HEIGHT);
	}

	public void update() {
		if(update)
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
		if(k == KeyEvent.VK_P) update = !update;
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
