import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Patolli {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		int[] p = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
		int[] s = {9, 16, 25, 36, 49};

		ArrayList<Integer> primes = new ArrayList<Integer>();
		ArrayList<Integer> squares = new ArrayList<Integer>();

		for(int i : p) {
			primes.add(i);
		}

		for(int i : s) {
			squares.add(i);
		}

		// 0 = horizontal, 1 = vertical
		int[] direction = {-1,-1,1,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,1,1,1,0,1,1,1,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,1};

		int cases = 5;
		while(cases --> 0) {

			ArrayList<Integer>[] players = new ArrayList[2];
			players[0] = new ArrayList<Integer>(); // opponent
			players[1] = new ArrayList<Integer>(); // player

			players[0].add(scan.nextInt()); players[0].add(scan.nextInt()); players[0].add(scan.nextInt());
			players[1].add(scan.nextInt()); players[1].add(scan.nextInt()); players[1].add(scan.nextInt());

			int rolls = scan.nextInt();
			int player = 1;
			while(rolls --> 0) {
				player = (player+1)%2;
				Collections.sort(players[0]);
				Collections.sort(players[1]);

				int start = players[player].remove(0);
				int roll = scan.nextInt();
				int land = start+roll;

				//System.out.println("\n0: " + players[0].toString() + " | 1: " + players[1].toString());
				//System.out.println("[++] Player " + player + " rolls a " + roll + "");
				//System.out.println("[+] Attempting to land on " + land);

				if(players[0].contains(land) || players[1].contains(land) || land > 52 || land == 47) {
					//System.out.println("[-] Occupied or > 52. Next turn");
					players[player].add(start);
					continue;
				}

				/* RULES AMBIGUITY
			if(land==47) {
				players[player].add(land);
				continue;
			}
				 */

				if(land==52) {
					//System.out.println("[-] Lands on 52. Removing");
					continue;
				}

				if(primes.contains(land)) {
					//System.out.println("[+] Prime. Moving forward 6 or until occupied");
					int moves = 6;
					while(!players[0].contains(land+1) && !players[1].contains(land+1) && moves-->0) {
						land++;
					}
					//System.out.println("[-] Stopped at " + land + ". Next turn");
					players[player].add(land);
					continue;
				}

				if(squares.contains(land)) {
					//System.out.println("[+] Square. Moving back 6 or until occupied");
					int moves = 6;
					while(!players[0].contains(land-1) && !players[1].contains(land-1) && moves-->0) {
						land--;
					}
					//System.out.println("[-] Stopped at " + land + ". Next turn");
					players[player].add(land);
					continue;
				}

				// else

				boolean horizontal = false, vertical = false;

				for(int i=start+1; i<=land; i++) {
					if(direction[i] == 0) {
						horizontal = true;
					}
					if(direction[i] == 1) {
						vertical = true;
					}
				}



				if(horizontal && vertical) {
					//System.out.println("[+] HorVer true. Must land on unoccupied multiple of " + roll);
					for(int i=start+1; i<=land; i++) {
						if(i%roll==0) {
							if(!players[0].contains(i) && !players[1].contains(i)) {

								//System.out.println("[-] Unoccupied. Lands at " + i + ". Next turn");

								land = i;
								break;

							}
							else {

								//System.out.println("[-] Occupied. Stays at " + start + ". Next turn");

								land = start;
								break;

							}
						}
					}
				}

				//System.out.println("[-] Final position: " + land);
				players[player].add(land);

			}

			//System.out.println("\n0: " + players[0].toString() + " | 1: " + players[1].toString());

			int sum1 = 0, sum2 = 0;
			for(int i : players[0]) {
				sum1+=i;
			}
			for(int i : players[1]) {
				sum2+=i;
			}

			System.out.println(sum1 + " " + sum2);

		}

		scan.close();
	}

}
