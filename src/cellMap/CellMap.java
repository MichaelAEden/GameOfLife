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
	
	private byte[][] cellsState;
	private int[][] cellsAge;
	private int[][] cellsGeneration;
	private int[][] cellsType;
	private byte[][] cellsMetadata;
	
	private int columns;
	private int rows;
	
	private int cellWidth;
	private int cellHeight;
	
	private int updateTick = 0;
	
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
		cellsState = new byte[columns][rows];
		cellsAge = new int[columns][rows];
		cellsGeneration = new int[columns][rows];
		cellsType = new int[columns][rows];
		cellsMetadata = new byte[columns][rows];
		for(int x = 0; x < columns; x++)
		{
			cellsState[x] = new byte[rows];
			cellsAge[x] = new int[rows];
			cellsGeneration[x] = new int[rows];
			cellsType[x] = new int[rows];
			cellsMetadata[x] = new byte[rows];
			
			for(int y = 0; y < rows; y++) {
				cellsState[x][y] = Cell.DEAD;
				cellsAge[x][y] = 0;
				cellsGeneration[x][y] = 0;
				cellsType[x][y] = Cell.CELL_DEAD.ID;
				cellsMetadata[x][y] = 0;
			}
		}
	}
	
	/**
	 * Updates all cells, including dead cells.
	 */
	public void update() {		
		updateTick++;
		
		for (int state = 0; state < 2; state++) {
			for (int x = 0; x < columns; x++) {
				for (int y = 0; y < rows; y++) {
					
					// Brings spawning cells to life, kills dying cells.
					if (state == 0) {
						if (getState(x, y) == Cell.SPAWNING) {
							getCell(x, y).onBirth(this, x, y);
						}
						else if (getState(x, y) == Cell.DYING) {
							getCell(x, y).onDeath(this, x, y);
						}
					}
					
					else if (state == 1) {
						if (isActiveCell(x, y))
							getCell(x, y).update(this, x, y);
					}
					
					// Note that dead cells are not updated directly, but
					// are updated through live neighbor cells
				}
			}
		}
	}

	/**
	 * Called upon cell creation by user.
	 */
	public void createCell(int x, int y, int type) {
		Cell.getCellFromId(type).onCreation(this, x, y);
	}
	
	/**
	 * Called upon cell deletion by user.
	 */
	public void deleteCell(int x, int y) {
		if (!isActiveCell(x, y)) return;
		Cell.getCellFromId(cellsType[x][y]).onDeletion(this, x, y);
	}
	
	
	
	/* Rendering Functions */
	
	/**
	 * Renders the cell map.
	 */
	public void draw(Graphics2D g) {
		for(int x = 0; x < columns; x++) {
			for(int y = 0; y < rows; y++) {
				if (isActiveCell(x, y)) {
					g.setColor(this.getCellColour(x, y));
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
	
	
	/* Getters and Setters*/
	
	public int getUpdateTick() {
		return updateTick;
	}
	
	public void setId(int x, int y, int id) {
		if (isInBounds(x, y))
			cellsType[x][y] = id;
	}
	
	public Cell getCell(int x, int y) {
		return Cell.getCellFromId(getCellId(x, y));
	}
	public int getCellId(int x, int y) {
		if (!isActiveCell(x, y)) return Cell.CELL_DEAD.ID;
		return cellsType[x][y];
	}
	
	public void setState(int x, int y, byte state) {
		if (isInBounds(x, y))
			cellsState[x][y] = state;
	}
	public void incrementAge(int x, int y) {
		if (isInBounds(x, y))
			cellsAge[x][y]++;
	}
	public void setAge(int x, int y, int age) {
		if (isInBounds(x, y))
			cellsAge[x][y] = age;
	}
	public void setGeneration(int x, int y, int generation) {
		if (isInBounds(x, y))
			cellsGeneration[x][y] = generation;
	}
	public void setMetadata(int x, int y, byte metadata) {
		if (isInBounds(x, y))
			cellsMetadata[x][y] = metadata;
	}
	
	public byte getState(int x, int y) {
		if (!isInBounds(x, y)) return Cell.DEAD;
		return cellsState[x][y];
	}
	public int getAge(int x, int y) {
		if (!isInBounds(x, y)) return 0;
		return cellsAge[x][y];
	}
	public int getGeneration(int x, int y) {
		if (!isInBounds(x, y)) return 0;
		return cellsGeneration[x][y];
	}
	public byte getMetadata(int x, int y) {
		if (!isInBounds(x, y)) return 0;
		return cellsMetadata[x][y];
	}
	public Color getCellColour(int x, int y) {
		if (!isActiveCell(x, y)) return Settings.DEFAULT_DEAD_CELL_COLOUR;
		return getCell(x, y).getColour(this, x, y);
	}
	public boolean isActiveCell(int x, int y) {
		if (!isInBounds(x, y)) return false;
		return cellsType[x][y] != Cell.CELL_DEAD.ID && (getState(x, y) == Cell.ALIVE || getState(x, y) == Cell.DYING);
	}
	
	
	/* Helper Functions */
	
	/**
	 * Gets the number of adjacent live cells of a certain type, at a given point.
	 */
	public int getAdjacentCellsOfId(int x, int y, int id) {		
		int adjacentCells = 0;
		for (int tx = x - 1; tx <= x + 1; tx++) {
			for (int ty = y - 1; ty <= y + 1; ty++) {
				if (tx == x && ty == y) continue;
				if (isActiveCell(tx, ty) && getCellId(tx, ty) == id) {
					adjacentCells++;
				}
			}
		}
		
		return adjacentCells;
	}
	
	/**
	 * Gets the largest generation of adjacent live cells to determine the generation of the new cell.
	 */
	public int getGenerationOfAdjacentCells(int x, int y) {
		int generation = 0;
		for (int tx = x - 1; tx <= x + 1; tx++) {
			for (int ty = y - 1; ty <= y + 1; ty++) {
				if (tx == x && ty == y) continue;
				if (isActiveCell(tx, ty)) {
					generation = Math.max(getGeneration(tx, ty), generation);
				}
			}
		}
		
		return generation;
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
