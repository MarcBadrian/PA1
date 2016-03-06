/*
 * COMP 6302 - Web Services / Internet
 * PA1: Hotel Reservation System
 * Marc Badrian and Hien Vo - Due 3/5/16
 * 
 */

package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import model.Customer;



public class MysqlConnector {

	private static String dbName = "Hotel_Reservation_System";
	private static String dbUser = "root";
	private static String dbPassword = "1234";
    int new_customer_id = 0;

	public MysqlConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("JDBC driver registered");
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
						"CREATE TABLE IF NOT EXISTS customers ( customer_id INT NOT NULL AUTO_INCREMENT, first_name VARCHAR(25) NOT NULL, last_name VARCHAR(40) NOT NULL, phone_number INT, billing_address VARCHAR(100), billing_city VARCHAR(50), billing_state VARCHAR(20), billing_zip INT,  checkin_date VARCHAR(25),  checkout_date VARCHAR(25), PRIMARY KEY(customer_id))");
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
			createTable3 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS transactions ( transaction_id	INT NOT NULL AUTO_INCREMENT, customer_id	INT NOT NULL, room_number	INT NOT NULL, amount	FLOAT NOT NULL, cc_number	INT(20) NOT NULL, expiration_date INT NOT NULL, FOREIGN KEY (customer_id) REFERENCES customers(customer_id), FOREIGN KEY (room_number) REFERENCES rooms (room_number), PRIMARY KEY(transaction_id))");
			createTable3.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
}
	
	public void populateRooms(Connection conn) {
		ResultSet rs = null;
		try {
			PreparedStatement checkCount = conn.prepareStatement("SELECT COUNT(*) AS count FROM Hotel_Reservation_System.rooms");
			rs = checkCount.executeQuery();
			rs.next();
			int count = rs.getInt("count");
			if (count == 0) {
				
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
			}
		} catch (SQLException e) {
			System.out.println("Table creation failed");
			e.printStackTrace();
		}
	}


	public boolean insertCustomer(Customer customer) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("INSERT INTO Hotel_Reservation_System.customers (first_name, last_name, phone_number, billing_address, billing_city, billing_state, billing_zip, checkin_date, checkout_date) VALUES (?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, customer.getFirstName());
			stmt.setString(2, customer.getLastName());
			stmt.setInt(3, customer.getNumber());
			stmt.setString(4, customer.getBillingAddress());
			stmt.setString(5, customer.getBillingCity());
			stmt.setString(6, customer.getBillingState());
			stmt.setInt(7, customer.getBillingZip());
			stmt.setString(8, customer.getCheckinDate());
			stmt.setString(9, customer.getCheckoutDate());
			System.out.println(stmt);
			boolean success = stmt.executeUpdate() > 0;
			return success;
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

	
	public int getCustomerId(String first_name, String last_name) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT customer_id FROM Hotel_Reservation_System.customers WHERE first_name = ? AND last_name = ?");
			stmt.setString(1, first_name);
			stmt.setString(2, last_name);
			rs = stmt.executeQuery();
			rs.next();
			int customer_id = rs.getInt("customer_id");
			return customer_id;
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
		return 0;
	}
	
	
	public boolean reserveRoom(int id, int room_number) {
		// Get the connection to the database
		Connection conn = getConnection();
		PreparedStatement stmt = null;
			// Execute the query
			try {
				stmt = conn.prepareStatement("UPDATE Hotel_Reservation_System.rooms SET current_occupant = ? WHERE room_number = ?");
				stmt.setInt(1, id);
				stmt.setInt(2, room_number);
				stmt.executeUpdate();
				boolean reserved = stmt.executeUpdate() > 0;
				return reserved;
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
		return false;
	};
	
	public boolean createPayment(int customer_id, int room_number, float amount, int cc_number, int exp_date) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("INSERT INTO Hotel_Reservation_System.transactions (customer_id, room_number, amount, cc_number, expiration_date) VALUES (?,?,?,?,?)");
			stmt.setInt(1, customer_id);
			stmt.setInt(2, room_number);
			stmt.setFloat(3, amount);
			stmt.setInt(4, cc_number);
			stmt.setInt(5, exp_date);
			System.out.println(stmt);
			boolean success = stmt.executeUpdate() > 0;
			return success;
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

	
	public int getTransactionId(int customer_id, int room_number) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT transaction_id FROM Hotel_Reservation_System.transactions WHERE customer_id = ? AND room_number = ? ORDER BY transaction_id DESC");
			stmt.setInt(1, customer_id);
			stmt.setInt(2, room_number);
			rs = stmt.executeQuery();
			rs.next();
			int transaction_id = rs.getInt("transaction_id");
			return transaction_id;
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
		return 0;
	}

	public String getCustomer(int id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT * FROM Hotel_Reservation_System.customers WHERE customer_id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			rs.next();
			String customer_id = Integer.toString(rs.getInt("customer_id"));
			String first_name = rs.getString("first_name");
			String last_name = rs.getString("last_name");
			String phone_number = Integer.toString(rs.getInt("phone_number"));
			String billing_address = rs.getString("billing_address");
			String billing_city = rs.getString("billing_city");
			String billing_state = rs.getString("billing_state");
			String billing_zip = Integer.toString(rs.getInt("billing_zip"));
			String checkin_date = rs.getString("checkin_date");
			String checkout_date = rs.getString("checkout_date");
			String custInfo = "Customer Information: " + "\n" + "\n" +
					"Id: " + customer_id + "\n" +
					"Name: " + first_name + " " + last_name + "\n" +
					"Phone Number: " + phone_number + "\n" +
					"Billing Address: " + billing_address + "\n" + "\t" + "\t" + "\t" + billing_city + ", " + billing_state + "  " + billing_zip + "\n" +
					"Check-In Date: " + checkin_date + "\n" +
					"Check-Out Date: " + checkout_date + "\n";
			return custInfo;
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
		return "Error";
	}
	
	public String getCustomersByName(String f_name, String l_name) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;	
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT * FROM Hotel_Reservation_System.customers WHERE first_name = ? OR last_name = ?");
			stmt.setString(1, f_name);
			stmt.setString(2, l_name);
			rs = stmt.executeQuery();
			String allMatches = "Last Name:		First Name:		Id:		Phone Number: " + "\n" + "\n";
			while (rs.next()) {
			String customer_id = Integer.toString(rs.getInt("customer_id"));
			String first_name = rs.getString("first_name");
			String last_name = rs.getString("last_name");
			String phone_number = Integer.toString(rs.getInt("phone_number"));
			String custInfo = last_name + "\t" + "\t" + "\t" + first_name + "\t" + "\t" + "\t" + customer_id + "\t" + "\t" + phone_number + "\n";
			allMatches += custInfo;
			}
			return allMatches;
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
		return "Error";
	}
	
	public String getCustomersCurrent() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;	
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT * FROM Hotel_Reservation_System.rooms JOIN Hotel_Reservation_System.customers ON rooms.current_occupant = customers.customer_id");
			rs = stmt.executeQuery();
			String allMatches = "Last Name:		First Name:		Id:		Phone Number: " + "\n" + "\n";
			while (rs.next()) {
			String customer_id = Integer.toString(rs.getInt("customer_id"));
			String first_name = rs.getString("first_name");
			String last_name = rs.getString("last_name");
			String phone_number = Integer.toString(rs.getInt("phone_number"));
			String custInfo = last_name + "\t" + "\t" + "\t" + first_name + "\t" + "\t" + "\t" + customer_id + "\t" + "\t" + phone_number + "\n";
			allMatches += custInfo;
			}
			return allMatches;
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
		return "Error";
	}
	
	
	public String getTransactions(int customer_id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT * FROM Hotel_Reservation_System.transactions JOIN Hotel_Reservation_System.customers ON transactions.customer_id = customers.customer_id WHERE transactions.customer_id = ?");
			stmt.setInt(1, customer_id);
			rs = stmt.executeQuery();
			String allMatches = "Transaction Id:		Amount:			First Name:	Last Name:	" + "\n" + "\n";
			while (rs.next()) {
				String transaction_id = Integer.toString(rs.getInt("transaction_id"));
				String amount = Float.toString(rs.getFloat("amount"));
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
			String custInfo = transaction_id + "\t" + "\t" + "\t" + amount + "\t" + "\t" + "\t" + first_name + "\t" + "\t" + last_name + "\n";
			allMatches += custInfo;
			}
			return allMatches;
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
		return "Error";
	}
	

	public String getVacancies() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;	
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT room_number, room_type FROM Hotel_Reservation_System.rooms WHERE current_occupant IS NULL");
			rs = stmt.executeQuery();
			String allMatches = "Room Number:	Room Type: " + "\n" + "\n";
			while (rs.next()) {
			String room_number = Integer.toString(rs.getInt("room_number"));
			String room_type = rs.getString("room_type");
			String custInfo = room_number + "\t" + "\t" + room_type + "\n";
			allMatches += custInfo;
			}
			return allMatches;
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
		return "Error";
	}
	
	
	public String getReservations() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;	
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT room_number, first_name, last_name FROM Hotel_Reservation_System.rooms JOIN Hotel_Reservation_System.customers ON rooms.current_occupant = customers.customer_id");
			rs = stmt.executeQuery();
			String allMatches = "Room Number:	First Name:	Last Name: " + "\n";
			while (rs.next()) {
			String room_number = Integer.toString(rs.getInt("room_number"));
			String first_name = rs.getString("first_name");
			String last_name = rs.getString("last_name");
			String custInfo = room_number + "\t" + "\t" + first_name + "\t" + "\t" + last_name + "\n";
			allMatches += custInfo;
			}
			return allMatches;
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
		return "Error";
	}
	

}
