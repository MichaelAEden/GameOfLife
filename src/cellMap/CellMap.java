package cellMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import main.GamePanel;

public class CellMap 
{	
	private int horizontalIndent;
	private int verticalIndent;
	private int width;
	private int height;
	
	private boolean[][] map;
	private boolean[][] newMap;
	private int columns;
	private int rows;
	
	private int cellWidth;
	private int cellHeight;
	
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
	
	private void init()
	{	
		map = new boolean[columns][rows];
		newMap = new boolean[columns][rows];
		for(int x = 0; x < columns; x++)
		{
			map[x] = new boolean[rows];
			newMap[x] = new boolean[rows];
		}
	}
	
	public void spawnCellOnMouseClick(int x, int y, boolean cell) //oldMap
	{
		if (!isInBounds(x, y)) return;
		map[x][y] = cell;
	}
	public boolean getCellAt(int x, int y) //oldMap
	{
		if (!isInBounds(x, y)) return false;
		return map[x][y];
	}
	public void setCellAt(int x, int y, boolean cell) //newMap
	{
		if (!isInBounds(x, y)) return;
		newMap[x][y] = cell;
	}
	
	public boolean isInBounds(int x, int y)
	{
		return (x < columns && y < rows) && (x >= 0 && y >= 0);
	}
	
	public void update() 
	{
		clone(map, newMap);
		for(int x = 0; x < columns; x++)
		{
			for(int y = 0; y < rows; y++)
			{
				if (!getCellAt(x, y) && willCellSpawnAt(x, y))
				{
					setCellAt(x, y, true);
				}
			}
		}
		for(int x = 0; x < columns; x++)
		{
			for(int y = 0; y < rows; y++)
			{
				if (getCellAt(x, y) && willCellDieAt(x, y))
				{
					setCellAt(x, y, false);
				}
			}
		}
		clone(newMap, map);
	}
	
	private void clone(boolean map1[][], boolean map2[][])
	{
		for(int x = 0; x < columns; x++)
		{
			for(int y = 0; y < rows; y++)
			{
				map2[x][y] = map1[x][y];
			}
		}
	}
	
	private boolean willCellSpawnAt(int x, int y) //oldMap
	{
		return (getNumberOfAdjacentCells(x, y) == 3);
	}
	private boolean willCellDieAt(int x, int y) //oldMap
	{
		return (getNumberOfAdjacentCells(x, y) <= 1 || getNumberOfAdjacentCells(x, y) >= 4);
	}
	private int getNumberOfAdjacentCells(int x, int y) //oldMap
	{
		int adjacentCells = 0;
		for(int tx = x - 1; tx <= x + 1; tx++)
		{
			for(int ty = y - 1; ty <= y + 1; ty++)
			{
				if(tx == x && ty == y) continue;
				if (getCellAt(tx, ty))
				{
					adjacentCells++;
				}
			}
		}
		return adjacentCells;
	}
	
	public void draw(Graphics2D g)
	{
		g.setColor(Color.YELLOW);
		for(int x = 0; x < columns; x++)
		{
			for(int y = 0; y < rows; y++)
			{
				if (getCellAt(x, y))
				{
					g.fillRect(this.getPixelFromCell(x, y).x, getPixelFromCell(x, y).y, cellWidth, cellHeight);
				}
			}
		}
		g.setColor(new Color(50, 50, 50));
		for(int x = 0; x <= columns; x++)
		{
			g.drawLine(getPixelFromCell(x, 0).x, verticalIndent, getPixelFromCell(x, 0).x, verticalIndent + height);
		}
		for(int y = 0; y <= rows; y++)
		{
			g.drawLine(horizontalIndent, getPixelFromCell(0, y).y, horizontalIndent + width, getPixelFromCell(0, y).y);
		}
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
