/*
 * COMP 6302 - Web Services / Internet
 * PA1: Hotel Reservation System
 * Marc Badrian and Hien Vo - Due 3/5/16
 * 
 */

package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.Customer;
import servlets.MysqlConnector;


/**
 * Servlet implementation class EchoServlet
 */
@WebServlet(name = "HotelReservationServlet",
description = "This is my first annotated servlet",
urlPatterns = {"/HotelReservationServlet", "/Reservations", "/Customer", "/Transactions"})
public class HotelReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//out.println(choice);
	private static final String dbName = "Hotel_Reservation_System";
	private static final MysqlConnector connector = new MysqlConnector();
	Connection conn = connector.getConnection();
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public HotelReservationServlet() {
        super();
    }

    public void init() throws ServletException
    {
		connector.createDB(conn, dbName);
		connector.createCustomersTable(conn);
		connector.createRoomsTable(conn);
		connector.createTransactionsTable(conn);
		connector.populateRooms(conn);
    };

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	MysqlConnector connector = new MysqlConnector();
		Integer choice = Integer.parseInt(request.getParameter("choice"));
		PrintWriter out = response.getWriter();
		//out.println(choice);
		
		try {
			
			switch (choice) {
			case 4: 
				Integer customer_id = Integer.parseInt(request.getParameter("customer_id"));
				String info = connector.getCustomer(customer_id);
				out.println("\n" + info);
				break;
			case 5:
				String first_name = request.getParameter("first_name");
				String last_name = request.getParameter("last_name");
				String custsByName = connector.getCustomersByName(first_name, last_name);
				out.println("\n" + custsByName);
				break;
			case 6:
				String custsCurrent = connector.getCustomersCurrent();
				out.println("\n" + custsCurrent);
				break;
			case 7:
				Integer c_id = Integer.parseInt(request.getParameter("customer_id"));
				String transactions = connector.getTransactions(c_id);
				out.println("\n" + transactions);
				break;
			case 8:
				String vacancies = connector.getVacancies();
				out.println("\n" + vacancies);
				break;
			case 9:
				String reservations = connector.getReservations();
				out.println("\n" + reservations);
				break;
			};
			} catch (Exception e) {
				System.out.println("That is not a valid entry.");
				//TODO Auto-generated catch block
				e.printStackTrace();
	};

	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    	MysqlConnector connector = new MysqlConnector();
				Integer choice = Integer.parseInt(request.getParameter("choice"));
				PrintWriter out = response.getWriter();
				
				try {
					
					switch (choice) {
					case 1: 
						String first_name = request.getParameter("first_name");
						String last_name = request.getParameter("last_name");
						Integer phone_number = Integer.parseInt(request.getParameter("phone_number"));
						String billing_address = request.getParameter("billing_address");
						String billing_city = request.getParameter("billing_city");
						String billing_state = request.getParameter("billing_state");
						Integer billing_zip = Integer.parseInt(request.getParameter("billing_zip"));
						String checkin_date = request.getParameter("checkin_date");
						String checkout_date = request.getParameter("checkout_date");
						Customer newCust = new Customer();
						newCust.setName(first_name, last_name);
						newCust.setNumber(phone_number);
						newCust.setBillingInfo(billing_address, billing_city, billing_state, billing_zip);
						newCust.setCheckInOut(checkin_date, checkout_date);
						boolean success = connector.insertCustomer(newCust);
						int id = connector.getCustomerId(first_name, last_name);
						response.setContentType("text/html");
						if(!success){
						      out.println("<h1>" + "Error!" + "</h1>");
						      break;
						}else{
							out.println("You have successfully created a new customer!" + "\n" + "Customer id: " + id);
						      break;
						}
					case 2:
						int customer_id = Integer.parseInt(request.getParameter("customer_id"));
						int room_number = Integer.parseInt(request.getParameter("room_number"));
						boolean reserved = connector.reserveRoom(customer_id, room_number);

						response.setContentType("text/html");
						if(!reserved){
						      out.println("Error!");
						      break;
						}else{
							out.println("You have successfully reserved room: " + room_number + " for customer id: " + customer_id);
						      break;
						}
					case 3:
						Integer cust_id = Integer.parseInt(request.getParameter("customer_id"));
						Integer room_id = Integer.parseInt(request.getParameter("room_number"));
						float amount = Float.valueOf(request.getParameter("amount"));
						Integer cc_number = Integer.parseInt(request.getParameter("cc_number"));
						Integer exp_date = Integer.parseInt(request.getParameter("exp_date"));
						boolean transaction = connector.createPayment(cust_id, room_id, amount, cc_number, exp_date);
						int transaction_id = connector.getTransactionId(cust_id, room_id);
						response.setContentType("text/html");
						if(!transaction){
						      out.println("Error!");
						      break;
						}else{
							out.println("You have successfully submitted a payment! Your transaction id number is: " + transaction_id);
						      break;
						}
					};
				} catch (Exception e) {
					System.out.println("That is not a valid entry.");
					e.printStackTrace();

				};

			}

		
	}
