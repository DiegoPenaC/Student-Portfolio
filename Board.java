import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Dimension;

public class Board extends JPanel
{
	private int height,width,mines;
	private JLabel statusbar;
	private Minefield minefield;
	private int remaining;
	public Board(int height, int width, int mines, JLabel statusbar){
		this.height = height;
		this.width = width;
		this.mines = mines;
		this.remaining = mines;
		this.statusbar = statusbar;
		minefield = new Minefield(Configuration.ROWS,Configuration.COLS,Configuration.MINES);
		setPreferredSize(new Dimension(Configuration.BOARD_WIDTH, Configuration.BOARD_HEIGHT));
		addMouseListener(new MouseReader(this));
	}

	@Override
	public void paintComponent(Graphics g){
		getMinefield().draw(g);
	}
	public Minefield getMinefield(){
		return minefield;
	}
	public boolean isGameOver(){
		for(int i = 0; i < Configuration.ROWS; i++){
			for(int j = 0; j < Configuration.COLS; j++){
				if(minefield.getCellByRowCol(i,j).getClass() == MineCell.class && 
			          ((MineCell)getMinefield().getCellByRowCol(i,j)).getStatus().equals(Configuration.STATUS_OPENED)){ 
					minefield.revealIncorrectMarkedCells();
					setStatusbar("Game over - You lost!");
					return true;
				}
			}
		}
		boolean tell = true;
		for(int i = 0; i < Configuration.ROWS; i++){
			for(int j = 0; j < Configuration.COLS; j++){
				if(minefield.getCellByRowCol(i,j).getClass() == InfoCell.class &&
		  		  ((InfoCell)(minefield.getCellByRowCol(i,j))).getStatus().equals(Configuration.STATUS_COVERED)){
					tell = false;
				}
			}
		}
		if(tell == true){
			setStatusbar("Game over - You won!");
		}
		return tell;
	}
	public void setStatusbar(String text){
		statusbar.setText(text);
	}
	public String getStatusbar(){
		return statusbar.getText();
	}
	public boolean removeMine(){
		if(remaining >= 0){
			remaining--;
			setStatusbar(remaining + " mines remaining");
			return true;
		}
		else{
			setStatusbar("Invalid Action");
			return false;
		}
	}
	public boolean addMine(){
		if(remaining <= this.remaining){
			remaining++;
			setStatusbar(remaining + " mines remaining");
			return true;
		}
		return false;
	}
	public void mouseClickOnLocation(int x, int y, String button){
		if(button.equals("left")){
			if(getMinefield().getCellByScreenCoordinates(y,x).getClass() == MineCell.class){
				((MineCell)(getMinefield().getCellByScreenCoordinates(y,x))).setStatus(Configuration.STATUS_OPENED);
				minefield.revealIncorrectMarkedCells();
				isGameOver();
				if(isGameOver() == true){
					
				}
			}

			else if(getMinefield().getCellByScreenCoordinates(y,x).getClass() == InfoCell.class){
				((InfoCell)(getMinefield().getCellByScreenCoordinates(y,x))).setStatus(Configuration.STATUS_OPENED);
				getMinefield().openCells((InfoCell)(getMinefield().getCellByScreenCoordinates(y,x)));
				isGameOver();
			}
		}
		else if(button.equals("right")){
			if(getMinefield().getCellByScreenCoordinates(y,x).getClass() == MineCell.class){
				if(((MineCell)(getMinefield().getCellByScreenCoordinates(y,x))).getStatus().equals(Configuration.STATUS_MARKED)){
					((MineCell)(getMinefield().getCellByScreenCoordinates(y,x))).setStatus(Configuration.STATUS_COVERED);
					addMine();
				}
				else if(((MineCell)(getMinefield().getCellByScreenCoordinates(y,x))).getStatus().equals(Configuration.STATUS_COVERED)){
					((MineCell)(getMinefield().getCellByScreenCoordinates(y,x))).setStatus(Configuration.STATUS_MARKED);
					removeMine();
				}
			}
			else if(getMinefield().getCellByScreenCoordinates(y,x).getClass() == InfoCell.class)
				if(((InfoCell)(getMinefield().getCellByScreenCoordinates(y,x))).getStatus().equals(Configuration.STATUS_MARKED)){
					((InfoCell)(getMinefield().getCellByScreenCoordinates(y,x))).setStatus(Configuration.STATUS_COVERED);
					addMine();
				}
				else if(((InfoCell)(getMinefield().getCellByScreenCoordinates(y,x))).getStatus().equals(Configuration.STATUS_COVERED)){
					((InfoCell)(getMinefield().getCellByScreenCoordinates(y,x))).setStatus(Configuration.STATUS_MARKED);
					removeMine();
				}
		}
		repaint();
	}

}
