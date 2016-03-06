/*
 * COMP 6302 - Web Services / Internet
 * PA1: Hotel Reservation System
 * Marc Badrian and Hien Vo - Due 3/5/16
 * 
 */

package clients;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


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
					System.out.println("Please enter the customer Phone Number (only numbers, no dashes or spaces please): ");
					String phone_number = s.nextLine();
					System.out.println("Please enter the customer Billing Address: ");
					String billing_address = s.nextLine();
					System.out.println("Please enter the customer Billing City: ");
					String billing_city = s.nextLine();
					System.out.println("Please enter the customer Billing State: ");
					String billing_state = s.nextLine();
					System.out.println("Please enter the customer Billing Zip (only numbers, no dashes or spaces please): ");
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
					// Create a payment transaction
					System.out.println("To create a payment transaction..." + "\n");
					System.out.println("Please enter the Customer Id: ");
					String cust_id = s.nextLine();
					System.out.println("Please enter the Room Number: ");
					String room_id = s.nextLine();
					System.out.println("Please enter the Payment Amount: ");
					String amount = s.nextLine();
					System.out.println("Please enter the Credit Card Number (only numbers, no dashes or spaces please): ");
					String cc_number = s.nextLine();
					System.out.println("Please enter the Expriation Date (only numbers, no dashes or spaces please): ");
					String exp_date = s.nextLine();

					try {
						System.out.println();
						System.out.println("<Making POST call>");
						System.out.println();

						// Parse the URL
						String urlParameters  = "choice=" + Integer.toString(choice) + 
								"&customer_id=" + cust_id + "&room_number=" + room_id + "&amount=" 
								+ amount + "&cc_number=" + cc_number + "&exp_date=" 
								+ exp_date;
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
				case 4:
					//Get customer record based on customer_id
					System.out.println("To get customer based on id...");
					System.out.print("Please enter a customer id: ");
					String customer = s.nextLine();
						//Retrieve row data
					try {
						System.out.println();
						System.out.println("<Making GET call>");
						System.out.println();
						
						String request        = "http://localhost:8080/PA1/Reservations" + "?choice=" + choice + "&customer_id=" + customer;

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
				case 5:
					//Get customer record based on customer_id
					System.out.println("To get a list of customers matching a first or last name...");
					System.out.print("Please enter a customer name: ");
					String name = s.nextLine();
					int index = name.indexOf(" ");
					String f_name = name.substring(0,  index);
					String l_name = name.substring(index + 1);
						//Retrieve row data
					try {
						System.out.println();
						System.out.println("<Making GET call>");
						System.out.println();
						
						String request        = "http://localhost:8080/PA1/Reservations" + "?choice=" + choice + "&first_name=" + f_name + "&last_name=" + l_name;

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
				case 6:
					//Get customer record based on customer_id
					System.out.println("Getting a list of customers currently checked into the hotel...");
						//Retrieve row data
					try {
						System.out.println();
						System.out.println("<Making GET call>");
						System.out.println();
						String request        = "http://localhost:8080/PA1/Reservations" + "?choice=" + choice;
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
				case 7:
					//Get transaction record based on customer_id
					System.out.println("To get a customer's transaction records...");
					System.out.print("Please enter a customer id: ");
					String c_id = s.nextLine();
						//Retrieve row data
					try {
						System.out.println();
						System.out.println("<Making GET call>");
						System.out.println();
						
						String request        = "http://localhost:8080/PA1/Reservations" + "?choice=" + choice + "&customer_id=" + c_id;

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
				case 8:
					//Get all rooms and their type that are vacant
					System.out.println("Getting a list of all rooms and their type that are vacant...");
						//Retrieve row data
					try {
						System.out.println();
						System.out.println("<Making GET call>");
						System.out.println();
						String request        = "http://localhost:8080/PA1/Reservations" + "?choice=" + choice;
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
				case 9:
					//Get all rooms currently occupied by a customer
					System.out.println("Getting a list of all rooms currently occupied by a customer...");
						//Retrieve row data
					try {
						System.out.println();
						System.out.println("<Making GET call>");
						System.out.println();
						String request        = "http://localhost:8080/PA1/Reservations" + "?choice=" + choice;
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
				case 10:
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
