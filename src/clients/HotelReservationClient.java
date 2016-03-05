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

	/*
	public MysqlConnector connector;

	public HotelReservationClient(Scanner s, String dbName){
		connector = new MysqlConnector(s, dbName);
		Connection conn = connector.getConnection();
		connector.createDB(conn, dbName);
		connector.createTable(conn);
	}

	public HotelReservationClient(Scanner s, String dbName, String user, String pass){
		connector = new MysqlConnector(s, dbName, user, pass);
		Connection conn = connector.getConnection();
		connector.createDB(conn, dbName);
		connector.createTable(conn);
	}
*/
	
	public static void main(String args[]) {

		//Read from Servlet
		
		/*
		try {
			System.out.println("Making POST call");
			// Parse the URL
			String urlParameters  = "param1=a&param2=b&param3=c";
			byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
			int    postDataLength = postData.length;
			String request        = "http://localhost:8080/PA1/Reservations";
			URL    url            = new URL( request );
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();   
			
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty( "Content-Type", "text/html"); 
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
		*/
		//old code
		Scanner s = new Scanner(System.in);  // Reading from System.in
		//String dbName = "Hotel_Reservation_System";
		//HotelReservationClient client = new HotelReservationClient();
		String request = "";
		String params = "";
		/*
		String first_name = "";
		String last_name = "";
		String phone_number = "";
		String billing_address = "";
		String billing_city = "";
		String billing_state = "";
		String billing_zip = "";
		String checkin_date = "";
		String checkout_date = "";
		*/
		//MysqlConnector conn = client.connector;
		//String username = conn.getUser();
		//String password = conn.getPassword();
		int choice = 0;
		boolean again = true;
		//do {
			
			//try {
				
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
				try {
					System.out.println("Making GET call");
					request = "http://localhost:3306/PA1/Reservations";
					
					switch (choice) {
					case 1: 
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
						params = "choice=" + URLEncoder.encode(Integer.toString(choice), "UTF-8") + 
								"&first_name=" + URLEncoder.encode(first_name, "UTF-8") + "&last_name=" 
								+ URLEncoder.encode(last_name, "UTF-8") + "&phone_number=" 
								+ URLEncoder.encode(phone_number, "UTF-8") + "&billing_address=" + URLEncoder.encode(billing_address, "UTF-8") 
								+ "&billing_city=" + URLEncoder.encode(billing_city, "UTF-8") + "&billing_state=" + URLEncoder.encode(billing_state, "UTF-8")
								+ "&billing_zip=" + URLEncoder.encode(billing_zip, "UTF-8") + "&checkin_date=" + URLEncoder.encode(checkin_date, "UTF-8") 
								+ "&checkout_date=" + URLEncoder.encode(checkout_date, "UTF-8"); 

						break;
					case 2:
						
						break;
					case 3:
						
						break;
					case 4:
						
						break;
					case 5:
						
						break;
					case 6:
						
						break;
					case 7:
						
						break;
					case 8:
						
						break;
					case 9:
						
						break;
					case 10:
						// exit program
						System.out.println("Goodbye!");
						again = false;
					};
				} catch (Exception e) {
					System.out.println("That is not a valid entry.");
					//TODO Auto-generated catch block
					e.printStackTrace();

				};
				
				    request = request + "?" + params;
					try {
						
						System.out.println("Making POST call");
						// Parse the URL
						String urlParameters  = params;
						byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
						int    postDataLength = postData.length;
						//String request        = "http://localhost:8080/ServletExample/Hello?param2=b";
						URL    url            = new URL( request );
						HttpURLConnection conn= (HttpURLConnection) url.openConnection();
						
						conn.setDoOutput( true );
						conn.setInstanceFollowRedirects( false );
						conn.setRequestMethod( "POST" );
						conn.setRequestProperty( "Content-Type", "text/html"); 
						conn.setRequestProperty( "charset", "utf-8");
						conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
						conn.setUseCaches( false );
						conn.connect();
						try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
			   				wr.write( postData );
						}
			/*

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String next_record = null;
			while ((next_record = reader.readLine()) != null) {
				System.out.println(next_record);
						
						conn.setDoOutput( true );
						conn.setDoInput( true );
						conn.setInstanceFollowRedirects( false );
						conn.setRequestMethod( "POST" );
						conn.setRequestProperty( "Content-Type", "text/html"); 
						conn.setRequestProperty( "charset", "utf-8");
						conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
						conn.setUseCaches( false );
						conn.connect();

						try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
						   wr.write( postData );
						}
						

						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String next_record = null;
						while ((next_record = reader.readLine()) != null) {
							System.out.println(next_record);
						}
						
						
						URL    url            = new URL( request );
						HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
						System.out.println(request);
						conn.setInstanceFollowRedirects( false );
						conn.setDoOutput(true);
						conn.setRequestMethod( "GET" );
						conn.setUseCaches( false );
						
						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String next_record;
						while ((next_record = reader.readLine()) != null) {
							System.out.println(next_record);
						}
						reader.close();
/*
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						*/
					
					//String charset = java.nio.charset.StandardCharsets.UTF_8.name();
					//URLConnection connection = new URL(request + "?" + params).openConnection();
					//connection.setRequestProperty("Accept-Charset", charset);
					//InputStream response = connection.getInputStream();
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//HttpURLConnection conn = (HttpURLConnection) url.openConnection();   
			
					/*
				} catch (IOException e) {
					throw new RuntimeException("Please try again. \n" + e);
					
				}
				*/
		//} while (again == true);
	}

};

