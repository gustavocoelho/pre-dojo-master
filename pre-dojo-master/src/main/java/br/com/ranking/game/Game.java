package br.com.ranking.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ranking.classes.GunType;
import br.com.ranking.classes.Match;
import br.com.ranking.classes.Player;

public class Game {
	
	private static final Long INITIAL_MATCH = 11348964L;
	private static String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
	private static final int MATCH_QUANTITY = 3;
	private static final int EVENT_QUANTITY_PER_MATCH = 5;
	
	public static List<Player> players = new ArrayList<Player>();
	public static Map<Long, Integer> totalAwards = new HashMap<Long, Integer>();
	private static Player killer;
	private static Player target;
	
	public Game(){
	}
	
	public static void init(){
		createPlayers();
		startMatch(INITIAL_MATCH, new Date());
	}
	
	public static void main(String args[]){
		init();
	}
	
	private static void startMatch(Long initialMatch, Date date){
		StringBuilder msg = null;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);

	    Match match = new Match(initialMatch, date);
		coreMatch(match, msg, sdf, date);
	}

	private static void initTotalAwards(){
		for (Player player : players) {
			totalAwards.put(player.getId(), Integer.valueOf(0));
		}
	}
	
	private static void initPlayers(){
		for (Player player : players) {
			player.setAwards(0);
			player.setDeaths(0);
			player.setStreaks(0);
		}
	}
	
	private static void coreMatch(Match match, StringBuilder msg, SimpleDateFormat sdf, Date date) {
		initTotalAwards();
		
		for(int i = 0; i < MATCH_QUANTITY; i++){
		    match.setId(Long.valueOf(match.getId().intValue() + 1));
		    initPlayers();
			
			msg = new StringBuilder();
			startMessage(date, msg, sdf, match);

			for(int j = 0; j < EVENT_QUANTITY_PER_MATCH; j++){	
				msg = new StringBuilder();
				
				msg.append("\n");
				msg.append(sdf.format(date));
				msg.append("- ");
		
				Collections.shuffle(players);
				killer = parseToKiller(players.get(0));
				msg.append(killer.getName());
				msg.append(" killed ");
				
				target = new Player();
				target.setId(killer.getId());
				while(target.getId().equals(killer.getId())){
					Collections.shuffle(players);
					target = parseToTarget(players.get(0));
				}
				
				msg.append(target.getName());
				msg.append(" using ");
				msg.append(killer.getGunType().getModel());
				System.out.println(msg.toString());
			}
			closureMessage(date, sdf, match);
			printPlayerStreaks();
			printPlayerAward();
			printRanking();
			sumTotalAwards();
		}
		printTotalAwards();
	}

	private static void printTotalAwards(){
		StringBuilder msg = new StringBuilder("\nTotal Players Awards:");
		for (Map.Entry<Long, Integer> entry : totalAwards.entrySet()){
			for (Player player : players) {
				if(player.getId().equals(entry.getKey()) && !player.getName().equals("<WORLD>"))
					msg.append("\n").append(player.getName()).append(": ").append(entry.getValue().intValue());
			}
		}
		System.out.println(msg.toString());
	}
	
	private static void sumTotalAwards(){
		for (Player player : players) {
			totalAwards.put(player.getId(), totalAwards.get(player.getId()) + player.getAwards());
		}
	}
	
	private static void closureMessage(Date date, SimpleDateFormat sdf, Match match) {
		StringBuilder msg = new StringBuilder("\n");
		msg.append(sdf.format(date));
		msg.append("- Match ");
		msg.append(match.getId());
		msg.append(" has ended");
		System.out.println(msg.toString());
	}

	private static void printRanking() {
		StringBuilder msg = new StringBuilder("\n");
		msg.append("Ranking:");
		
		for (Player player : players) {
			if(!player.getName().equals("<WORLD>"))
				msg.append("\n").append(player.getName()).append(" killed ").append(player.getStreaks()).append(" player(s)");
			
			msg.append("\n").append(player.getName()).append(" died ").append(player.getDeaths()).append(" time(s)");
		}
		System.out.println(msg.toString());
	}

	private static void printPlayerAward() {
		StringBuilder msg = new StringBuilder();
		
		Collections.sort(players);
		int higherScore = players.get(0).getStreaks();

		int i = 0;
		for (Player player : players) {
			if(player.getDeaths() == 0 && player.getStreaks() == higherScore){
				players.get(i).setAwards(players.get(i).getAwards() + 1);
				msg.append("\nWinner(s):\n");
				msg.append(player.getName());
				msg.append(" - Streak(s): ").append(player.getStreaks());
				msg.append(" - Award(s): ").append(player.getAwards());
				msg.append(" - Most Used Gun: ").append(player.getGunType().getModel());
			}
			i ++;
		}		
		System.out.println(msg.toString());
	}	
	
	private static void printPlayerStreaks() {
		StringBuilder msg = new StringBuilder();
		for (Player player : players) {
			if(player.getStreaks() > 0 && player.getDeaths() == 0){
				msg.append("\nThe player ").append(player.getName()).append(" killed ").append(player.getStreaks()).append(" player(s) without dying");
			}
		}
		System.out.println(msg.toString());
	}

	private static void startMessage(Date date, StringBuilder msg, SimpleDateFormat sdf, Match match) {
		msg.append(sdf.format(date));
		msg.append("- New match ");
		msg.append(match.getId());
		msg.append(" has started");
		System.out.println(msg.toString());
		msg.setLength(0);
	}

	private static Player parseToKiller(Player player) {
		killer = new Player();
		killer.setId(player.getId());
		killer.setName(player.getName());
		killer.setGunType(player.getGunType());
		
		int i = 0;
		for (Player playerAux : players) {
			if(playerAux.getId().equals(killer.getId()))
				players.get(i).setStreaks(players.get(i).getStreaks() + 1);
			i++;
		}

		for (int j = 0; j < players.size(); j++) {
			if(players.get(j).getStreaks() >= 5)
				players.get(j).setStreaks(players.get(j).getStreaks() + 1);
		}
		
		return killer;
	}

	private static Player parseToTarget(Player player) {
		target = new Player();
		target.setId(player.getId());
		target.setName(player.getName());
		target.setGunType(player.getGunType());

		int i = 0;
		for (Player playerAux : players) {
			if(playerAux.getId().equals(target.getId()))
				players.get(i).setDeaths(players.get(i).getDeaths() + 1);
			i++;
		}

		return target;
	}

	private static void createPlayers() {
		players.add(createPlayer(1L, "Roman", "M16"));
		players.add(createPlayer(2L, "Nick", "M17"));
		players.add(createPlayer(3L, "Ewan", "M16"));
		players.add(createPlayer(4L, "Connor", "M18"));
		players.add(createPlayer(5L, "Kilo Ren", "M19"));
		players.add(createPlayer(6L, "<WORLD>", "M20"));
		players.add(createPlayer(7L, "Ray", "DROWN"));
		players.add(createPlayer(8L, "Hera", "M20"));
		players.add(createPlayer(9L, "Angeline", "DROWN"));
		players.add(createPlayer(10L, "Maya", "M17"));
		players.add(createPlayer(11L, "Logan", "M18"));
	}

	private static Player createPlayer(Long id, String name, String modelGunType){
		Player player = new Player();
		player.setId(id);
		player.setName(name);
		GunType gunTypeRoman = new GunType();
		gunTypeRoman.setModel(modelGunType);
		player.setGunType(gunTypeRoman);
		return player;		
	}
	
}