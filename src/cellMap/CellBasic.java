package cellMap;

import java.awt.Color;

import ui.Settings;

public class CellBasic extends Cell {
	
	@Override
	public Color getColour(CellMap cellMap, int x, int y) {
		float hue = 0.4f;
		if (Settings.SHOW_AGE) {
			if (cellLife == Settings.IMMORTAL) {
				hue = Math.min(cellMap.getAge(x, y) / 200.0f, 1.0f);
			}
			else {
				hue = (((cellMap.getAge(x, y) * 1.0f) / cellLife) + 0.3f) % 1.0f;
			}
		}

		float sat = Settings.SHOW_GENERATION ? (((cellMap.getGeneration(x, y) * 1.0f) / 200.0f) + 0.3f) % 1.0f : 1.0f;
		float val = 1.0f;
		
		return new Color(Color.HSBtoRGB(hue, sat, val));
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
