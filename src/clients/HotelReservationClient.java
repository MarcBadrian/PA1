package clients;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;


import model.Customer;
import model.Room;
import model.Transaction;
import sun.net.www.URLConnection;

public class HotelReservationClient {
	
	public static void main(String args[]) {

		Scanner s = new Scanner(System.in);  // Reading from System.in
		int choice = 0;
		boolean again = true;
		do {
			try {
				System.out.print("\n" + "Choose one of the following options: " + "\n"
						+ "(1) CREATE CUSTOMER" + "\n"
						+ "(2) RESERVE ROOM" + "\n"
						+ "(3) CREATE PAYMENT" + "\n"
						+ "(4) GET CUSTOMER [customer_id] " + "\n"
						+ "(5) GET CUSTOMERS BYNAME [customer_name]" + "\n"
						+ "(6) GET CUSTOMERS CURRENT" + "\n"
						+ "(7) GET TRANSACTIONS [customer_id]" + "\n"
						+ "(8) GET VACANCIES" + "\n"
						+ "(9) GET RESERVATIONS" + "\n"
						+ "(10) EXIT PROGRAM" + "\n" + "\n"
						+ "Please enter a number:  ");
				try {
					choice = Integer.parseInt(s.nextLine());
				}catch(NumberFormatException nfe){
					System.err.println("Invalid Format!");
				}
				System.out.println();
				switch (choice) {
				case 1: 
					// Create a new customer
					System.out.println("To create a new customer..." + "\n");
					System.out.println("Please enter the customer First Name: ");
					String first_name = s.nextLine();
					System.out.println("Please enter the customer Last Name: ");
					String last_name = s.nextLine();
					System.out.println("Please enter the customer Phone Number: ");
					String phone_number = s.nextLine();
					System.out.println("Please enter the customer Billing Address: ");
					String billing_address = s.nextLine();
					System.out.println("Please enter the customer Billing City: ");
					String billing_city = s.nextLine();
					System.out.println("Please enter the customer Billing State: ");
					String billing_state = s.nextLine();
					System.out.println("Please enter the customer Billing Zip: ");
					String billing_zip = s.nextLine();
					System.out.println("Please enter the customer Check-in Date: ");
					String checkin_date = s.nextLine();
					System.out.println("Please enter the customer Check-out Date: ");
					String checkout_date = s.nextLine();
					try {
						System.out.println();
						System.out.println("<Making POST call>");
						System.out.println();

						// Parse the URL
						String urlParameters  = "choice=" + Integer.toString(choice) + 
								"&first_name=" + first_name + "&last_name=" + last_name + "&phone_number=" 
								+ phone_number + "&billing_address=" + billing_address + "&billing_city=" 
								+ billing_city + "&billing_state=" + billing_state + "&billing_zip=" 
								+ billing_zip + "&checkin_date=" + checkin_date + "&checkout_date=" + checkout_date;
						byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
						int    postDataLength = postData.length;
						String request        = "http://localhost:8080/PA1/Reservations";
						URL    url            = new URL( request );
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();   
						
						conn.setDoOutput( true );
						conn.setInstanceFollowRedirects( false );
						conn.setRequestMethod( "POST" );
						conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
						conn.setRequestProperty( "charset", "utf-8");
						conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
						conn.setUseCaches( false );
						try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
						   wr.write( postData );
						}
						

						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String next_record = null;
						while ((next_record = reader.readLine()) != null) {
							System.out.println(next_record);
						}
					} catch (IOException e) {
						throw new RuntimeException("Please try again. \n" + e);
					}
					break;
				case 2:
					System.out.println("To reserve a room...");
					System.out.print("Please enter a customer id: ");
					String customer_id = s.nextLine();
					System.out.println("Please enter a room number: ");
					String room_number = s.nextLine();

					try {
						System.out.println();
						System.out.println("<Making POST call>");
						System.out.println();
						
						// Parse the URL
						String urlParameters  = "choice=" + choice + "&customer_id=" + customer_id + "&room_number=" + room_number;
						byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
						int    postDataLength = postData.length;
						String request        = "http://localhost:8080/PA1/Reservations";
						URL    url            = new URL( request );
						HttpURLConnection conn= (HttpURLConnection) url.openConnection();   
						
						conn.setDoOutput( true );
						conn.setInstanceFollowRedirects( false );
						conn.setRequestMethod( "POST" );
						conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
						conn.setRequestProperty( "charset", "utf-8");
						conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
						conn.setUseCaches( false );
						try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
						   wr.write( postData );
						}
						

						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String next_record = null;
						while ((next_record = reader.readLine()) != null) {
							System.out.println(next_record);
						}
					} catch (IOException e) {
						throw new RuntimeException("Please try again. \n" + e);
					}
					break;
				case 3:
					//Get and display all todo items
					System.out.println("Getting ALL records...");
						//Retrieve row data
					try {
						System.out.println();
						System.out.println("<Making GET call>");
						System.out.println();
						
						String request        = "http://localhost:8080/PA1/Reservations";
						URL    url            = new URL( request );
						HttpURLConnection conn= (HttpURLConnection) url.openConnection();   
						
						conn.setInstanceFollowRedirects( false );
						conn.setRequestMethod( "GET" );
						conn.setUseCaches( false );
						
						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String next_record = null;
						while ((next_record = reader.readLine()) != null) {
							System.out.println(next_record);	
					}
							
					} catch (IOException e) {
						throw new RuntimeException("Please try again. \n" + e);
					}
					break;
				case 4:
					// Deletes the todo message at the given id from the database.
					System.out.println("To DELETE a record...");
					System.out.print("Enter an id number: ");
					int deleteId = Integer.parseInt(s.nextLine());

					try {
						System.out.println();
						System.out.println("<Making POST call>");
						System.out.println();
						
						// Parse the URL
						String urlParameters  = "choice=" + choice + "&id=" + deleteId;
						byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
						int    postDataLength = postData.length;
						String request        = "http://localhost:8080/PA1/Reservations";
						URL    url            = new URL( request );
						HttpURLConnection conn= (HttpURLConnection) url.openConnection();   
						
						conn.setDoOutput( true );
						conn.setInstanceFollowRedirects( false );
						conn.setRequestMethod( "POST" );
						conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
						conn.setRequestProperty( "charset", "utf-8");
						conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
						conn.setUseCaches( false );
						try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
						   wr.write( postData );
						}
						

						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String next_record = null;
						while ((next_record = reader.readLine()) != null) {
							System.out.println(next_record);
						}
					} catch (IOException e) {
						throw new RuntimeException("Please try again. \n" + e);
					}
					break;
				case 5:
					// Insert an ToDo item.
					System.out.println("To PUT a record...");
					System.out.print("Enter an id number: ");
					int putId = Integer.parseInt(s.nextLine());
					System.out.print("Enter a message: ");
					String putMessage = s.nextLine();
					try {
						System.out.println();
						System.out.println("<Making POST call>");
						System.out.println();
						
						// Parse the URL
						String urlParameters  = "choice=" + choice + "&id=" + putId + "&putMessage=" + putMessage;
						byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
						int    postDataLength = postData.length;
						String request        = "http://localhost:8080/PA1/Reservations";
						URL    url            = new URL( request );
						HttpURLConnection conn= (HttpURLConnection) url.openConnection();   
						
						conn.setDoOutput( true );
						conn.setInstanceFollowRedirects( false );
						conn.setRequestMethod( "POST" );
						conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
						conn.setRequestProperty( "charset", "utf-8");
						conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
						conn.setUseCaches( false );
						try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
						   wr.write( postData );
						}
						

						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String next_record = null;
						while ((next_record = reader.readLine()) != null) {
							System.out.println(next_record);
						}
					} catch (IOException e) {
						throw new RuntimeException("Please try again. \n" + e);
					}
					break;
				case 6:
					// exit program
					System.out.println("Goodbye!");
					again = false;
				};
			} catch (Exception e) {
				System.out.println("That is not a valid entry.");
				e.printStackTrace();

			};
		} while (again == true);
	
	}
	
}
