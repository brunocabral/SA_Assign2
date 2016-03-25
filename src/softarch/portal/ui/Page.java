package softarch.portal.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import softarch.portal.app.ApplicationFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * This is an abstract superclass for all of the portal's pages.
 * 
 * @author Niels Joncheere
 * 
 * Updated by Humberto Rodriguez Avila
 */
public abstract class Page {
	protected int id;
	protected String title;
	protected String description;
	protected String userName;

	protected static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	protected ApplicationFacade appFacade;

	/**
	 * This method returns an XML string that represents the page's response to
	 * an HTML GET request.
	 */
	public abstract String doGet(HttpServletRequest request, HttpServletResponse response);

	/**
	 * This method returns an XML string that represents the page's response to
	 * an HTML POST request.
	 */
	public abstract String doPost(HttpServletRequest request, HttpServletResponse response);

	/**
	 * This method constructs a <i>page</i> from an XML input string.
	 */
	protected String makePage(String input) {
		StringBuilder output = new StringBuilder();
		output.append("<Page>");
		output.append("<Id>" + id + "</Id>");
		output.append("<Title>" + title + "</Title>");
		output.append("<Description>" + description + "</Description>");
		output.append(input);
		output.append("<Time>" + new Date() + "</Time>");
		output.append("<UserName>" + userName + "</UserName>");
		output.append("</Page>");
		return output.toString();
	}
}
