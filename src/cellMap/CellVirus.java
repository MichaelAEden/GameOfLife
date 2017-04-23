package cellMap;

import java.awt.Color;

public class CellVirus extends Cell {

	public CellVirus() {
		super();
		
		colour = Color.PINK;
		
		updateRate = 1;
		cellLife = 10;
	}
	
	@Override
	protected boolean shouldSpawn(CellMap cellMap, int x, int y, int parentX, int parentY) {
		return (cellMap.isActiveCell(x, y) && cellMap.getCellId(x, y) != this.ID);
	}
	
	@Override
	protected boolean shouldDie(CellMap cellMap, int x, int y) {
		return cellMap.getAge(x, y) > cellLife;
	}
}
