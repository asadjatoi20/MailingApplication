
public interface MailingInterface {
	// the concept of interface is used here. . . 
	// the methods are by default abstract
	public static void adminPanel() {
	}
	public boolean matchAdmin(String user, String pass);
	public static void mailingScreen() {
		
	}
	public static void userPanel() {
		
	}
	public boolean matchPass(String user, String pass);
	
}
