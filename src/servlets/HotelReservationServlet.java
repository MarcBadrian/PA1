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
urlPatterns = {"/HotelReservationServlet", "/Reservations", "/Customers", "/Transactions"})
public class HotelReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
    	//Scanner s = new Scanner(System.in);  // Reading from System.in
		//String dbName = "Hotel_Reservation_System";
    	//MysqlConnector connector;
    	//connector = new MysqlConnector(dbName);
		//Connection conn = connector.getConnection();
		connector.createDB(conn, dbName);
		connector.createCustomersTable(conn);
		connector.createRoomsTable(conn);
		connector.createTransactionsTable(conn);
		connector.populateRooms(conn);

		//String username = ((MysqlConnector) conn).getUser();
		//String password = ((MysqlConnector) conn).getPassword();

        // Do required initialization
    	
    };

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Scanner s = new Scanner(System.in);  // Reading from System.in
		//String dbName = "Hotel_Reservation_System";
    	//MysqlConnector connector;
    	//connector = new MysqlConnector(dbName);
		//Connection conn = connector.getConnection();

	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//Scanner s = new Scanner(System.in);  // Reading from System.in
		    	MysqlConnector connector = new MysqlConnector();
				Integer choice = Integer.parseInt(request.getParameter("choice"));
				PrintWriter out = response.getWriter();
				out.println(choice);
				
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
						out.println(first_name + last_name + phone_number + billing_address + billing_zip + checkout_date);
						Customer newCust = new Customer();
						newCust.setName(first_name, last_name);
						newCust.setNumber(phone_number);
						newCust.setBillingInfo(billing_address, billing_city, billing_state, billing_zip);
						newCust.setCheckInOut(checkin_date, checkout_date);
						boolean success = connector.insertCustomer(newCust);
					    out.println(success);
						int id = connector.new_customer_id;
						response.setContentType("text/html");
					    out.println("<h1>" + newCust + "</h1>");
						if(!success){
						      out.println("<h1>" + "Error!" + "</h1>");
						      break;
						}else{
						      out.println("<h1>" + "Customer id: " + id + "</h1>");
						      break;
						}
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
					};
				} catch (Exception e) {
					System.out.println("That is not a valid entry.");
					//TODO Auto-generated catch block
					e.printStackTrace();

				};

			}

		
	}
