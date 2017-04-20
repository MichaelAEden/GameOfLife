package cellMap;

import java.awt.Color;

import ui.Settings;

public class Cell {
	
	private Color colour;
	
	private int age;
	private boolean isActive;			// Whether cell is currently active
	private boolean spawnNextCycle;		// Spawn cell after next iteration
	private boolean dieNextCycle;		// Kill cell after next iteration

	/*
	 * Constructor.
	 * 
	 * @param activateNow	Whether cell should be brought to life immediately, or next iteration
	 */
	public Cell() {
		age = 0;
		colour = Settings.DEFAULT_CELL_COLOUR;
		isActive = false;
		spawnNextCycle = false;
		dieNextCycle = false;
	}
	
	/*
	 * Updates the state of the cell.
	 */
	public void update(CellMap cellMap) {
		if (spawnNextCycle) {
			spawn();
		}
		else if (dieNextCycle ||
				(Settings.CELL_LIFE != Settings.IMMORTAL && age > Settings.CELL_LIFE)) {
			kill();
		}
		
		age++;
		colour = new Color(colour.getRed() - 3, colour.getBlue() - 3, colour.getGreen() - 3);
	}
	
	
	
	/* Getters and Setters */
	
	public int getAge() {
		return age;
	}
	public Color getColour() {
		return colour;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void kill() {
		isActive = false;
		
		age = 0;
		spawnNextCycle = false;
		dieNextCycle = false;
		colour = Settings.DEFAULT_CELL_COLOUR;
	}
	public void killNextCycle() {
		dieNextCycle = true;
	}
	
	public void spawn() {
		isActive = true;
		spawnNextCycle = false;
	}
	public void spawnNextCycle() {
		spawnNextCycle = true;
	}
}
