package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import model.bean.User;
import model.BO.UserBO;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserBO userBO;
       
    public RegisterServlet() {
        super();
        this.userBO=new UserBO();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("userID")!=null) {
			response.sendRedirect(request.getContextPath()+"/home");
			return;
		}
		RequestDispatcher dispatcher=request.getRequestDispatcher("/Register.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!request.getParameter("password").equals(request.getParameter("retypePassword"))) {
			request.setAttribute("errorMsg", "Mật khẩu không khớp!");
			RequestDispatcher dispathcer=request.getServletContext().getRequestDispatcher("/Register.jsp");
			dispathcer.forward(request, response);
			return;
		}
		if(userBO.getUserByEmail(request.getParameter("email"))!=null){
			request.setAttribute("errorMsg", "Email đã tồn tại!");
			RequestDispatcher dispathcer=request.getServletContext().getRequestDispatcher("/Register.jsp");
			dispathcer.forward(request, response);
			return;
		}
		User user=new User(request.getParameter("fullName"), request.getParameter("email"), request.getParameter("password"));
		userBO.createUser(user);
		response.sendRedirect(request.getContextPath()+"/login");
		return;
	}

}
