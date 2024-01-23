import java.util.Random;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.io.File;
public class Minefield{
	private Object[][] field;
	private int numRows, numColumns, numMines;

	Minefield(){
		new Minefield(10,10,10);
	}
	Minefield(int numRows, int numColumns, int numMines){
		if(numRows > 0 && numColumns > 0){
			this.numRows = numRows;
			this.numColumns = numColumns;
			this.numMines = numMines;
			field = new Object[numRows][numColumns];
		}
		mineLaying(numMines);
		addInfoCells();
	}
	public void mineLaying(int numOfMines){
		Random r = new Random();
		MineCell cell;
		for(int i = 0; i < numOfMines; i++){
			int a = r.nextInt(numRows);
			int b = r.nextInt(numColumns);
			if(getCellByRowCol(a,b) != null){
				i--;
				continue;
			}
			cell = new MineCell(a,b);	
			field[a][b] = cell;
			((MineCell)field[a][b]).setStatus(Configuration.STATUS_COVERED);
		}
	}
	public void addInfoCells(){
		InfoCell cell;
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numColumns; j++){
				if(field[i][j] == null){
					field[i][j] = adjacentMinesHelper(i,j);
					((InfoCell)field[i][j]).setStatus(Configuration.STATUS_COVERED);
				}		
			}
		}
	}
	private InfoCell adjacentMinesHelper(int i, int j){
		int count = 0;
		InfoCell cell;
		for(int xOff = -1; xOff <= 1; xOff++){
			for(int yOff = -1; yOff <= 1; yOff++){
				int a = i + xOff;
				int b = j + yOff;
				if(a > -1 && a < numRows && b > -1 && b < numColumns){
					if(field[a][b] != null && field[a][b].getClass() == MineCell.class)
						count++;
				}
			}
		}
		cell = new InfoCell(i,j,count);
		return cell;

	}
	public void draw(Graphics g){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numColumns; j++){
				if(field[i][j].getClass() == MineCell.class)
					((MineCell)field[i][j]).draw(g);
				else if(getCellByRowCol(i,j).getClass() == InfoCell.class)
					((InfoCell)field[i][j]).draw(g);
			}
		}
	}
	public Object getCellByScreenCoordinates(int x, int y){
		return getCellByRowCol(x/Configuration.CELL_SIZE, y/Configuration.CELL_SIZE);
	}
	public Object getCellByRowCol(int row, int col){
		return field[row][col];
	}
	public void setMineCell(int row, int col, MineCell cell){
		field[row][col] = cell;
	}
	public void setInfoCell(int row, int col, InfoCell cell){
		field[row][col] = cell;
	}
	public int countCellsWithStatus(String status){
		int count = 0;
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numColumns; j++){
				if( field[i][j] != null && getCellByRowCol(i,j).getClass() == MineCell.class && ((MineCell)getCellByRowCol(i,j)).getStatus().equals(status))
					count++;
				else if(getCellByRowCol(i,j).getClass() == InfoCell.class && ((InfoCell)getCellByRowCol(i,j)).getStatus().equals(status))
					count++;
			}
		}
		return count;
	}
	public void openCells(Object cell){
		if(cell.getClass() == InfoCell.class && ((InfoCell)cell).getNumOfAdjacentMines() == 0){
			int row = (((InfoCell)cell).getVerticalPosition())/Configuration.CELL_SIZE;
			int col = (((InfoCell)cell).getHorizontalPosition())/Configuration.CELL_SIZE;
			for(int xOff = -1; xOff <= 1; xOff++){
				for(int yOff = -1; yOff <= 1; yOff++){
					int a = row + xOff;
					int b = col + yOff;
					if(a > -1 && a < numRows && b > -1 && b < numColumns){
						Object newCell = field[a][b];
						if(newCell.getClass() == InfoCell.class && !((InfoCell)newCell).getStatus().equals(Configuration.STATUS_OPENED)){
							((InfoCell)newCell).setStatus(Configuration.STATUS_OPENED);
							openCells((InfoCell)newCell);
						}
					}
				}
			}
		}
	}
	public void revealIncorrectMarkedCells(){
		for(int i=0; i<Configuration.ROWS; i++){
			for(int j=0; j<Configuration.COLS; j++){
				if(field[i][j].getClass() == InfoCell.class && 
				((InfoCell)field[i][j]).getStatus().equals(Configuration.STATUS_MARKED)){
					((InfoCell)(field[i][j])).setStatus(Configuration.STATUS_WRONGLY_MARKED);
				}
			}
		}
	}
	
}
