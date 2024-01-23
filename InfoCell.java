import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
public class InfoCell{
	private int row, col, numOfAdjacentMines, horizontalPosition, verticalPosition;
	private String status = new String();
	private ImageIcon imageIcon;
	private Image image;

	InfoCell(int row, int col, int numOfAdjacentMines){
		this.row = row;
		this.col = col;
		this.numOfAdjacentMines = numOfAdjacentMines;
	}
	public void draw(Graphics g){
		g.drawImage(getImage(), getHorizontalPosition(), getVerticalPosition(), null);
	}
	public int getHorizontalPosition(){
		horizontalPosition =  ((col * Configuration.CELL_SIZE));
		return horizontalPosition;
	}
	public int getVerticalPosition(){
		verticalPosition =  ((row * Configuration.CELL_SIZE));
		return verticalPosition;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		if(status.equals(Configuration.STATUS_OPENED) || 
		   status.equals(Configuration.STATUS_COVERED) ||
		   status.equals(Configuration.STATUS_MARKED) ||
		   status.equals(Configuration.STATUS_WRONGLY_MARKED))
			this.status = status;

	}
	public Image getImage(){
		if(status.equals(Configuration.STATUS_OPENED)){
			switch(getNumOfAdjacentMines()){
				case 0:
					imageIcon = new ImageIcon("img/info_0.png");
					break;
				case 1:
					imageIcon = new ImageIcon("img/info_1.png");
					break;
				case 2:
					imageIcon = new ImageIcon("img/info_2.png");
					break;
				case 3:
					imageIcon = new ImageIcon("img/info_3.png");
					break;
				case 4:
					imageIcon = new ImageIcon("img/info_4.png");
					break;
				case 5:
					imageIcon = new ImageIcon("img/info_5.png");
					break;
				case 6:
					imageIcon = new ImageIcon("img/info_6.png");
					break;
				case 7:
					imageIcon = new ImageIcon("img/info_7.png");
					break;
				case 8:
					imageIcon = new ImageIcon("img/info_8.png");
					break;
			}
		}
		else if(status.equals(Configuration.STATUS_COVERED))
			imageIcon = new ImageIcon("img/covered_cell.png");
		else if(status.equals(Configuration.STATUS_MARKED))
			imageIcon = new ImageIcon("img/marked_cell.png");
		else if(status.equals(Configuration.STATUS_WRONGLY_MARKED))
			imageIcon = new ImageIcon("img/wrong_mark.png");
		image = imageIcon.getImage();
		return image;
	}
	public int getNumOfAdjacentMines(){
		return numOfAdjacentMines;
	}
}
