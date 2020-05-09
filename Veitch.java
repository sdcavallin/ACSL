import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
Per 3 Name Sebastian Cavallin Grade 11
Doral Academy SENIOR-5 Division
2019-2020 American Computer Science League
Contest #3 "Veitch" DUE Date 03/06/2020
CLASS NAME: Veitch
INPUT FILE: VEITCH.IN
On my honor I have neither given nor received help,
nor will I give help on this program
*/

public class Veitch {

	public static int[][] map;

	// A = A
	// a = ~A

	public static final String[][] terms =
		{
				{"ABcd", "ABCd", "aBCd", "aBcd"},
				{"ABcD", "ABCD", "aBCD", "aBcD"},
				{"AbcD", "AbCD", "abCD", "abcD"},
				{"Abcd", "AbCd", "abCd", "abcd"}
		};

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(new File("VEITCH.IN"));

		int cases = 5;
		while(cases-->0){
			String str = scan.nextLine();
			map = new int[4][4];

			char[] car = str.toCharArray();
			for(int i=0; i<4; i++) {
				char c = car[i];
				String temp = Integer.toString(Integer.parseInt(c+"", 16), 2);

				int x = 0;
				for(int j=4-temp.length(); j<4; j++) {
					map[i][j] = Integer.parseInt(temp.substring(x,x+1));
					x++;
				}
			}

			/*for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}*/

			ArrayList<String> list = new ArrayList<String>();
			for(int i=0; i<4; i++) {
				for(int j=0; j<4; j++) {
					if(map[i][j] == 1) {
						list.add(terms[i][j]);
						//System.out.print(terms[i][j] + " ");
					}
				}
			}
			//System.out.println();
			String answer = "";

			// count 8's (terms of 1)
			String[] test1 = {"A", "B", "C", "D", "a", "b", "c", "d"};
			for(int i=0; i<8; i++) {
				ArrayList<String> temp = new ArrayList<String>();
				for(String s : list) {
					if(s.contains(test1[i])) {
						temp.add(s);
					}
				}
				if(temp.size()==8) {
					for(String s : temp) {
						list.remove(s);
					}
					answer+=test1[i] + "+";
				}
			}

			String[][] match = {
					{"A", "B", "C", "D"},
					{"a", "b", "c", "d"}
			};
			
			
			
			// count 4's (terms of 2)
			ArrayList<Rect> listof4 = new ArrayList<Rect>();
			do {
				listof4.clear();
				for(int i=0; i<4; i++) {
					for(int j=i+1; j<4; j++) {
						for(int x=0; x<2; x++) {
							for(int y=0; y<2; y++) {
								ArrayList<String> temp = new ArrayList<String>();
								String test = match[x][i] + match[y][j];
								//System.out.println(test);

								for(String s : list) {
									if(s.contains(test.substring(0,1)) && s.contains(test.substring(1,2))) {
										temp.add(s);
									}
								}
								if(temp.size()==4) {
									/*for(String s : temp) {
								list.remove(s);
							}*/
									Rectangle rect = new Rectangle(0,0,-1,-1);

									ArrayList<String> useless = new ArrayList<String>();
									for(String s : temp) {
										rect.add(loc(s));
										useless.add(s);
									}
									//answer+=test + " + ";
									listof4.add(new Rect(rect, useless, test));
								}
							}
						}
					}
				}

				//System.out.println(listof4.toString());
				Collections.sort(listof4);
				//System.out.println(listof4.toString());

				if(listof4.isEmpty()) break;
				Rect aaa = listof4.remove(0);
				ArrayList<String> toRemove = aaa.list;

				for(String s : toRemove) {
					list.remove(s);
				}

				answer+=aaa.expr + "+";

			} while(!listof4.isEmpty());

			// count 2's (terms of 3)
			ArrayList<Pair> listof2 = new ArrayList<Pair>();
			do {
				listof2.clear();

				for(int i=0; i<4; i++) {
					for(int j=i+1; j<4; j++) {
						for(int k=j+1; k<4; k++) {
							for(int x=0; x<2; x++) {
								for(int y=0; y<2; y++) {
									for(int z=0; z<2; z++) {
										ArrayList<String> temp = new ArrayList<String>();
										String test = match[x][i] + match[y][j] + match[z][k];
										//System.out.println(test);

										for(String s : list) {
											if(s.contains(test.substring(0,1)) && s.contains(test.substring(1,2)) && s.contains(test.substring(2,3))) {
												temp.add(s);
											}
										}
										if(temp.size()==2) {
											/*for(String s : temp) {
										list.remove(s);
									}*/
											ArrayList<String> useless = new ArrayList<String>();
											for(String s : temp) {
												useless.add(s);
											}
											//answer+=test + " + ";
											listof2.add(new Pair(useless, test));
										}
									}
								}
							}
						}
					}
				}

				//System.out.println(listof2.toString());
				Collections.sort(listof2);
				//System.out.println(listof2.toString());

				if(listof2.isEmpty()) break;
				Pair aaa = listof2.remove(0);
				ArrayList<String> toRemove = aaa.list;

				for(String s : toRemove) {
					list.remove(s);
				}

				answer+=aaa.expr + "+";

			} while(!listof2.isEmpty());


			for(String s : list)
				answer += s + "+";

			System.out.println(answer.substring(0,answer.length()-1).replaceAll("a", "~A").replaceAll("b", "~B").replaceAll("c", "~C").replaceAll("d", "~D"));
		}
		scan.close();
	}

	// to is exclusive
	public static int countOnes(int fromi, int toi, int fromj, int toj) {
		int count = 0;

		for(int i=fromi; i<toi; i++) {
			for(int j=fromj; j<toj; j++) {
				if(map[i][j] == 1)
					count++;
			}
		}
		return count;
	}

	public static void setZero(int fromi, int toi, int fromj, int toj) {

		for(int i=fromi; i<toi; i++) {
			for(int j=fromj; j<toj; j++) {
				map[i][j] = 0;
			}
		}

	}

	public static Point loc(String str) {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(terms[i][j].equals(str)) {
					return new Point(j,i);
				}
			}
		}
		return new Point(9999, 9999);
	}

	public static class Rect implements Comparable<Rect>{
		public Rectangle r;
		public ArrayList<String> list;
		public int type;
		public String expr;

		public Rect(Rectangle rectangle, ArrayList<String> arr, String str) {
			r = rectangle;
			list = arr;
			expr = str;

			type = 99;
			if(r.width==3) type=1;
			if(r.height==3) type=2;
			if(r.width==1 && r.height == 1) type=3;
			if(r.width==3 && r.height == 1) type=4;
			if(r.width==1 && r.height == 3) type=4;
			if(r.width==3 && r.height==3) type=5;

		}

		@Override
		public int compareTo(Rect o) {
			if(type==o.type) {
				if(r.getMinY()==o.r.getMinY()) {
					return (int) (r.getMinX()-o.r.getMinX());
				}
				return (int) (r.getMinY()-o.r.getMinY());
			}
			return type-o.type;
		}

		@Override
		public String toString() {
			return type+"["+expr+"]"+r.toString();
		}

	}

	public static class Pair implements Comparable<Pair>{
		public ArrayList<String> list;
		public int type;
		public String expr;
		public Point a, b;

		public Pair(ArrayList<String> arr, String str) {
			list=arr;
			expr=str;

			a = loc(arr.get(0));
			b = loc(arr.get(1));

			type=99;
			if(a.y==b.y) {
				type=1;
				if(Math.abs(a.x-b.x) != 1) {
					type=3;
				}
			}
			if(a.x==b.x) {
				type=2;
				if(Math.abs(a.y-b.y) != 1) {
					type=4;
				}
			}

		}

		@Override
		public int compareTo(Pair o) {
			if(type==o.type) {
				if(a.y==o.a.y) {
					return (int) (a.x-o.a.x);
				}
				return (int) (a.y-o.a.y);
			}
			return type-o.type;
		}

		@Override
		public String toString() {
			return type+"["+expr+"]";
		}

	}

}

