package applicationDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import model.Player;
import service.SnakeAndLadderBoard;
import service.SnakeAndLadderBoardService;

public class Driver {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.println("Welcome to the game");
		
		Map<Integer, Integer> snakes = new HashMap<Integer,Integer>();
		Map<Integer, Integer> ladders = new HashMap<Integer,Integer>();
		Queue<Player> players = new LinkedList<Player>();
	
		File file = new File("/Users/salonija/eclipse-workspace/Snakes and Ladders/src/applicationDriver/test.txt");
		Scanner sc = new Scanner(file, StandardCharsets.UTF_8.name());
		
		int numOfSnakes = sc.nextInt();
		System.out.println("Number of snakes are " +numOfSnakes);
		for(int i=0;i<numOfSnakes;i++) {
			int start = sc.nextInt();
			int end = sc.nextInt();
			System.out.println("Start and end are " + start + " " + end);
			snakes.put(start, end);
		}
		
		int numOfLadders = sc.nextInt();
		System.out.println("Number of ladders are " +numOfLadders);
		for(int i=0;i<numOfLadders;i++) {
			int start = sc.nextInt();
			int end = sc.nextInt();
			System.out.println("Start and end are " + start + " " + end);
			ladders.put(start, end);
		}
		
		int numOfPlayers = sc.nextInt();
		System.out.println("Number of players");
		for(int i=0; i<numOfPlayers;i++) {
			Player p = new Player(sc.next());
			System.out.println("Player is " + p.getName());
			players.add(p);
		}
		
		int numOfDice = sc.nextInt();
		System.out.println("Number of Dice " + numOfDice);
		sc.close();
		SnakeAndLadderBoardService service = new SnakeAndLadderBoardService();
		service.setBoard(players, snakes, ladders, numOfDice );
		Player winner = service.getWinner();
		System.out.println("Winner is " + winner.getName());
	}

}
