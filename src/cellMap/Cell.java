package cellMap;

import java.awt.Color;
import java.util.ArrayList;

import ui.Settings;

public abstract class Cell {
	
	private static ArrayList<Cell> cells = new ArrayList<Cell>();
	private static int currentID = 0;
	
	// Cell state constants
	public static final byte DEAD = 0;
	public static final byte SPAWNING = 1;
	public static final byte ALIVE = 2;
	public static final byte DYING = 3;

	// Cell types
	public static final Cell CELL_DEAD = new CellDead();
	public static final Cell CELL_BASIC = new CellBasic();
	public static final Cell CELL_GROWER_A = new CellGrowerA();
	public static final Cell CELL_GROWER_B = new CellGrowerB();
	
	public static int MAX_ID = currentID - 1;
	
	// Cell attributes
	public int ID;
	protected int cellLife = Settings.CELL_LIFE;
	protected Color colour = Settings.DEFAULT_CELL_COLOUR;
	protected int updateRate = Settings.DEFAULT_UPDATE_RATE;
	
	// Cell rules
	protected int neighboursCausingDeathMax = Settings.NEIGHBOURS_CAUSING_DEATH_MAX;	// n or more surrounding cells and cell dies
	protected int neighboursCausingDeathMin = Settings.NEIGHBOURS_CAUSING_DEATH_MIN;	// n or less surrounding cells and cell dies
	protected int neighboursCausingBirthMax = Settings.NEIGHBOURS_CAUSING_BIRTH_MAX;	// Between n - m surrounding cells - inclusive - cell is born
	protected int neighboursCausingBirthMin = Settings.NEIGHBOURS_CAUSING_BIRTH_MIN;
	
	/**
	 * Constructor.
	 */
	public Cell() {
		ID = currentID;
		currentID++;
		
		cells.add(this);
	}
	
	/**
	 * Gets Cell object given its ID.
	 */
	public static Cell getCellFromId(int id) {
		return cells.get(id);
	}
	
	
	
	/**
	 * Called upon cell birth.
	 */
	public void onSpawn(CellMap cellMap, int x, int y) {
		int generation = cellMap.getGenerationOfAdjacentCells(x, y);
		cellMap.setGeneration(x, y, generation + 1);
	}

	/**
	 * Called upon cell creation by user.
	 */
	public void onCreation(CellMap cellMap, int x, int y) {
		cellMap.setGeneration(x, y, 0);
	}
	
	/**
	 * Called upon cell death.
	 */
	public void onDeath(CellMap cellMap, int x, int y) {
		cellMap.setGeneration(x, y, 0);
		cellMap.setAge(x, y, 0);
	}

	/**
	 * Called upon cell deletion by user.
	 */
	public void onDeletion(CellMap cellMap, int x, int y) {
		cellMap.setGeneration(x, y, 0);
		cellMap.setAge(x, y, 0);
	}
	
	
	/**
	 * Updates the state of the cell.
	 */
	public void update(CellMap cellMap, int x, int y) {
		if (cellMap.getUpdateTick() % updateRate == 0) {
			updateSelf(cellMap, x, y);
			updateNeighbours(cellMap, x, y);
		}
	}
	
	/**
	 * Updates the cell's age and state.
	 */
	public void updateSelf(CellMap cellMap, int x, int y) {
		if (cellMap.isActiveCell(x, y))
		{
			cellMap.incrementAge(x, y);
		
			if (shouldDie(cellMap, x, y))
				cellMap.killCell(x, y);
		}
	}
	
	/**
	 * Spawns neighbor cells accordingly.
	 */
	public void updateNeighbours(CellMap cellMap, int x, int y) {
		for (int tx = x - 1; tx <= x + 1; tx++) {
			for (int ty = y - 1; ty <= y + 1; ty++) {
				if (tx == x && ty == y) continue;
				if (shouldSpawn(cellMap, tx, ty)) {
					cellMap.spawnCell(tx, ty, this.ID);
				}
			}
		}
	}
	
	/**
	 * Returns whether cell at the given coordinates should be spawned next iteration
	 */
	protected boolean shouldSpawn(CellMap cellMap, int x, int y) {
		// By default, looks for neighbors of the same type only		
		int adjacentCells = cellMap.getAdjacentCellsOfId(x, y, this.ID);
		return (!cellMap.isActiveCell(x, y) &&
				(adjacentCells <= neighboursCausingBirthMax &&
				 adjacentCells >= neighboursCausingBirthMin));	
	}
	
	/**
	 * Returns whether cell at the given coordinates should die next iteration
	 */
	protected boolean shouldDie(CellMap cellMap, int x, int y) {
		// By default, looks for neighbors of the same type only
		int adjacentCells = cellMap.getAdjacentCellsOfId(x, y, this.ID);
		
		return (cellMap.isActiveCell(x, y) &&
				(adjacentCells >= neighboursCausingDeathMax ||
				 adjacentCells <= neighboursCausingDeathMin));
	}
	
	
	/* Getters and Setters */

	public Color getColour(CellMap cellMap, int x, int y) {
		return colour;
	}
}
