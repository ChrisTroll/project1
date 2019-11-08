package project1.models;

public class User {

	private String username;
	private int userrole;
	private int userid;
	
	public User(String username, int userrole, int userid) {
		super();
		this.username = username;
		this.userrole = userrole;
		this.userid = userid;
	}

	public User() {
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUserrole() {
		return userrole;
	}
	public void setUserrole(int userrole) {
		this.userrole = userrole;
	}
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
}
