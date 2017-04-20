package ui;

import java.awt.Color;

public class Settings {
	
	
	/* Constants */
	
	public static final int IMMORTAL = -1;
	
	
	/* Game Settings */
	
	// Number of neighbors required for cells to die, and for cells to be born
	public static int NEIGHBOURS_CAUSING_DEATH_MAX = 4;
	public static int NEIGHBOURS_CAUSING_DEATH_MIN = 1;
	public static int NEIGHBOURS_CAUSING_BIRTH_MAX = 3;
	public static int NEIGHBOURS_CAUSING_BIRTH_MIN = 3;
	
	// Number of iterations the cell can survive before dying
	public static int CELL_LIFE = -1;	// By default, immortal
	
	
	/* Visual Settings */
	
	// Show the age of the cell visually
	public static boolean SHOW_LIFE = false;
	
	// Show lines of the cell grid
	public static boolean SHOW_GRID = false;
	
	public static Color DEFAULT_CELL_COLOUR = Color.WHITE;
	public static Color DEFAULT_TABLE_COLOUR = Color.BLACK;
	

	
	/* Getters and Setters */
	
	public static boolean setNeighboursCausingDeathMax(int neighbours) {
		if (!isValidNeighbourCount(neighbours) || neighbours <= NEIGHBOURS_CAUSING_DEATH_MIN) 
			return false;
		
		NEIGHBOURS_CAUSING_DEATH_MAX = neighbours;
		return true;
	}
	public static boolean setNeighboursCausingDeathMin(int neighbours) {
		if (!isValidNeighbourCount(neighbours) || neighbours >= NEIGHBOURS_CAUSING_DEATH_MAX) 
			return false;
		
		NEIGHBOURS_CAUSING_DEATH_MIN = neighbours;
		return true;
	}
	public static boolean setNeighboursCausingBirthMax(int neighbours) {
		if (!isValidNeighbourCount(neighbours) || neighbours <= NEIGHBOURS_CAUSING_BIRTH_MIN) 
			return false;
		
		NEIGHBOURS_CAUSING_BIRTH_MAX = neighbours;
		return true;
	}
	public static boolean setNeighboursCausingBirthMin(int neighbours) {
		if (!isValidNeighbourCount(neighbours) || neighbours >= NEIGHBOURS_CAUSING_BIRTH_MAX) 
			return false;
		
		NEIGHBOURS_CAUSING_BIRTH_MIN = neighbours;
		return true;	
	}
	
	public static boolean setCellLife(int cellLife) {
		if (cellLife < -1) 
			return false;
		
		CELL_LIFE = cellLife;
		return true;
	}
	
	
	
	/* Helpers */
	
	public static boolean isValidNeighbourCount(int neighbours) {
		return neighbours >= 0 && neighbours <= 8;
	}
}
