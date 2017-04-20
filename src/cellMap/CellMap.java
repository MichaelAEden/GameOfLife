package cellMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import main.GamePanel;
import ui.Settings;

/**
 * A map of living and dead cells.
 */
public class CellMap {	
	private int horizontalIndent;
	private int verticalIndent;
	private int width;
	private int height;
	
	private Cell[][] map;
	private int columns;
	private int rows;
	
	private int cellWidth;
	private int cellHeight;
	
	/**
	 * Constructor.
	 *
	 * @param cellWidth 	Width of each cell in pixels
	 * @param width			Width of map, in cells
	 * @param height		Height of map, in cells
	 */
	public CellMap(int cellWidth, int width, int height)
	{	
		this.width = width;
		this.height = height;
		this.cellWidth = cellWidth;
		this.cellHeight = cellWidth;
		this.columns = this.width / this.cellWidth;
		this.rows = this.height / this.cellWidth;
		
		horizontalIndent = 0;
		verticalIndent = 0;
		
		init();
	}
	
	/**
	 * Initializes a blank cell map.
	 */
	private void init() {	
		map = new Cell[columns][rows];
		for(int x = 0; x < columns; x++)
		{
			map[x] = new Cell[rows];
			for(int y = 0; y < rows; y++) {
				map[x][y] = new Cell();
			}
		}
	}
	
	public void update() {	
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				if (!isActiveCellAt(x, y) && shouldSpawnCellAt(x, y)) {
					spawnCellNextCycle(x, y);
				}
				else if (isActiveCellAt(x, y) && shouldKillCellAt(x, y)) {
					killCellNextCycle(x, y);
				}
			}
		}
		
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				updateCellAt(x, y);
			}
		}
	}
	
	/*
	 * Returns whether cell at the given coordinates should be spawned next iteration
	 */
	private boolean shouldSpawnCellAt(int x, int y) {
		return (getNumberOfAdjacentCells(x, y) <= Settings.NEIGHBOURS_CAUSING_BIRTH_MAX &&
				getNumberOfAdjacentCells(x, y) >= Settings.NEIGHBOURS_CAUSING_BIRTH_MIN);	
	}
	
	/*
	 * Returns whether cell at the given coordinates should die next iteration
	 */
	private boolean shouldKillCellAt(int x, int y) {
		return (getNumberOfAdjacentCells(x, y) >= Settings.NEIGHBOURS_CAUSING_DEATH_MAX ||
				getNumberOfAdjacentCells(x, y) <= Settings.NEIGHBOURS_CAUSING_DEATH_MIN);
	}
	
	/*
	 * Gets the number of adjacent live cells to determine if a cell should live or die
	 */
	private int getNumberOfAdjacentCells(int x, int y) {
		int adjacentCells = 0;
		for (int tx = x - 1; tx <= x + 1; tx++) {
			for (int ty = y - 1; ty <= y + 1; ty++) {
				if (tx == x && ty == y) continue;
				if (isActiveCellAt(tx, ty)) {
					adjacentCells++;
				}
			}
		}
		
		return adjacentCells;
	}
	
	public void draw(Graphics2D g) {
		for(int x = 0; x < columns; x++) {
			for(int y = 0; y < rows; y++) {
				if (isActiveCellAt(x, y)) {
					g.setColor(this.getCellColourAt(x, y));
					g.fillRect(this.getPixelFromCell(x, y).x, getPixelFromCell(x, y).y, cellWidth, cellHeight);
				}
			}
		}
		
		if (Settings.SHOW_GRID) {
			g.setColor(new Color(50, 50, 50));
			for(int x = 0; x <= columns; x++) {
				g.drawLine(getPixelFromCell(x, 0).x, verticalIndent, getPixelFromCell(x, 0).x, verticalIndent + height);
			}
			for(int y = 0; y <= rows; y++) {
				g.drawLine(horizontalIndent, getPixelFromCell(0, y).y, horizontalIndent + width, getPixelFromCell(0, y).y);
			}
		}
	}

	
	
	/* * * * * * * * * * * * * *
	 * 		GETTERS AND SETTERS
	 * * * * * * * * * * * * * */
	
	public void spawnCellNow(int x, int y) {
		if (isActiveCellAt(x, y)) return;
		map[x][y].spawn();
	}
	public void spawnCellNextCycle(int x, int y) {
		if (isActiveCellAt(x, y)) return;
		map[x][y].spawnNextCycle();
	}
	public void killCellNow(int x, int y) {
		if (!isActiveCellAt(x, y)) return;
		map[x][y].kill();
	}
	public void killCellNextCycle(int x, int y) {
		if (!isActiveCellAt(x, y)) return;
		map[x][y].killNextCycle();
	}
	
	public void updateCellAt(int x, int y) {
		map[x][y].update(this);
	}
	
	public Color getCellColourAt(int x, int y) {
		if (!isInBounds(x, y)) return Settings.DEFAULT_TABLE_COLOUR;
		return map[x][y].getColour();
	}
	public boolean isActiveCellAt(int x, int y) {
		if (!isInBounds(x, y)) return false;
		return map[x][y].isActive();
	}
	
	public boolean isInBounds(int x, int y) {
		return (x < columns && y < rows) && (x >= 0 && y >= 0);
	}
	
	public Point getCellFromMouse(MouseEvent m)
	{
		return new Point((m.getX() / GamePanel.SCALE - horizontalIndent) / cellWidth, (m.getY() / GamePanel.SCALE - verticalIndent) / cellHeight);
	}
	public Point getPixelFromCell(int x, int y)
	{
		return new Point((x * cellWidth + horizontalIndent) * GamePanel.SCALE, (y * cellHeight + verticalIndent) * GamePanel.SCALE);
	}
}
