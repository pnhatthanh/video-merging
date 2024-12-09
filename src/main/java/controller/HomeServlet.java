package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BO.UserBO;
import model.BO.VideoBO;
import model.bean.Video;

import java.io.IOException;
import java.util.List;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public HomeServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		if(session==null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		if(session.getAttribute("userID")==null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		String userID = (String)session.getAttribute("userID");
		String fullName=(String)session.getAttribute("fullName");
		List<Video> listVideo = new VideoBO().getVideoByUser(userID);
		request.setAttribute("listVideo", listVideo);
		request.setAttribute("fullName", fullName);
		RequestDispatcher dispathcer=request.getServletContext().getRequestDispatcher("/Home.jsp");
		dispathcer.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
