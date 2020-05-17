package service;

import java.util.Random;

public class RollDice {

	public int rollDice() {
		Random ran = new Random();
		return ran.nextInt(6)+1;
	}
}
