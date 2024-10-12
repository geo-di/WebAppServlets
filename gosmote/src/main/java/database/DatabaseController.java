package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.*;

public class DatabaseController {
	
	final String db_string = "";

    final String db_name = "";

    final String db_password = "";
    
    private static final int SALT_LENGTH_BYTES = 16; 
    


	
	public DatabaseController() {
				
	
	}
	
	
	

	

    public static String generateSalt() {
        final SecureRandom secureRandom = new SecureRandom();

        byte[] saltBytes = new byte[SALT_LENGTH_BYTES];
        secureRandom.nextBytes(saltBytes);

        StringBuilder saltString = new StringBuilder(SALT_LENGTH_BYTES * 2);
        for (byte b : saltBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                saltString.append('0');
            }
            saltString.append(hex);
        }
        return saltString.toString();
    }
    

    public static String getHashSHA256(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                String hex = Integer.toHexString(0xff & hashedByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("SHA-256 algorithm not available.", ex); 
        }
    }
    
    public String getSalt(String username) {
    	User user = null;
    	String salt = "";
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM \"user\" WHERE username = ?");
	        stmt.setString(1, username);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	        	salt = rs.getString("salt");
	        }

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return salt;
    }
	

	
	public User searchUser(String username) {
		User user = null;
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM \"user\" WHERE username = ?");
	        stmt.setString(1, username);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	        	user = new User(rs.getString("username"),rs.getString("name"),rs.getString("surname"));
	            user.setType(rs.getString("type"));
	        }

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return user;
	}
	

	public User findUser(String username, String password) {
	    User user = null;
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String salt = getSalt(username);
	    String hash = getHashSHA256(password,salt);

	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM \"user\" WHERE username = ? AND password = ?");
	        stmt.setString(1, username);
	        stmt.setString(2, hash);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	        	user = new User(rs.getString("username"),rs.getString("name"),rs.getString("surname"));
	            user.setType(rs.getString("type"));

	 
	        }

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return user;
	}

	public User registerUser(String username, String password, String name, String surname) {
		User user = null;
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String salt = generateSalt();
	    String hash = getHashSHA256(password,salt);

        try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("INSERT INTO \"user\"	 (username, password, name, surname, type, salt) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, hash);
            stmt.setString(3, name);
            stmt.setString(4, surname);
            stmt.setString(5, "user");
            stmt.setString(6, salt);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                user = searchUser(username);
            }
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }



	public ArrayList<Program> getAllPrograms() {
		ArrayList<Program> programs = new ArrayList<Program>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM program");
	        rs = stmt.executeQuery();
	        while(rs.next()) {
				programs.add(new Program(rs.getInt("id"),rs.getString("name"),rs.getInt("minutes"),rs.getInt("data"),rs.getFloat("price"),rs.getFloat("extraminutescharge"),rs.getFloat("extradatacharge")));
			}
	        

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return programs;
	    
		
	}



	public ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM \"user\" WHERE type = 'user' ");
	        rs = stmt.executeQuery();
	        while(rs.next()) {
				users.add(new User(rs.getString("username"),rs.getString("name"),rs.getString("surname")));
			}
	        

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return users;
	}

	public void updateUser(String username) {
		Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("UPDATE \"user\" SET type ='client' WHERE  username =?");
            stmt.setString(1,username);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User became Client!");
            } else {
                System.out.println("No user found with username: " + username);
            }
	        

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	}

	public ArrayList<Client> getAllClients() {
		ArrayList<Client> clients = new ArrayList<Client>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT c.* FROM client c INNER JOIN \"user\" u ON c.username = u.username WHERE u.type = 'client'");
	        rs = stmt.executeQuery();
	        while(rs.next()) {
	        	User user = searchUser(rs.getString("username"));
	        	PhoneNumber phoneNumber = searchPhone(rs.getString("phonenumber"));
	        	Client client = new Client(user,rs.getString("afm"));
	        	client.setPhoneNumber(phoneNumber);
				clients.add(client);
				
			}
	        

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return clients;
	}
	
	public PhoneNumber registerPhone(String phonenumber) {
		PhoneNumber phoneNumber = null;
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

        try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("INSERT INTO phonenumber(phonenumber) VALUES (?)");
            stmt.setString(1, phonenumber);
            

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
            	System.out.println("Phone registered successfully!");

                stmt = con.prepareStatement("SELECT * FROM phonenumber WHERE phonenumber = ?");
                stmt.setString(1, phonenumber);
                rs = stmt.executeQuery();
                if (rs.next()) {
    	        	phoneNumber = new PhoneNumber(rs.getString("phonenumber"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return phoneNumber;
    }


	public Client registerClient(String username, String phonenumber, String afm) {
		Client client = null;
		Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    PhoneNumber phoneNumber = registerPhone(phonenumber);

        try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("INSERT INTO client (username, afm, phonenumber) VALUES (?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, afm);
            stmt.setString(3, phonenumber);
            

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
            	System.out.println("Client registered successfully!");

                stmt = con.prepareStatement("SELECT * FROM client WHERE username = ?");
                stmt.setString(1, username);
                rs = stmt.executeQuery();
                if (rs.next()) {
					User user = searchUser(rs.getString("username"));				
					client = new Client(user, rs.getString("afm"));
					client.setPhoneNumber(phoneNumber);
	            	System.out.println(client.getUsername());
					updateUser(client.getUsername());
                } 
            }
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return client;
    
	}
	
	public PhoneNumber searchPhone(String phonenumber) {
		PhoneNumber phoneNumber = null;
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM phonenumber WHERE phonenumber = ?");
	        stmt.setString(1, phonenumber);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	        	phoneNumber = new PhoneNumber(rs.getString("phonenumber"));
	        	Program program = searchProgram(rs.getInt("program"));
	            phoneNumber.setProgram(program);
	        }

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return phoneNumber;
	}


	public Program searchProgram(int id) {
		
		Program program = null;
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM program WHERE id = ?");
	        stmt.setInt(1, id);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
				program = new Program(rs.getInt("id"),rs.getString("name"),rs.getInt("minutes"),rs.getInt("data"),rs.getFloat("price"),rs.getFloat("extraminutescharge"),rs.getFloat("extradatacharge"));
	            
	        }

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return program;
	}
	
	
	public Client searchClient(String username) {
		Client client = null;
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM client WHERE username = ?");
	        stmt.setString(1, username);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	        	User user = searchUser(rs.getString("username"));
	        	PhoneNumber phoneNumber = searchPhone(rs.getString("phonenumber"));
	        	client = new Client(user,rs.getString("afm"));
	        	client.setPhoneNumber(phoneNumber);
	        }

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return client;
	}

	public void setProgram(String phonenumber, int programId) {
		
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("UPDATE phonenumber SET program =? WHERE  phonenumber =?");
	        stmt.setInt(1, programId);
	        stmt.setString(2, phonenumber);
	        int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Client's program updated!");
            } else {
                System.out.println("Problem updating program!");
            }

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

		
	}






	public ArrayList<Seller> getAllSellers() {
		ArrayList<Seller> sellers = new ArrayList<Seller>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM \"user\" WHERE type = 'seller' ");
	        rs = stmt.executeQuery();
	        while(rs.next()) {
				sellers.add(new Seller(rs.getString("username"),rs.getString("name"),rs.getString("surname")));
			}
	        

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return sellers;
	}






	public void createProgram(String name, int minutes, int data, float price, float extraMinutesCharge, float extraDataCharge) {
		
		Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

        try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("INSERT INTO program (name, minutes, data, price, extraMinutesCharge, extraDataCharge) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setInt(2, minutes);
            stmt.setInt(3, data);
            stmt.setFloat(4, price);
            stmt.setFloat(5, extraMinutesCharge);
            stmt.setFloat(6, extraDataCharge);
            

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
            	System.out.println("Program Created successfully!");
            } else {
            	System.out.println("Problem Creating Program");
            }
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }		
	}






	public void createSeller(String username, String password, String name, String surname) {
		
		Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    User user = registerUser(username, password, name, surname);

        try {
        	con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("UPDATE \"user\" SET type ='seller' WHERE  username =?");
            stmt.setString(1,user.getUsername());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User became Seller!");
            } else {
                System.out.println("No user found with username: " + username);
            }
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }		
	}






	public void changeProgram(int id, String name, int minutes, int data, float price, float extraMinutesCharge,
			float extraDataCharge) {
		
		Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

        try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("UPDATE program SET name = ?, minutes = ?, data = ?, price = ?, extraMinutesCharge = ?, extraDataCharge = ? WHERE id = ?");
            stmt.setString(1, name);
            stmt.setInt(2, minutes);
            stmt.setInt(3, data);
            stmt.setFloat(4, price);
            stmt.setFloat(5, extraMinutesCharge);
            stmt.setFloat(6, extraDataCharge);
            stmt.setInt(7, id);
            

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
            	System.out.println("Program Changed successfully!");
            } else {
            	System.out.println("Problem Changing Program");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }		
		
	}






	public Client findClient(User user) {
		Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
		Client client = null;
		try {
			con = DriverManager.getConnection(db_string, db_name, db_password );
			stmt = con.prepareStatement("SELECT * FROM client WHERE username = ?");
			stmt.setString(1, user.getUsername());
			rs = stmt.executeQuery();
			while(rs.next())
			{
				client = new Client(user, rs.getString("afm"));
				client.setPhoneNumber(searchPhone(rs.getString("phonenumber")));
			}
			rs.close();
			con.close();
		}
		catch(SQLException e) {
			System.out.println("Database connection problem!");
			e.printStackTrace();		}
		return client;
	}






	public ArrayList<Call> getClientCalls(PhoneNumber phoneNumber) {
		
		ArrayList<Call> calls = new ArrayList<Call>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM call WHERE phonenumber = ? ");
            stmt.setString(1,phoneNumber.getPhoneNumber());
	        rs = stmt.executeQuery();
	        while(rs.next()) {
				calls.add(new Call(rs.getInt("minutes"),phoneNumber,rs.getString("month")));
			}
	        

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return calls;
	}






	public ArrayList<String> getClientBillMonths(Client client) {
		
		ArrayList<String> billMonth = new ArrayList<String>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM bill WHERE phonenumber = ? ");
            stmt.setString(1,client.getPhoneNumber().getPhoneNumber());
	        rs = stmt.executeQuery();
	        while(rs.next()) {
				billMonth.add(new String(rs.getString("month")));
			}
	        

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return billMonth;
	}






	public ArrayList<String> getClientCallMonths(Client client) {
		
		ArrayList<String> callMonth = new ArrayList<String>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM call WHERE phonenumber = ? ");
            stmt.setString(1,client.getPhoneNumber().getPhoneNumber());
	        rs = stmt.executeQuery();
	        while(rs.next()) {
				callMonth.add(new String(rs.getString("month")));
			}
	        

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return callMonth;
	}






	public void calculateBill(PhoneNumber phoneNumber, String billMonth) {
		
		int totalMinutes = 0 ;
		int totalData = 0;
		ArrayList<Integer> calls = new ArrayList<Integer>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM call WHERE phonenumber = ? AND month = ? ");
            stmt.setString(1,phoneNumber.getPhoneNumber());
            stmt.setString(2,billMonth);
	        rs = stmt.executeQuery();
	        while(rs.next()) {
				totalMinutes += rs.getInt("minutes");
				calls.add(rs.getInt("id"));
			}
	        try {
	        	int basicCharge = 0, minutes = 0, data = 0;
	        	float extraMinutesCharge = 0, extraDataCharge = 0;
	        	stmt = con.prepareStatement("select * from program where id = ?");
	        	stmt.setInt(1, phoneNumber.getProgram().getId());
	        	rs = stmt.executeQuery();
		        while(rs.next()) {
					basicCharge = rs.getInt("price");
					minutes = rs.getInt("minutes");
					data = rs.getInt("data");
					extraMinutesCharge = rs.getFloat("extraminutescharge");
					extraDataCharge = rs.getFloat("extradatacharge");
				}
		        int total = basicCharge ;
		        if (totalMinutes > minutes ) {
		        	total += (totalMinutes - minutes) * extraMinutesCharge; 
		        }
		        if (totalData > data ) {
		        	total += (totalData - data) * extraDataCharge; 
		        }
		        try {
		        	stmt = con.prepareStatement("INSERT INTO bill(phonenumber, month, calls, dataused, payed, total) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		        	stmt.setString(1,phoneNumber.getPhoneNumber());
		        	stmt.setString(2,billMonth);
		        	stmt.setInt(3, totalMinutes);
		        	stmt.setInt(4, totalData);
		        	stmt.setBoolean(5, false);
		        	stmt.setInt(6, total);
		        	
		        	int rowsAffected = stmt.executeUpdate();
		            if (rowsAffected > 0) {
		            	try (ResultSet generatedKeys = stmt.getGeneratedKeys()) { 
		                    if (generatedKeys.next()) {
		                        int generatedPk = generatedKeys.getInt(1);
		                        for (int call : calls) {
		                        	stmt = con.prepareStatement("INSERT INTO bill_call(bill_id, call_id) VALUES (?, ?)");
		                        	stmt.setInt(1, generatedPk);
		                        	stmt.setInt(2, call);
		                        	int rowsAffected1 = stmt.executeUpdate();
		        		            if (rowsAffected1 > 0) {
		        		            	System.out.println("Bill_Call added succesfully!");
		        		            }
		        		            
		                        }
		                    } else {
		                        throw new SQLException("Inserting bill failed, no generated key obtained.");
		                    }
		                
		            	}catch (SQLException e){
				        	System.out.println("Problem accessing the database! (4)");
					        e.printStackTrace();
				        }
		            } else {
		            	System.out.println("Problem Creating Bill");
		            }
		        	
		        	
		        	 
		        }catch (SQLException e){
		        	System.out.println("Problem accessing the database! (3)");
			        e.printStackTrace();
		        }
	        }catch (SQLException e){
	        	System.out.println("Problem accessing the database! (2)");
		        e.printStackTrace();
	        }
	        
	        
	        

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database! (1)");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	}






	public ArrayList<Bill> getClientBills(PhoneNumber phoneNumber) {
		
		ArrayList<Bill> bills = new ArrayList<Bill>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
            con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("SELECT * FROM bill WHERE phonenumber = ? ");
            stmt.setString(1,phoneNumber.getPhoneNumber());
	        rs = stmt.executeQuery();
	        while(rs.next()) {
				bills.add(new Bill(rs.getInt("id"), rs.getString("month"),phoneNumber, rs.getInt("calls"),rs.getInt("dataUsed"), rs.getBoolean("payed"), rs.getFloat("total")));
			}
	        

	    } catch (SQLException e) {
	        System.out.println("Problem accessing the database!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return bills;
	}






	public void payBill(int billId) {
		
		Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

        try {
        	con = DriverManager.getConnection(db_string, db_name, db_password );
            stmt = con.prepareStatement("UPDATE bill SET payed ='true' WHERE  id =?");
            stmt.setInt(1,billId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Bill Payed!");
            } else {
                System.out.println("An error occured during payment!");
            }
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }		
	}
	
		
}


