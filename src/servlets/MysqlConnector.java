package servlets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Customer;
import model.Room;
import model.Transaction;


public class MysqlConnector {

	private static String dbName = "Hotel_Reservation_System";
	private static String dbTable = null;
	private static String dbUser = "root";
	private static String dbPassword = "1234";

	public MysqlConnector(String db) {
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("JDBC driver registered");
			dbName = db;
			//s = new Scanner(System.in);  // Reading from System.in
			//System.out.println("Enter the database username: ");
			//dbUser = s.next();
			//System.out.println("Enter the database password: ");
			//dbPassword = s.next();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public MysqlConnector(Scanner s, String db) {
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("JDBC driver registered");
			dbName = db;
			s = new Scanner(System.in);  // Reading from System.in
			System.out.println("Enter the database username: ");
			dbUser = s.next();
			System.out.println("Enter the database password: ");
			dbPassword = s.next();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public MysqlConnector(Scanner s, String db, String user, String pass) {
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbName = db;
			dbUser = user;
			dbPassword = pass;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String getUser() {
		return dbUser;
	}
	
	public String getPassword() {
		return dbPassword;
	}
	
	public Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?" + 
					"user=" + dbUser + "&password=" + dbPassword + "&useSSL=false");
			//System.out.println("Got Mysql database connection");
			return conn;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}

	public void createDB(Connection conn, String dbname) {
		try {
			PreparedStatement createDB = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS " + dbname);
			createDB.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database creation failed");
			e.printStackTrace();
		}
	}

