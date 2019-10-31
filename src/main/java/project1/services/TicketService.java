package project1.services;

import project1.dao.UserDao;
import project1.models.Ticket;

//TODO add validation logic to validateTicket
public class TicketService {
	UserDao dao = new UserDao();
	
	public Ticket submit(Ticket ticket) {
		validateTicket(ticket);
		ticket = dao.pushTicket(ticket);
		return ticket;
	}
	
	private Ticket validateTicket(Ticket ticket) {
		return ticket;
	}
}
