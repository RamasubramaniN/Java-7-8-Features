package com.psg.nramasubramani;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author nramasubramani
 *
 */
public class PredicateExample {

	public static void main(String[] args) {
		
		PredicateExample predicateExample = new PredicateExample();
		predicateExample.testPredicate();
	}
	
	
	/*
	 * Predicate is functional interface. It mean we can pass lambda expressions wherever predicate is expected. 
	 * 1) They move your conditions (sometimes business logic) to a central place. This helps in unit-testing them separately.
	 * 2) Any change need not be duplicated into multiple places. It improves manageability of code.
	 * 3) The code e.g. filterPlayers(playerList, isIndianTeam()) is very much readable than writing a if-else block.
	 * 
	 */
	private void testPredicate(){
		
		PlayerGroup playerGroup = new PlayerGroup();
		List<Player> playerList = playerGroup.getPlayerList();
		
		//Simple Predicate. Following implements test(T t) method in Predicate Functional Interface.
		//Functional interface can be implemented using Lambda directly.
		//Predicate<Player> player1 = player -> player.equals(player);
		
		/*
		*
		@FunctionalInterface
		public interface Predicate<T> {
		    boolean test(T t);
		}
		@FunctionalInterface
		public interface Supplier<T> {
		    T get();
		}
		@FunctionalInterface
		public interface Consumer<T> {
		    void accept(T t);
		}
		*
		*/
		
		PlayerPredicate playerPredicate = new PlayerPredicate();
		
		List<Player> indianPlayerList = filterPlayers(playerList, playerPredicate.isIndianTeam());
		printPlayers("Indian Players", indianPlayerList);
		
		List<Player> above250MatchesIndianTeam = filterPlayers(playerList, playerPredicate.isAbove250MatchesIndianTeam());
		printPlayers("Indian Players above 250 matches", above250MatchesIndianTeam);
		
		List<Player> averageAbove50PlayerList = filterPlayers(playerList, playerPredicate.isAverageAboveFifty());
		printPlayers("Player average above 50", averageAbove50PlayerList);
	}

	public List<Player> filterPlayers (List<Player> players, Predicate<Player> predicate) {
        return players.stream().filter( predicate ).collect(Collectors.<Player>toList());
    }
	
	public void printPlayers(String title, List<Player> playerList){
	
		System.out.println("**********" + title + "**********");
		
		for(Player player : playerList){
			System.out.println(player.getName());
		}
	
		System.out.println("\n");
	}
}

class PlayerPredicate {

	//Predicate is on a single entity. 
	//It is a functional interface whose functional method is test(Object).Predicate returns boolean.
	//Read about BiPredicate.
	public Predicate<Player> isIndianTeam() {//Simple function which returns predicate.
		return player -> {
			return "India".equals(player.getTeam());
		};
	}
	
	//	public Predicate<Player> isIndianTeam = player -> "India".equals(player.getTeam());


	public Predicate<Player> isAverageAboveFifty() {
		return player -> {
			return player.getAverage() >= 50;
		};
	}

	public Predicate<Player> isAbove250MatchesIndianTeam() {
		return player -> {
			return player.getTotalMatches() >= 250
					&& "India".equals(player.getTeam());
		};
	}
}

class Player {

	private final String name;
	private final String team;
	private final Long totalMatches;
	private final Double average;
	private String battingStyle;

	public Player(String name, String team, Long totalMatches, Double average, String battingStyle) {
		this.name = name;
		this.team = team;
		this.totalMatches = totalMatches;
		this.average = average;
		this.battingStyle = battingStyle;
	}

	public String getName() {
		return name;
	}

	public String getTeam() {
		return team;
	}

	public Long getTotalMatches() {
		return totalMatches;
	}

	public Double getAverage() {
		return average;
	}

	public String getBattingStyle() {
		return battingStyle;
	}
	
	@Override
	public String toString() {
		return name + " : " + team;
	}
	
}

class PlayerGroup {
	
	private List<Player> playerList;

	public PlayerGroup() {

		playerList = new ArrayList<Player>();

		playerList.add(new Player("Sachin", "India", 425L, 45.0, "Right"));
		playerList.add(new Player("Ganguly", "India", 240L, 42.0, "Left"));
		playerList.add(new Player("Dravid", "India", 275L, 39.0, "Right"));
		playerList.add(new Player("Brian Lara", "Westindies", 410L, 43.0, "Left"));
		playerList.add(new Player("Viv Richards", "Westindies", 199L, 52.0, "Right"));
		playerList.add(new Player("Beven", "Australia", 230L, 57.0, "Left"));
		playerList.add(new Player("Hussey", "Australia", 180L, 57.0, "Left"));
		playerList.add(new Player("Kallis", "Southafrica", 400L, 44.0, "Right"));

	}

	public List<Player> getPlayerList() {
		return playerList;
	}

}
