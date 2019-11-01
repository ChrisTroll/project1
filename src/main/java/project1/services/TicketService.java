package project1.services;

import java.util.ArrayList;

import project1.dao.Dao;
import project1.models.Ticket;
import project1.models.TicketUpdateRequest;
import project1.models.User;

//TODO add validation logic to validateTicket
public class TicketService {
	Dao dao = new Dao();
	
	public Ticket submit(Ticket ticket) {
		ticket = dao.pushTicket(ticket);
		return ticket;
	}
	
	public ArrayList<Ticket> viewPastTickets(User user){
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		tickets = dao.getPastTickets(user);
		return tickets;
	}
	
	public ArrayList<Ticket> getReviewTickets(User user){
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		tickets = dao.getReviewTickets(user);
		return tickets;
	}
	
	public void closeTicket(TicketUpdateRequest tur) {
		dao.closeTicket(tur);
	}
	
	public Ticket getTicket(int ticketid) {
		return dao.pullTicket(ticketid);
	}
	
}
