package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BO.MergeVideoMangerBO;
import model.BO.UpVideoToHDManagerBO;

import java.io.IOException;

@WebServlet("/MergeVideoServlet")
public class MergeVideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MergeVideoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pID = Integer.parseInt(request.getParameter("pID"));
		double mergeStatus = (MergeVideoMangerBO.getInstance().progressManagerMap.get(pID)==null)?0:MergeVideoMangerBO.getInstance().progressManagerMap.get(pID).getStatus();
		double upHDStatus = (UpVideoToHDManagerBO.getInstance().progressManagerMap.get(pID)==null)?0:UpVideoToHDManagerBO.getInstance().progressManagerMap.get(pID).getStatus();
		response.getWriter().write(String.format("{\"progressMerge\": %.2f, \"progressHD\": %.2f}", mergeStatus,upHDStatus));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userID = (String)request.getSession().getAttribute("userID");
		int pID = (int)request.getSession().getAttribute("pID");
		MergeVideoMangerBO.getInstance().postTask(userID, pID);
	}

}
