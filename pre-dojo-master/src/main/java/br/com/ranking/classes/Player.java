package br.com.ranking.classes;

public class Player implements Comparable<Player>{

	private Long id;
	private String name;
	private int streaks;
	private int awards;
	private GunType gunType;
	private int deaths;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStreaks() {
		return streaks;
	}

	public void setStreaks(int streaks) {
		this.streaks = streaks;
	}

	public int getAwards() {
		return awards;
	}

	public void setAwards(int awards) {
		this.awards = awards;
	}

	public GunType getGunType() {
		return gunType;
	}

	public void setGunType(GunType gunType) {
		this.gunType = gunType;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public int compareTo(Player player) {
        if (this.streaks < player.streaks) {
            return 1;
        }
        if (this.streaks > player.streaks) {
            return -1;
        }
        return 0;
    }
	
}