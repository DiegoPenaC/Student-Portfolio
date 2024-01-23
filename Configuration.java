import java.io.File;
import java.util.Scanner;
public class Configuration{
	public static int ROWS;
	public static int COLS;
	public static int CELL_SIZE;
	public static int MINES;
	public static int BOARD_WIDTH;
	public static int BOARD_HEIGHT;
	public static String STATUS_COVERED;
	public static String STATUS_OPENED;
	public static String STATUS_MARKED;
	public static String STATUS_WRONGLY_MARKED;
	public static void loadParameters(String filename){
		try{	
			File file = new File(filename);
			Scanner scan = new Scanner(file);
			while(scan.hasNext()){
				switch(scan.next()){
					case "ROWS":
						ROWS = scan.nextInt();
						break;
					case "COLS":
						COLS = scan.nextInt();
						break;
					case "CELL_SIZE":
						CELL_SIZE = scan.nextInt();
						break;
					case "MINES":
						MINES = scan.nextInt();
						break;
					case "STATUS_COVERED":
						STATUS_COVERED = scan.next();
						break;
					case "STATUS_OPENED":
						STATUS_OPENED = scan.next();
						break;
					case "STATUS_MARKED":
						STATUS_MARKED = scan.next();
						break;
					case "STATUS_WRONGLY_MARKED":
						STATUS_WRONGLY_MARKED = scan.next();
						break;
				}
			}
		}catch(Exception e){
			System.out.println("File not Found");
		}
		BOARD_HEIGHT = (ROWS * CELL_SIZE) + 1;
		BOARD_WIDTH = (COLS * CELL_SIZE) + 1;
	}
}
