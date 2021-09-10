import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.*;
public class Mailing implements MailingInterface {
	// implemening the Mailing Interface
	/**
	 * With which all the methods of Interface are inherited -> Inheritace
	 * Some methods of Mailing Iterface, static methods are overriden here -> Concept
	 * of Polymorphism is used
	 * The program is fully Object-Oriented Program uses all the concepts of OOP
	 */
	
	/**
	 * Fields are declared as private to ensure the encapsulation
	 */
	private JPasswordField userPass; // holds the password of the field
	private static JTextField userMail; //holds the mail of the user
	private String adminUser;
	private String adminPass;
	static int userFolderNum = 0; // holds the folder number of the signed in user
	static int numOfMails = 0;
	/**
	 * ArrayLists will store the mails from files
	 */
	static ArrayList<String> list = new ArrayList<String>();
	static ArrayList<String> listDetail = new ArrayList<String>();
	
	/**
	 * Getter Setters are used to achieve the Encapsulation in code
	 * @return
	 */
	public String getUserPass() {
		
		return userPass.getText();
	}
	public static String getUserMail() {
		
		return userMail.getText();
	}
	public String getAdminPass() {
		// reading from the file
		/**
		 * Concept of File Handling used
		 * Buffered File Writer/Reader are more efficient and do posses the append method
		 * which is why Buffered Class is used
		 * Since FileReader/FileWriter throws Exceptions therefore Try Catch statements are used to handle the Exceptions
		 */
				try {
					File f = new File("Admin\\password.txt");
					FileReader fr = new FileReader(f);
					BufferedReader br = new BufferedReader(fr);
					this.adminPass = br.readLine();
				}catch(Exception e) {
					e.printStackTrace(); // if there is any Exception print it.
				}	
		
		return adminPass;
	}
	public String getAdminMail() {
		// reading from the file
				try {
					File f = new File("Admin\\username.txt");
					FileReader fr = new FileReader(f);
					BufferedReader br = new BufferedReader(fr);
					this.adminUser = br.readLine();
				}catch(Exception e) {
					e.printStackTrace();
				}
		return adminUser;
	}
	/**
	 * Methods to set the admin password
	 * @param adminPass
	 */
	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
		// reading from the file
				try {
					File f = new File("Admin\\password.txt");
					FileWriter fw = new FileWriter(f);
					BufferedWriter br = new BufferedWriter(fw);
					br.write(this.adminPass);
					br.close(); //once text is written over on a file it must be closed in order to save alterations
					fw.close();
					
				}catch(Exception e) {
					e.printStackTrace();
				}
	}
	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
		// reading from the file
		try {
			File f = new File("Admin\\username.txt");
			FileWriter fw = new FileWriter(f);
			BufferedWriter br = new BufferedWriter(fw);
			br.write(this.adminUser);
			br.close();
			fw.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Constructor Holds the GUI part along with the actions followed on an Event
	 */
	Mailing(){
		JFrame frame = new JFrame();
		frame.setTitle("Login");
		frame.setSize(750, 500);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		ImageIcon img = new ImageIcon("Wallpaper.JPG"); //holds the background image
		JLabel imageHolder = new JLabel(img);
		frame.setContentPane(imageHolder); // image added in screen
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);   // cant resize the Frame size
		
		/**
		 * Fonts are used to give some styles to the Labels or other components
		 */
		Font font = new Font("Gabriola", Font.BOLD, 22); //for components
		Font fontLabel = new Font("Gabriola", Font.BOLD, 75); //for heading
		Font head = new Font("Gabriola", Font.BOLD, 28); //for JLabels used for medium heading
		// Label used as Heading of the ProjectName
		/**
		 * Labels, Buttons, Fields all necessary components are added
		 */
		JLabel mail = new JLabel("Mailing");
		mail.setFont(fontLabel);
		mail.setForeground(new Color(206, 212, 219));
		mail.setBounds(450,10,400,100);
		frame.add(mail);
		
		//Label used to guide the user
		JLabel enterMail = new JLabel("Enter Mail");
		enterMail.setFont(head);
		enterMail.setForeground(new Color(206, 212, 219));
		enterMail.setBounds(430,100,200,50);
		frame.add(enterMail);
		
		// mail will be entered in this field
		userMail = new JTextField();
		userMail.setBounds(430,150,200,40);
		userMail.setFont(font);
		frame.add(userMail);
		
		
		//Label used to guide the user
		JLabel enterPass = new JLabel("Enter Password");
		enterPass.setFont(head);
		enterPass.setForeground(new Color(206, 212, 219));
		enterPass.setBounds(430,200,200,50);
		frame.add(enterPass);
		
		
		// password will be entered in the field
		userPass = new JPasswordField();
		userPass.setBounds(430,250,200,40);
		userPass.setFont(font);
		frame.add(userPass);
		
		
		

		JButton login = new JButton("Login");
		login.setBounds(495,300,80,40);
		login.setFont(font);
		login.setBackground(new Color(237, 237, 237));
		login.addActionListener(new ActionListener() { // Anonymous action listener is used
			// When button is clicked then check for authentication
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String user = getUserMail();
				String pass = getUserPass();
				// Encapsulation used to obtain the values from fields
				if(matchAdmin(user, pass)) {
					adminPanel();
				}
				else if(matchPass(user,pass)) {
					userPanel();
				}
				else if(!matchPass(user,pass) && !matchAdmin(user,pass)) {
					JOptionPane.showMessageDialog(null, "Credentials do not match.");
				}
			}
			
		});
		frame.add(login);
		
		
		frame.setVisible(true);
		
		
		
	}
	/**
	 * Method is used to match the user entered credentials
	 * @param user
	 * @param pass
	 * @return
	 */
	public boolean matchPass(String user, String pass) {
		
		/**
		 * Checking for the entered user in files
		 */
		try {
			// inside the Users Folder count.txt file holds the current number of users
			int folderNum = 1;
			File f0 = new File("Users\\count.txt");
			FileReader fr0 = new FileReader(f0);
			BufferedReader br0 = new BufferedReader(fr0);
			int count = Integer.parseInt(br0.readLine());
			while(folderNum<=count) {
					// FolderNum holds the folder name which are stored in Project Folder
				// along with the username and passwords
				File f = new File("Users\\"+folderNum+"\\username.txt");
				File f2 = new File("Users\\"+folderNum+"\\password.txt");
				FileReader fr,fr2;
				//FileReader will read the content of text files 
				
				BufferedReader br,br2;
				// Buffered Reader will read the file line by line
				
					// this FileReader gives exceptions therefore try catch are used to
					// handle the exceptions
				fr = new FileReader(f);
				fr2 = new FileReader(f2);
				
				br = new BufferedReader(fr);
				br2 = new BufferedReader(fr2);
				
				String tempUser = ""; // used to store the username from file
				String tempo = ""; // used to store the password from file
				tempUser=br.readLine(); //reads username
				if(tempUser.equalsIgnoreCase(user)) { // if user name matches
					tempo = br2.readLine(); // reads password of same user
					if(tempo.equals(pass)) { // if pass also matches
						// user found
						File f3 = new File("Users\\"+folderNum+"\\mailsNum.txt"); // mailsNum holds the number of new mails
						FileReader fr3 = new FileReader(f3);
						BufferedReader br3 = new BufferedReader(fr3);
						this.userFolderNum = folderNum;
						// correct
						String line = br3.readLine()+"";
						this.numOfMails = Integer.parseInt(line);
						//JOptionPane.showMessageDialog(null, "numOfMails : "+numOfMails);
						return true;
					}
				}
				folderNum++;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;  // credentials donot match 
	}
	public static void userPanel() {
		JFrame user = new JFrame("User Panel");
		// send mail
		// view mail
		user.setExtendedState(JFrame.MAXIMIZED_BOTH);
		user.setLayout(null);
		ImageIcon img = new ImageIcon("User.JPG");
		JLabel imageHolder = new JLabel(img);
		user.setContentPane(imageHolder);
		
		
		user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		user.setLocationRelativeTo(null);
		
		
		Font font = new Font("Gabriola", Font.BOLD, 24);
		
		
		JButton viewMails = new JButton("View Mails ("+numOfMails+")");
		// numOfMails = number of mails
		viewMails.setFont(font);
		viewMails.setBackground(new Color(148, 191, 224));
		viewMails.setBounds(600,160,200,40);
		viewMails.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// view All the mails 
				// get All the mail's Subject
				int countMails = 1;
				/**
				 * List will hold the subject of all mails
				 * and listDetail will hold the whole mail
				 */
				list = new ArrayList<String>();
				listDetail = new ArrayList<String>();
				list.add("~Subject~");
				listDetail.add("");
				try {
					while(countMails<=numOfMails) {
						// until All Mails Subject is not collected
						File f = new File("Users\\"+userFolderNum+"\\mail"+countMails+".txt");
						FileReader fr = new FileReader(f);
						BufferedReader br = new BufferedReader(fr);
						String sub = br.readLine();
						list.add(sub); // subject line obtained
						String lines;
						String msg = sub+"\n";
						while((lines=br.readLine())!=null) {
							msg = msg+lines+"\n";
						}
						listDetail.add(msg); // whole mail entered in the list
						countMails++;
					}
					
					
				}
				catch(Exception ee) {
					ee.printStackTrace();
				}
				JFrame modelUser = new JFrame();
				modelUser.setTitle("Login");
				modelUser.setSize(800, 500);
				modelUser.setLocationRelativeTo(null);
				modelUser.setLayout(null);
				modelUser.setBackground(Color.LIGHT_GRAY);
				modelUser.setForeground(Color.LIGHT_GRAY);
				modelUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				modelUser.setResizable(false);   // cant resize the Frame size
				
				/**
				 * Fonts are used to give some styles to the Labels or other components
				 */
				Font font = new Font("Gabriola", Font.BOLD, 22); //for components
				
				JTextArea area = new JTextArea();

				area.setFont(font);
				JScrollPane scrollPane = new JScrollPane(area); // area along with scroll pane is initialized
				
				//Scrolpane will provide the area ScrolBars
				scrollPane.setBounds(320,20,400,350);
				// setText in between
				
				JComboBox allMails = new JComboBox(list.toArray()); // list converted into arrays and passed
				allMails.setFont(font);
				allMails.setBounds(20,20,280,40);
				allMails.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int selected = allMails.getSelectedIndex();
						area.setText(listDetail.get(selected)+"");
						
					}
					
					
				});
				
				JButton back = new JButton("Back");
				back.setBounds(120,310,100,40);
				back.setFont(font);
				back.setBackground(new Color(117, 201, 196));
				back.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						modelUser.setVisible(false);
						userPanel();
						
					}
					
				});
				modelUser.add(back);
				
				
				modelUser.add(allMails);
				
				modelUser.add(scrollPane); // scroll panel has the area therefore adding scrollpane in frame
				
				
				
				modelUser.setVisible(true);
				
				
			}
			
		});
		user.add(viewMails);
		
		
		JButton sendMail = new JButton("Send Mail");
		sendMail.setFont(font);
		sendMail.setBackground(new Color(148, 191, 224));
		sendMail.setBounds(600,260,200,40);
		sendMail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mailingScreen();
				
			}
			
		});
		user.add(sendMail);
		
		JButton delMail = new JButton("Delete Mail");
		delMail.setFont(font);
		delMail.setBackground(new Color(148, 191, 224));
		delMail.setBounds(600,360,200,40);
		delMail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { // Implementing the required method
				/////////////////////////////////////////////////////

				JFrame modelUser = new JFrame();
				modelUser.setTitle("Delete Mail");
				modelUser.setSize(800, 500);
				modelUser.setLocationRelativeTo(null);
				modelUser.setLayout(null);
				modelUser.setBackground(Color.LIGHT_GRAY);
				modelUser.setForeground(Color.LIGHT_GRAY);
				modelUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				modelUser.setResizable(false);   // cant resize the Frame size
				
				/**
				 * Fonts are used to give some styles to the Labels or other components
				 */
				Font font = new Font("Gabriola", Font.BOLD, 22); //for components
				
				list = new ArrayList<String>();
				listDetail = new ArrayList<String>();
				list.add("~Subject~");
				listDetail.add("");
				int countMails = 1;
				try {
					while(countMails<=numOfMails) {
						// until All Mails Subject is not collected
						File f = new File("Users\\"+userFolderNum+"\\mail"+countMails+".txt");
						FileReader fr = new FileReader(f);
						BufferedReader br = new BufferedReader(fr);
						String sub = br.readLine();
						list.add(sub); // subject line obtained
						String lines;
						String msg = sub+"\n";
						while((lines=br.readLine())!=null) {
							msg = msg+lines+"\n";
						}
						listDetail.add(msg); // whole mail entered in the list
						countMails++;
					}
					
					
				}
				catch(Exception ee) {
					ee.printStackTrace();
				}
				
				JComboBox allMails = new JComboBox(list.toArray()); // list converted into arrays and passed
				allMails.setFont(font);
				allMails.setBounds(20,20,280,40);
				allMails.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int selected = allMails.getSelectedIndex()-1; // since the first inserted list member is Subject Text
						int value = JOptionPane.showConfirmDialog(null, "You Sure");
						JOptionPane.showMessageDialog(null,value);
						if(value==0) {
							/**
							 * Delete the folder with that index
							 * 
							 */
							int countMails = 0;
							try {
								int extraIndex = 0;
								while(countMails<=numOfMails) {
									// until All Mails Subject is not collected
									if(countMails==selected) {
										extraIndex = countMails+1; //matching the index
										File f = new File("Users\\"+userFolderNum+"\\mail"+extraIndex+".txt");
										//JOptionPane.showMessageDialog(null, "Selected: "+selected+"\nCountMail: "+countMails);
										// delete that mailfile
											
										FileWriter fw3 = new FileWriter(f);
										BufferedWriter bw3 = new BufferedWriter(fw3);
										bw3.write("");
										bw3.append("mailDeleted\nmailDeleted");
										bw3.close();
											
										
										JOptionPane.showMessageDialog(null, "Mail Deleted");
											
										/*
											File f2 = new File("Users\\"+userFolderNum+"\\mailsNum.txt");
											FileReader fr = new FileReader(f2);
											BufferedReader br = new BufferedReader(fr);
											int current = Integer.parseInt(br.readLine());
											current--; // 1 mail deleted
											FileWriter fw = new FileWriter(f2);
											BufferedWriter bw = new BufferedWriter(fw);
											bw.write("");
											bw.append(current+"");
											bw.close();
											//fw.close();
											*/
											modelUser.setVisible(false);
											userPanel();
										
					
									}
									countMails++;
								}
								
								
							}
							catch(Exception ee) {
								ee.printStackTrace();
							}
							
							
						}
						else {
							modelUser.setVisible(false);
							userPanel();
						}
					}
					
					
				});
				modelUser.add(allMails);

				modelUser.setVisible(true);
			}
			
			
		});
		user.add(delMail);
		
		JButton signOut = new JButton("Sign Out");
		signOut.setFont(font);
		signOut.setBackground(new Color(148, 191, 224));
		signOut.setBounds(600,460,200,40);
		user.add(signOut);
		
		
		
		user.setVisible(true);
		signOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				user.setVisible(false);
				new Mailing(); 

				user.setVisible(false);
				// back to login screen
				
			}
			
		});
		
		
	}
	public static void mailingScreen() {
		
		/**
		 * 
		 * Frame for users
		 * User Interface
		 * Below are the attributes of user Interface annd the components it's operating upon
		 */
		JFrame mailFrame = new JFrame("Send Mail");
		mailFrame.setSize(800,500);
		mailFrame.setLayout(null);
		
		Font font = new Font("Gabriola", Font.BOLD, 25);
		
		mailFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mailFrame.setLocationRelativeTo(null);
		mailFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mailFrame.setResizable(false);   // cant resize the Frame size
		
		JLabel sendTo = new JLabel("Send To");
		sendTo.setBounds(50,30,120,40);
		sendTo.setFont(font);
		mailFrame.add(sendTo);
		
		JTextField receiver = new JTextField();
		receiver.setBounds(180,30,300,40);
		receiver.setFont(font);
		mailFrame.add(receiver);
		

		JLabel subjectHead = new JLabel("Subject");
		subjectHead.setBounds(50,80,120,40);
		subjectHead.setFont(font);
		mailFrame.add(subjectHead);
		
		JTextField subject = new JTextField();
		subject.setBounds(180,80,300,40);
		subject.setFont(font);
		mailFrame.add(subject);
		
		JLabel composeHead = new JLabel("Compose");
		composeHead.setBounds(50,130,120,40);
		composeHead.setFont(font);
		mailFrame.add(composeHead);
		

		JTextArea compose = new JTextArea();
		compose.setFont(font);
		JScrollPane scrollPane = new JScrollPane(compose);
		//Scrolpane will provide the area ScrolBars
		scrollPane.setBounds(180,130,600,270);
		mailFrame.add(scrollPane);
		
		

		JButton send = new JButton("Send");
		send.setBounds(50,410,80,40);
		send.setFont(font);
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String message = "";
				message = message+"Subject: "+subject.getText()+"\n";
				message = message+""+compose.getText()+"\n";
				message = message+"From: "+getUserMail()+".";
				//JOptionPane.showMessageDialog(null, "Message: "+message);
				// let's find the user from mails
				String sendToMail = receiver.getText();
				try {	
					int count = 1; // name of folder
					int totalUsers =0; // this will find total number of users
					File totalLoc = new File("Users\\count.txt");
					FileReader reader = new FileReader(totalLoc);
					BufferedReader readerB = new BufferedReader(reader);
					totalUsers = Integer.parseInt(readerB.readLine());
					String tempUsername = "";
					boolean mailSent = false;
					while(count<=totalUsers) {
						File f = new File("Users\\"+count+"\\username.txt");
						FileReader frd = new FileReader(f);
						BufferedReader brd = new BufferedReader(frd);
						tempUsername = brd.readLine();
						if(sendToMail.equals(tempUsername)) {
							//////////////// Send Mail
							// getting number of mails user have already
							
							int currentMails = 0;
							File alreadyMails = new File("Users\\"+count+"\\mailsNum.txt");
							FileReader frrr = new FileReader(alreadyMails);
							BufferedReader brrr = new BufferedReader(frrr);
							currentMails = Integer.parseInt(brrr.readLine());
							
							// new mail therefore incrementing current mail number
							currentMails++;
							
							
							
							File mailer = new File("Users\\"+count+"\\mail"+currentMails+".txt"); // new mail this will contain message in it
							if(mailer.createNewFile()) {
								FileWriter fwr = new FileWriter(mailer);
								FileWriter fwr2 = new FileWriter(alreadyMails);
								BufferedWriter bwr = new BufferedWriter(fwr);
								BufferedWriter bwr2 = new BufferedWriter(fwr2);
								/////////////////////////////////////////////////////////////
								// change number of already mails
								bwr.write("");
								bwr.append(message); // message added to the file
								bwr.close();
								fwr.close();
								// and also add new mail to the file
								bwr2.write("");
								bwr2.append(currentMails+"");
								bwr2.close();
								fwr2.close();
								
								
								mailSent = true;
								
							}
							
							break;
						}
						count++;
					}
				
					if(mailSent) {
						JOptionPane.showMessageDialog(null, "Mail sent to: "+sendToMail);					
					}
					else {
						JOptionPane.showMessageDialog(null, "Mail could not be send to: "+sendToMail);					
						
					}
				}catch(Exception exp) {
					exp.printStackTrace();
				}
				
				
			}
			
		});
		mailFrame.add(send);
		
		JButton back = new JButton("Back");
		back.setBounds(200,410,80,40);
		back.setFont(font);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mailFrame.setVisible(false);
				userPanel();
			}
			
		});
		mailFrame.add(back);
		
		
		
		
		
		
		
		
		
		mailFrame.setVisible(true);
		
		
	}
	public boolean matchAdmin(String user, String pass) {
		this.adminUser = this.getAdminMail();
		this.adminPass = this.getAdminPass();
		if(this.adminUser.equalsIgnoreCase(user) && this.adminPass.equals(pass)) {
			return true;
		}
		return false;
	}
	public static  void adminPanel() {
			/**
			 * Frame and it's decoration for Admin
			 */
			JFrame admin = new JFrame("Admin Panel");
			admin.setSize(1024,1024);
			admin.setLayout(null);
			ImageIcon img = new ImageIcon("Admin.JPG");
			JLabel imageHolder = new JLabel(img);
			admin.setContentPane(imageHolder);
			
			Font font = new Font("Gabriola", Font.BOLD, 34);
			
			admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			admin.setLocationRelativeTo(null);
			
			// Heading
			JLabel welcome = new JLabel("Welcome, Admin");
			welcome.setBounds(500,70,400,40);
			welcome.setFont(new Font("Gabriola", Font.BOLD, 54));
			welcome.setForeground(new Color(117, 201, 196));
			admin.add(welcome);
			// Buttons
			JButton register = new JButton("Register User");
			register.setBounds(450,300,220,40);
			register.setBackground(new Color(117, 201, 196));
			register.setFont(font);
			register.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String name = JOptionPane.showInputDialog("Enter Username:");
					String pass = JOptionPane.showInputDialog("Enter Password:");
					try {
						
						
						int totalUsers =0; // this will find total number of users
						File totalLoc = new File("Users\\count.txt");
						FileReader reader = new FileReader(totalLoc);
						BufferedReader readerB = new BufferedReader(reader);
						totalUsers = Integer.parseInt(readerB.readLine());
						totalUsers++; // new user Creations
						
						
						File f = new File("Users\\"+totalUsers+"");
						if(f.mkdir()) {
							FileWriter fw = new FileWriter(totalLoc);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write("");
							bw.append(totalUsers+"");
							bw.close();
							fw.close();
							// number of users changed
							File fi = new File("Users\\"+totalUsers+"\\username.txt");
							if(fi.createNewFile()) {
								// saving username
								FileWriter fw0 = new FileWriter(fi);
								BufferedWriter bw0 = new BufferedWriter(fw0);
								bw0.write("");
								bw0.append(name); // username saved
								bw0.close();
								fw0.close();
								
								fi = new File("Users\\"+totalUsers+"\\password.txt");
								if(fi.createNewFile()) {
									// saving password
									FileWriter fw00 = new FileWriter(fi);
									BufferedWriter bw00 = new BufferedWriter(fw00);
									bw00.write("");
									bw00.append(pass); // password saved
									bw00.close();
									fw00.close();
								}
								fi = new File("Users\\"+totalUsers+"\\mailsNum.txt");
								if(fi.createNewFile()) {
									
									BufferedWriter bwr0 = new BufferedWriter(new FileWriter(fi));
									bwr0.write("");
									bwr0.append("0"); // at start new user won't have any mails
									bwr0.close();
									
								}
								
								JOptionPane.showMessageDialog(null, "User:"+name+" Registered");
							}
							
							
							
						}
						
						
						
					}catch(Exception exc) {
						exc.printStackTrace();
					}
				}
				
				
			});
			admin.add(register);
			
			
			JButton delete = new JButton("Delete User");
			delete.setBounds(450,400,220,40);
			delete.setBackground(new Color(117, 201, 196));
			delete.setFont(font);
			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					String name = JOptionPane.showInputDialog("Enter Username:");
					/////////////////////////////////////////////
					/**
					 * Delete a user from program i-e make him null invalidUser
					 */
					try {
						int totalUsers =0; // this will find total number of users
						File totalLoc = new File("Users\\count.txt");
						FileReader reader = new FileReader(totalLoc);
						BufferedReader readerB = new BufferedReader(reader);
						totalUsers = Integer.parseInt(readerB.readLine());
						
						int count = 1; //starting from 1
						
						// number of users getting
						boolean isDeleted = false;
						String tempUser = "";
						while(count<=totalUsers) {
							File f = new File("Users\\"+count+"\\username.txt");
							BufferedReader br02 = new BufferedReader(new FileReader(f));
							tempUser = br02.readLine();
							if(tempUser.equalsIgnoreCase(name)) {
								BufferedWriter brw = new BufferedWriter(new FileWriter(f));
								brw.write("");
								brw.append("deletedUser");
								brw.close();
								
								f = new File("Users\\"+count+"\\password.txt");
								BufferedWriter brw22 = new BufferedWriter(new FileWriter(f));
								brw22.write("");
								brw22.append("deletedUser");
								brw22.close();
								isDeleted = true;
								JOptionPane.showMessageDialog(null, "User Deleted");
								break;
							}
							
							count++;
						}
						if(!isDeleted) {
							JOptionPane.showMessageDialog(null, name+" could not be deleted");
						}
					}catch(Exception expp ) {
						expp.printStackTrace();
					}	
				}
				
			});
			admin.add(delete);
			
			
			JButton update = new JButton("Update User");
			update.setBounds(450,500,220,40);
			update.setBackground(new Color(117, 201, 196));
			update.setFont(font);
			update.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
				
					
					
					String name = JOptionPane.showInputDialog("Enter Username:");
					/////////////////////////////////////////////
					/**
					 * Update a user 
					 * Similiar Logic as Delete is used
					 * But here username  is altered instead of deleting
					 */
					try {
						int totalUsers =0; // this will find total number of users
						File totalLoc = new File("Users\\count.txt");
						FileReader reader = new FileReader(totalLoc);
						BufferedReader readerB = new BufferedReader(reader);
						totalUsers = Integer.parseInt(readerB.readLine());
						
						int count = 1; //starting from 1
						
						// number of users getting
						boolean isUpdated = false;
						String tempUser = "";
						while(count<=totalUsers) {
							File f = new File("Users\\"+count+"\\username.txt");
							BufferedReader br02 = new BufferedReader(new FileReader(f));
							tempUser = br02.readLine();
							if(tempUser.equalsIgnoreCase(name)) {
								BufferedWriter brw = new BufferedWriter(new FileWriter(f));
								String name2 = JOptionPane.showInputDialog("Enter new username: ");
								brw.write("");
								brw.append(name2+"");
								brw.close();
								
								f = new File("Users\\"+count+"\\password.txt");
								BufferedWriter brw22 = new BufferedWriter(new FileWriter(f));
								String pass2 = JOptionPane.showInputDialog("Enter new password: ");
								brw22.write("");
								brw22.append(pass2+"");
								brw22.close();
								isUpdated = true;
								JOptionPane.showMessageDialog(null, "User Updated");
								break;
							}
							
							count++;
						}
						if(!isUpdated) {
							JOptionPane.showMessageDialog(null, name+" could not be updated");
						}
					}catch(Exception expp ) {
						expp.printStackTrace();
					}	
					
					
					
					
					
					
					
					
					
					
					
					
				}
				
				
			});
			admin.add(update);
			

			JButton signout  = new JButton("Sign Out");
			signout.setBounds(450,600,220,40);
			signout.setBackground(new Color(117, 201, 196));
			signout.setFont(font);
			signout.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					admin.setVisible(false); // make current frame not visible
					new Mailing(); // and start the constructor again
					
				}
				
			});
			admin.add(signout);
			
			
			
			
			admin.setVisible(true);
			
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Mailing();
	
	}

}
