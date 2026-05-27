package in.co.rays.proj4.bean;

public class GamingBean extends BaseBean{

	private long tournamentId;
	private String tournamentCode;
	private String gameName;
	private Double prizePool;
	private String status;
	public long getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(long tournamentId) {
		this.tournamentId = tournamentId;
	}
	public String getTournamentCode() {
		return tournamentCode;
	}
	public void setTournamentCode(String tournamentCode) {
		this.tournamentCode = tournamentCode;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public Double getPrizePool() {
		return prizePool;
	}
	public void setPrizePool(Double prizePool) {
		this.prizePool = prizePool;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return tournamentCode + " " + gameName;
	}
	
}
