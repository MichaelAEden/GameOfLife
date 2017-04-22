package cellMap;

import java.awt.Color;

public class CellGrowerA extends Cell {
	
	public CellGrowerA() {
		super();
		
		neighboursCausingDeathMax = 7;
		neighboursCausingDeathMin = 2;
		neighboursCausingBirthMax = 3;
		neighboursCausingBirthMin = 3;
		
		colour = Color.RED;
		
		updateRate = 1;
	}
	
	/**
	 * Returns whether cell at the given coordinates should be spawned next iteration
	 */
	protected boolean shouldSpawn(CellMap cellMap, int x, int y) {
		// By default, looks for neighbors of the same type only		
		int adjacentCells = cellMap.getAdjacentCellsOfId(x, y, this.ID);
		return (!(cellMap.isActiveCell(x, y) && cellMap.getCellId(x, y) == this.ID) && 
				(adjacentCells <= neighboursCausingBirthMax &&
				 adjacentCells >= neighboursCausingBirthMin));	
	}
}
