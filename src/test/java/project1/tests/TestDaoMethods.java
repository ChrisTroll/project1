package project1.tests;

import java.util.ArrayList;

import org.junit.Test;

import project1.dao.Dao;
import project1.models.Ticket;
import project1.models.User;

public class TestDaoMethods {
	Dao dao = new Dao();
	User testuser = new User("BananaBatman", "batman", 1, 1);
	User testuser2 = new User("CherryRobin", "robin", 2, 2);
	
//	@Test
//	public void testAcctNumExists() {
//		//this is a test account
//		assert(dao.checkCredentials("BananaBatman", "batman") == 1);
//	}
	
	@Test
	public void testGetTickets() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		tickets = dao.getPastTickets(testuser);
		assert(tickets.size() == 1);
	}
	
	@Test
	public void testGetReviewTickets() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		tickets = dao.getReviewTickets(testuser2);
		assert(tickets.size() == 1);
	}
}
