package softarch.portal.ui;

import javax.servlet.http.HttpServlet;
import java.util.Map;
import java.util.Hashtable;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.transform.stream.StreamSource;


import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import softarch.portal.app.ApplicationFacade;

/**
 * This class implements the portal's internet front-end. It is a
 * <code>javax.servlet.http.HttpServlet</code> that accepts HTTP GET and POST
 * requests from the user and forwards these requests to the appropriate page.
 * 
 * @author Niels Joncheere
 * 
 * Updated by Humberto Rodriguez Avila
 */
public class InternetFrontEnd extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Page> pages = new Hashtable<String, Page>();
	private Properties properties;

	/**
	 * Handles HTTP GET requests. This method accepts an HTTP GET request, and
	 * forwards it to the page given in the request's <i>Page</i> parameter.
	 * This page will return an XML string that will be transformed to HTML
	 * using a <code>javax.xml.transform.Transformer</code>.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String parameter = request.getParameter("Page");
		Page page;
		if (parameter != null && pages.containsKey(parameter))
			page = (Page) pages.get(parameter);
		else
			page = (Page) pages.get("Login");
		response.setContentType("text/html; charset=ISO-8859-1");
		PrintWriter out = response.getWriter();
		try {
			StreamSource input = new StreamSource(new StringReader(page.doGet(request, response)));
			StreamSource stylesheet = new StreamSource(
					new URL("file", "", properties.getProperty("stylesheet")).openStream());
			StreamResult output = new StreamResult(out);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer(stylesheet);
			transformer.transform(input, output);
		} catch (Exception e) {
			out.write(e.getMessage());
			e.printStackTrace(out);
		}
	}

	/**
	 * Handles HTTP POST requests. This method accepts an HTTP POST request, and
	 * forwards it to the page given in the request's <i>Page</i> parameter.
	 * This page will return an XML string that will be transformed to HTML
	 * using a <code>javax.xml.transform.Transformer</code>.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String parameter = request.getParameter("Page");
		Page page;
		if (parameter != null && pages.containsKey(parameter))
			page = (Page) pages.get(parameter);
		else
			page = (Page) pages.get("Login");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			StreamSource input = new StreamSource(new StringReader(page.doPost(request, response)));
			StreamSource stylesheet = new StreamSource(
					new URL("file", "", properties.getProperty("stylesheet")).openStream());
			StreamResult output = new StreamResult(out);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer(stylesheet);
			transformer.transform(input, output);
		} catch (Exception e) {
			out.write(e.getMessage());
			e.printStackTrace(out);
		}
	}

	/**
	 * Initializes the <code>HttpServlet</code>.
	 */
	public void init() throws ServletException {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String fullPath = "";

		try {
			fullPath = URLDecoder.decode(path, "UTF-8");
			String pathArr[] = fullPath.split(".metadata/");
			fullPath = pathArr[0] + "web_portal";

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String dbUser = getInitParameter("db_user");
		String dbPassword = getInitParameter("db_pass");
		String dbUrl = fullPath+getInitParameter("db_url");
		String stylesheet = fullPath+getInitParameter("stylesheet");

		properties = new Properties();
		properties.put("dbUser", dbUser);
		properties.put("dbPassword", dbUser);
		properties.put("dbUrl", dbUser);
		properties.put("stylesheet", stylesheet);

		ApplicationFacade appFacade = new ApplicationFacade(dbUser, dbPassword, dbUrl);

		pages.put("Registration", new RegistrationPage(appFacade));
		pages.put("Login", new LoginPage(appFacade));
		pages.put("Query", new QueryPage(appFacade));
		pages.put("Administration", new AdministrationPage(appFacade));
		pages.put("Operation", new OperationPage(appFacade));
		pages.put("Logout", new LogoutPage(appFacade));

	}

	/**
	 * Returns a string containing some basic information about the
	 * <code>HttpServlet</code>.
	 */
	public String getServletInfo() {
		return "Semantic Web Portal -- Copyright (C) 2002-2003 " + "Niels Joncheere";
	}
}
