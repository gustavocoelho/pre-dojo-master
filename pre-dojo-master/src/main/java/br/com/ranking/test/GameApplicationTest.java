package br.com.ranking.test;

import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.com.ranking.classes.Player;
import br.com.ranking.game.Game;

public class GameApplicationTest {

    Game game;
    
    @Before
    public void before() throws IOException {
    	game = new Game();
    	Game.init();
    }

    @Test
    public void wrongMeasuredStreaks() {
    	boolean erro = true;
    	for (Player player : Game.players) {
			if(player.getStreaks() >= 5 && player.getAwards() == 0){
				erro = false;
				break;
			}
		}
    	assertFalse("Players that reached 5 streaks must have at least 1 award", erro == false);
    }


}