package cellMap;

import java.awt.Color;

public class CellGrowerB extends Cell {
	
	public CellGrowerB() {
		super();
		
		neighboursCausingDeathMax = 5;
		neighboursCausingDeathMin = 0;
		neighboursCausingBirthMax = 2;
		neighboursCausingBirthMin = 1;
		
		colour = Color.BLUE;
		
		updateRate = 4;
	}
}
