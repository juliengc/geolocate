package fr.m2i.formation.poec.geolocate.domain.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.m2i.formation.poec.geolocate.service.BDDService;
import fr.m2i.formation.poec.geolocate.service.BDDServiceImpl;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestBDDServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Inject
	BDDTest bdd;
	
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
		out.println((bdd.getTags()));
		bdd.creationTagTest();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
