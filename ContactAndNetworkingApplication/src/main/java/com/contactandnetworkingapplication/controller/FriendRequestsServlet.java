package com.contactandnetworkingapplication.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.contactandnetworkingapplication.dao.FriendRequestsDaoInterface;
import com.contactandnetworkingapplication.dao.RegistrationDaoInterface;
import com.contactandnetworkingapplication.model.FriendRequest;
import com.contactandnetworkingapplication.model.User;
import com.contactandnetworkingapplication.utility.DaoFactory;

/**
 * Servlet implementation class FriendRequestsServlet
 */
@WebServlet("/FriendRequestsServlet")
public class FriendRequestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public FriendRequestsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String option = request.getParameter("option");
		
		if(option.equals("view")) {
			FriendRequestsDaoInterface ud = DaoFactory.createFriendRequestsObject();
			
			HttpSession session = request.getSession(true);
			int id=(int) session.getAttribute("id");
			User u = new User();
			u.setId(id);
			System.out.println("user id " + session.getAttribute("id"));
			List<FriendRequest> list=ud.viewFriendRequestsDao(u);
			
			if(list.size()>0) {
				request.setAttribute("list",list);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/FriendRequests.jsp");
				rd.forward(request, response);
			}
			else {
				request.setAttribute("message","No friend request to show.");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/FriendRequests.jsp");
				rd.forward(request, response);
			}
		}
		else if(option.equals("accept")) {
			FriendRequestsDaoInterface ud = DaoFactory.createFriendRequestsObject();
			
			int id = Integer.parseInt(request.getParameter("id"));
			int sender_id = Integer.parseInt(request.getParameter("sender_id"));
			int receiver_id = Integer.parseInt(request.getParameter("receiver_id"));
			FriendRequest f = new FriendRequest();
			f.setFriend_request_pk(id);
			f.setSender_id(sender_id);
			f.setReceiver_id(receiver_id);
			System.out.println(f.getFriend_request_pk() + " " + f.getSender_id() + " " + f.getReceiver_id());
			int res=ud.acceptFriendRequestDao(f);
			
			if(res==1) {
				request.setAttribute("message","Friend Request accepted successfully");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/FriendRequestsServlet?option=view");
				rd.forward(request, response);
			}
			else {
				request.setAttribute("message","Was unable to accept Friend Request. Please try again.");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/FriendRequestsServlet?option=view");
				rd.forward(request, response);
			}
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
