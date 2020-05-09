import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
Sebastian Cavallin, 9th Grade
Doral Academy INTERMEDIATE Division
2017-2018 American Computer Science League
Contest #3 “ACSL Walk” DUE DATE 02/16/18
CLASS NAME: AcslWalk
INPUT FILE: WALKINT.IN
On my honor I have neither given nor received help,
nor will I give help on this program
*/

public class AcslWalk {
	static int[][] map = new int[8][8];
	static int[][] visited = new int[8][8];
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("WALKINT.IN"));
		
		// makes map
		for(int i=0; i<8; i++) {
			String bin = hexToBinary(scan.next());
			for(int j=0; j<bin.length(); j++) {
				map[i][j] = Character.getNumericValue(bin.charAt(j));
			}
		}
		
		int five = 5;
		while(five --> 0) {
			scan.nextLine();
			// reset visited
			visited = new int[8][8];
			
			int row = scan.nextInt()-1; int col = scan.nextInt()-1;
			int origin;
			
			switch(scan.next().charAt(0)) {
			case 'L' : origin=0; break;
			case 'A' : origin=1; break;
			case 'R' : origin=2; break;
			case 'B' : origin=3; break;
			default : origin = -1;
			}
			if(origin<0) System.err.println("Something went horribly wrong.");
			
			Point point = new Point(row, col, origin);
			//System.out.println("Starting point: " + point);
			
			int moves = scan.nextInt();
			for(int i=0; i<moves; i++) {
				point = whereTo(point);
			}
			System.out.println((point.row+1) + ", " + (point.col+1));
		}
		
		scan.close();
	}
	
	// ONLY for seeing what direction its supposed to go
	public static int direction(Point point) {
		if(map[point.row][point.col] == 0) {
			//System.out.println("Since " + point + " is a zero and it came from " + point.origin() + ", it should move in direction " + ((point.origin+2)%4));
			return (point.origin+2)%4;
		}
		visited[point.row][point.col]++;
		//System.out.println("Since " + point + " is a one, it's been visited " + visited[point.row][point.col] + " times, and it came from " + point.origin()
		//+ ", it should move in direction " + ((point.origin+visited[point.row][point.col])%4));
		return (point.origin+visited[point.row][point.col])%4;
	}
	
	
	public static Point whereTo(Point point) {
		int h = direction(point);
		switch(h) {
		case 0 : point.col-=1; break;
		case 1 : point.row-=1; break;
		case 2 : point.col+=1; break;
		case 3 : point.row+=1; break;
		}
		
		//bounds
		if(point.row==-1) point.row=7;
		if(point.col==-1) point.col=7;
		if(point.row==8) point.row=0;
		if(point.col==8) point.col=0;
		
		point.origin=(h+2)%4;
		return point;
	}
	
	public static String hexToBinary(String hex) {
	    String bin = Integer.toBinaryString(Integer.parseInt(hex, 16));
	    if(bin.length()<8) {
//	    	bin="0000"+bin;
	    	int meme = 8-bin.length();
	    	for(int i=0;i<meme;i++) {
	    		bin="0"+bin;
	    	}
	    }
	    return bin;
	}
	
}

class Point {
	public int row, col, origin;
	public Point(int r, int c, int o) {
		row=r;
		col=c;
		origin=o;
	}
	public String toString() {
		return "(" + row + ", " + col + ")";
	}
	public String origin() {
		switch(origin) {
		case 0 : return "the left";
		case 1 : return "above";
		case 2 : return "the right";
		case 3 : return "below";
		default : return "????";
		}
	}
}
