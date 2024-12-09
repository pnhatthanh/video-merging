package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;


import model.bean.User;
import model.BO.UserBO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserBO userBO;

    public LoginServlet() {
        super();
        this.userBO=new UserBO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("userID")!=null) {
			response.sendRedirect(request.getContextPath()+"/home");
			return;
		}
		RequestDispatcher dispathcer=request.getRequestDispatcher("/Login.jsp");
		dispathcer.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user=userBO.getUser(request.getParameter("email"), request.getParameter("password"));
		if(user==null) {
			request.setAttribute("errorMsg", "Email hoặc mật khẩu không chính xác!");
			RequestDispatcher dispathcer=request.getServletContext().getRequestDispatcher("/Login.jsp");
			dispathcer.forward(request, response);
			return;
		}
		HttpSession session=request.getSession();
		session.setAttribute("userID", user.getId());
		session.setAttribute("fullName", user.getFullName());
		response.sendRedirect(request.getContextPath()+"/home");
	}

}
