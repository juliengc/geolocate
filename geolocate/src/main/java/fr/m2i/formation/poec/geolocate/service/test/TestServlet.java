package fr.m2i.formation.poec.geolocate.service.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.m2i.formation.poec.geolocate.domain.Tag;
import fr.m2i.formation.poec.geolocate.service.BDDServiceImpl;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServiceServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	
	@Inject
	BDDServiceImpl serv;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(serv.getLocatedObjects(0, 0));
		List<Tag> tags = serv.getTags(0, 0);
		out.println(serv.getLocatedObjects(tags.get(0), 0, 0));
		out.println(serv.getLocatedObjects(0.0, 0.0));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
