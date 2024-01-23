import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
public class MineCell{
	private int row, col, horizontalPosition, verticalPosition;
	private String status = new String();
	private ImageIcon imageIcon;
	private Image image;

	MineCell(int row, int col){
		this.row = row;
		this.col = col;
	}
	public void draw(Graphics g){
		g.drawImage(getImage(), getHorizontalPosition(), getVerticalPosition(), null);	
	}
	public int getHorizontalPosition(){
		return ((col*Configuration.CELL_SIZE));
	}
	public int getVerticalPosition(){
		return ((row*Configuration.CELL_SIZE));
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		if(status.equals(Configuration.STATUS_OPENED) || 
		   status.equals(Configuration.STATUS_COVERED) ||
		   status.equals(Configuration.STATUS_MARKED))
			this.status = status;
	}
	public Image getImage(){
		if(status.equals(Configuration.STATUS_OPENED))
			imageIcon = new ImageIcon("img/mine_cell.png");
		else if(status.equals(Configuration.STATUS_COVERED))
			imageIcon = new ImageIcon("img/covered_cell.png");
		else if(status.equals(Configuration.STATUS_MARKED))
			imageIcon = new ImageIcon("img/marked_cell.png");
		image = imageIcon.getImage();
		return image;
	}
}
