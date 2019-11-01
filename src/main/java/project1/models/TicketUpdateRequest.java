package project1.models;

public class TicketUpdateRequest {
	private int ticketid;
	private int userid;
	private int ticketstatus;
	
	public TicketUpdateRequest() {
		super();
	}
	public int getTicketid() {
		return ticketid;
	}
	public void setTicketid(int ticketid) {
		this.ticketid = ticketid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getTicketstatus() {
		return ticketstatus;
	}
	public void setTicketstatus(int ticketstatus) {
		this.ticketstatus = ticketstatus;
	}

	
}
