package cellMap;

public class CellDead extends Cell {
	
	@Override
	public void update(CellMap cellMap, int x, int y) {
		System.out.println("Why was this called?");
	}
}