	public void createCustomersTable(Connection conn) {

		PreparedStatement createTable;
			PreparedStatement createTable1;
			try {
				createTable = conn.prepareStatement("USE Hotel_Reservation_System; ");
				createTable.executeUpdate();
				createTable1 = conn.prepareStatement( 
						"CREATE TABLE IF NOT EXISTS customers ( customer_id INT NOT NULL AUTO_INCREMENT, first_name VARCHAR(25) NOT NULL, last_name VARCHAR(40) NOT NULL, phone_number INT, billing_address VARCHAR(100), billing_city VARCHAR(50), billing_state VARCHAR(2), billing_zip INT,  checkin_date VARCHAR(25),  checkout_date VARCHAR(25), PRIMARY KEY(customer_id))");
				createTable1.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	
	public void createRoomsTable(Connection conn) {
		PreparedStatement createTable2;
		PreparedStatement createTable;

		try {
			createTable = conn.prepareStatement("USE Hotel_Reservation_System; ");
			createTable.executeUpdate();
			createTable2 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS rooms ( room_number INT NOT NULL AUTO_INCREMENT, room_type VARCHAR(100) NOT NULL, price FLOAT NOT NULL, current_occupant INT, FOREIGN KEY (current_occupant) REFERENCES customers(customer_id), PRIMARY KEY(room_number))");
			createTable2.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
}
			
	public void createTransactionsTable(Connection conn) {
		PreparedStatement createTable3;
		PreparedStatement createTable;

		try {
			createTable = conn.prepareStatement("USE Hotel_Reservation_System; ");
			createTable.executeUpdate();
			createTable3 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS transactions ( transaction_id	INT NOT NULL AUTO_INCREMENT, customer_id	INT NOT NULL, room_number	INT NOT NULL, amount	FLOAT NOT NULL, cc_number	INT NOT NULL, expiration_date INT NOT NULL, FOREIGN KEY (customer_id) REFERENCES customers(customer_id), FOREIGN KEY (room_number) REFERENCES rooms (room_number), PRIMARY KEY(transaction_id))");
			createTable3.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
}
	
	public void populateRooms(Connection conn) {
		try {
			PreparedStatement loadSingleRooms = conn.prepareStatement("INSERT INTO "
					+ "Hotel_Reservation_System.rooms" + " (room_type, price, current_occupant) " +
					"VALUES ('Single', 100.00, NULL)");
			for (int i=0;i<40;i++) {
				loadSingleRooms.executeUpdate();
			};
			PreparedStatement loadDoubleRooms = conn.prepareStatement("INSERT INTO "
					+ "Hotel_Reservation_System.rooms" + " (room_type, price, current_occupant) " +
					"VALUES ('Double', 150.00, NULL)");
			for (int i=0;i<50;i++) {
				loadDoubleRooms.executeUpdate();
			};
			PreparedStatement loadPresidentialRooms = conn.prepareStatement("INSERT INTO "
					+ "Hotel_Reservation_System.rooms" + " (room_type, price, current_occupant) " +
					"VALUES ('Presidential', 300.00, NULL)");
			for (int i=0;i<10;i++) {
				loadPresidentialRooms.executeUpdate();
			};
		} catch (SQLException e) {
			System.out.println("Table creation failed");
			e.printStackTrace();
		}
	}

/*
 * ​POST [id] [todo message]” : 
 * Stores the string ‘todo message’ in the database with the supplied integer ‘id’ 
 * and the client’s timestamp. Overwrite any existing values.
 */
	public boolean insertCustomer(Customer customer) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("INSERT INTO " + dbName + ".customers" 
					+ " VALUES(?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, customer.getFirstName());
			stmt.setString(2, customer.getLastName());
			stmt.setInt(3, customer.getNumber());
			stmt.setString(4, customer.getBillingAddress());
			stmt.setString(5, customer.getBillingCity());
			stmt.setString(6, customer.getBillingState());
			stmt.setInt(7, customer.getBillingZip());
			stmt.setString(8, customer.getCheckinDate());
			stmt.setString(9, customer.getCheckoutDate());
			//System.out.println(stmt);
			return stmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return false;
	};

	public Room getToDoMessage(int id) {
		// Get the connection to the database
		Connection conn = getConnection();
		Room message = new Room();
		if (conn != null) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			// Execute the query
			try {
				stmt = conn.prepareStatement("SELECT * FROM " + dbName + "." + dbTable + " WHERE id = ?");
				stmt.setInt(1, id);
				//System.out.println(stmt);
				rs = stmt.executeQuery();
				rs.next();
				//message.setId(rs.getInt("id"));
				//message.setToDoMessage(rs.getString("todo_message"));
				//message.setTimestamp(rs.getTimestamp("timestamp"));
				return message;
			} catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			} finally {
				// it is a good idea to release
				// resources in a finally{} block
				// in reverse-order of their creation
				// if they are no-longer needed

				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlEx) {
					} // ignore

					rs = null;
				}

				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) {
					} // ignore

					stmt = null;
				}
				if(conn != null){
					try {
						conn.close();
					} catch (SQLException sqlEx) {
					} // ignore

					conn = null;
				}

			}
		}
		return message;
	};

	public List<Room> getAllMessages() {
		// Get the connection to the database
		Connection conn = getConnection();
		List<Room> messages = new ArrayList<Room>();

		if (conn != null) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				// Execute the query
				stmt = conn.prepareStatement("SELECT * FROM " + dbName + "." + dbTable);
				rs = stmt.executeQuery();
				while (rs.next()) {
					Room message = new Room();
					//message.setId(rs.getInt("id"));
					//message.setToDoMessage(rs.getString("todo_message"));
					//message.setTimestamp();
					messages.add(message);
				}
				return messages;
			} catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			} finally {
				// it is a good idea to release
				// resources in a finally{} block
				// in reverse-order of their creation
				// if they are no-longer needed

				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqlEx) {
					} // ignore

					rs = null;
				}

				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) {
					} // ignore

					stmt = null;
				}
				if(conn != null){
					try {
						conn.close();
					} catch (SQLException sqlEx) {
					} // ignore

					conn = null;
				}

			}
		}
		return messages;
	}

	public boolean deleteMessage(int id) {
		// Get the connection to the database
		Connection conn = getConnection();
		if (conn != null) {
			PreparedStatement stmt = null;
			// Execute the query
			try {
				stmt = conn.prepareStatement("DELETE FROM " + dbName + "." + dbTable + " WHERE id = ?");
				stmt.setInt(1, id);
				//System.out.println(stmt);
				stmt.executeUpdate();
				return true;
			} catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			} finally {
				// it is a good idea to release
				// resources in a finally{} block
				// in reverse-order of their creation
				// if they are no-longer needed
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) {
					} // ignore
					stmt = null;
				}
				if(conn != null){
					try {
						conn.close();
					} catch (SQLException sqlEx) {
					} // ignore
					conn = null;
				}
			}
		}
		return false;
	}

}
