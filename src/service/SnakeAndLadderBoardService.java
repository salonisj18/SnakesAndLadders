package service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import model.Player;

public class SnakeAndLadderBoardService {
	
	Queue<Player> players = new LinkedList<Player>();
	Map<Integer, Integer> snakes = new HashMap<Integer, Integer>();
	Map<Integer, Integer> ladders = new HashMap<Integer, Integer>();
	Map<String, Integer> playerPosition = new HashMap<String, Integer>();
	int numOfDice;
	boolean isGameWon = false;
	RollDice dice = new RollDice();
	

	public void setBoard(Queue<Player> players, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders, int numOfDice) {
		this.players = players;
		this.snakes = snakes;
		this.ladders = ladders;
		this.numOfDice = numOfDice;
	}


	private void setInitialPositionForPlayers() {
		int numOfPlayers = players.size();
		Player popPlayerFromQueue;
		for(int i=0;i<numOfPlayers;i++) {
			popPlayerFromQueue = players.remove();
			playerPosition.put(popPlayerFromQueue.getId(), 0);
			players.add(popPlayerFromQueue);
		}
	}
	
	public Player startGame() {
		while(!isGameWon) {
			Player currentPlayer = players.poll();
			int initialPosition = playerPosition.get(currentPlayer.getId());
			turnForPlayer(currentPlayer);
			if(playerPosition.get(currentPlayer.getId()) == 100) {
				isGameWon = true;
				return currentPlayer;
			}
			players.add(currentPlayer);
		}
		return null;
	}


	private void turnForPlayer(Player currentPlayer) {
		int currentPosition = playerPosition.get(currentPlayer.getId());
		int newPosition = rollDiceForPlayer(currentPosition);
		System.out.println(currentPlayer.getName() + "moved from " + currentPosition + " to " + newPosition); 
		playerPosition.put(currentPlayer.getId(), newPosition);
	}


	private int rollDiceForPlayer(int currentPosition) {
		int newPosition = currentPosition;
		
		boolean playerHasMoreTurn = true;
		
		int numOfSix = 0;
		
		while(playerHasMoreTurn) {
			
			int numOnDice = dice.rollDice();
			System.out.println("Dice rolled  gave " + numOnDice);
			if(numOnDice == 6) {
				numOfSix++;
				if(numOfSix == 3) {
					playerHasMoreTurn = false;
				}
				else if (numOfSix <3) {
					newPosition = getNextPosition(newPosition, numOnDice);
					playerHasMoreTurn = true;
				}
			}
			else {
				numOfSix = 0;
				newPosition = getNextPosition(newPosition, numOnDice);
				if(cutOtherPlayer(newPosition)) {
					playerHasMoreTurn = true;
				}
				else playerHasMoreTurn = false;
			}
			
		}
		return newPosition;
	}


	private boolean cutOtherPlayer(int currentPlayerPosition) {
		for(Entry<String, Integer> entry: playerPosition.entrySet()) {
			if(entry.getValue() == currentPlayerPosition) {
				entry.setValue(0);
			}
		}
		return false;
	}
	
	private int getNextPosition(int startPosition, int numberOnDice) {
		// TODO Auto-generated method stub
		int newPosition = startPosition + numberOnDice;
		if(newPosition>100)
			return startPosition;
		newPosition = checkSnake(newPosition);
		newPosition = checkLadder(newPosition);
		return newPosition;
	}


	private int checkLadder(int newPosition) {
		int nextPosition = newPosition;
		if(ladders.containsKey(newPosition)) {
			if(ladders.containsKey(newPosition))
				nextPosition = ladders.get(newPosition);
		}
		if(nextPosition != newPosition)
			return nextPosition;
		return newPosition;
	}


	private int checkSnake(int newPosition) {
		// TODO Auto-generated method stub
		int nextPosition = newPosition;
		if(snakes.containsKey(newPosition)) {
			if(snakes.containsKey(newPosition))
				nextPosition = snakes.get(newPosition);
		}
		if(nextPosition != newPosition)
			return nextPosition;
		return newPosition;
	}


	public Player getWinner() {
		setInitialPositionForPlayers();
		return startGame();
	}

}
