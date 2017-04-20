package cellMap;

import java.awt.Color;

import ui.Settings;

public class Cell {
	
	private Color colour;
	
	private int age;
	private int nth_generation;			// Number of generations before the cell
	private boolean isActive;			// Whether cell is currently active
	private boolean spawnNextCycle;		// Spawn cell after next iteration
	private boolean dieNextCycle;		// Kill cell after next iteration
	
	/*
	 * Constructor.
	 */
	public Cell() {
		age = 0;
		nth_generation = 0;
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
		else if (dieNextCycle) {
			kill();
		}
		
		if (isActive) {
			age++;
			
			float hue = 0.4f;
			if (Settings.SHOW_AGE) {
				if (Settings.CELL_LIFE == Settings.IMMORTAL) {
					hue = Math.min(age / 200.0f, 1.0f);
				}
				else {
					hue = (((age * 1.0f) / Settings.CELL_LIFE) + 0.3f) % 1.0f;
				}
			}

			float sat = Settings.SHOW_GENERATION ? Math.min(nth_generation / 50.0f, 1.0f) : 1.0f;
			float val = 1.0f;
			
			colour = new Color(Color.HSBtoRGB(hue, sat, val));
		}
	}
	
	
	
	/* Getters and Setters */
	
	public int getAge() {
		return age;
	}
	public int getGeneration() {
		return nth_generation;
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
		nth_generation = 0;
		
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
	public void spawnNextCycle(int nth_generation) {
		this.nth_generation = nth_generation;
		spawnNextCycle = true;
	}
}
