package gameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.GamePanel;
import cellMap.Cell;
import cellMap.CellMap;

public class MainState extends GameState {
	
	private CellMap cellMap;
	
	private int updateTick = 0;
	private int updateRate = 3;
	private boolean isPaused = true;
		
	private int cellId = Cell.CELL_GROWER_A.ID;


	public MainState(GameStateManager gsm) {
		super(gsm);
		
		cellMap = new CellMap(5, GamePanel.WIDTH, GamePanel.HEIGHT);
	}

	public void update() {
		if (!isPaused)
		{
			updateTick++;
			if (updateTick >= updateRate) {
				cellMap.update();
				updateTick = 0;
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
			if (updateRate >= 1) {
				updateRate--;
			}
		}
		if(k == KeyEvent.VK_LEFT) {
			if (updateRate <= 60) {
				updateRate++;
			}
		}
		
		if(k == KeyEvent.VK_D) {
			cellId++;
			if (cellId > Cell.MAX_ID) {
				cellId = 1;
			}
		}
		if(k == KeyEvent.VK_A) {
			cellId--;
			if (cellId < 1) {
				cellId = Cell.MAX_ID;
			}
		}
	}

	public void mouseClicked(MouseEvent m) {
		Point cell = cellMap.getCellFromMouse(m);
		cellMap.createCell(cell.x, cell.y, cellId);
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
