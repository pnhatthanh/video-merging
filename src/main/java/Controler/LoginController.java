package Controler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.BEAN.User;
import Model.BO.UserBO;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserBO userBO;

    public LoginController() {
        super();
        this.userBO=new UserBO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		session.setAttribute("uid", user.getId());
		response.sendRedirect(request.getContextPath()+"/home");
	}

}
