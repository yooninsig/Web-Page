

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import BoarderModel.BoarderDao;

public class SaveNewEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newevent=request.getParameter("newevent");
		String eventdate=request.getParameter("eventdate");
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("ID");
		
		
		BoarderDao dao = new BoarderDao();
		boolean result=dao.saveNewEvent(username,newevent,eventdate);
	 
		
		PrintWriter out = response.getWriter();
		out.println(result);
		RequestDispatcher rd = request.getRequestDispatcher("MyPage");
 		rd.forward(request, response); 
	}

}
