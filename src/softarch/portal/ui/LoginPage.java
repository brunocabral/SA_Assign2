package softarch.portal.ui;

import softarch.portal.app.ApplicationException;
import softarch.portal.app.ApplicationFacade;
import softarch.portal.data.UserProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

/**
 * This class implements the portal's login page. Functionality that doesn't
 * change the state of the portal is provided by the <code>doGet</code> method,
 * functionality that does is provided by the <code>doPost</code> method.
 * 
 * @author Niels Joncheere
 * 
 *         Updated by Humberto Rodriguez Avila
 */
public class LoginPage extends Page {
	/**
	 * Creates a new login page.
	 * 
	 * @param appFacade
	 *            The application facade the page should use to send its
	 *            requests to.
	 */
	public LoginPage(ApplicationFacade appFacade) {
		this.appFacade = appFacade;
	}

	/**
	 * This method provides the functionality that does not change the state of
	 * the portal.
	 * 
	 * @param request
	 *            The HTTP request that was sent to the server.
	 * @param response
	 *            The HTTP response that will be sent to the client.
	 * @return An XML string that should be transformed to an HTML page
	 *         containing some kind of login form.
	 */
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		this.id = 1;
		this.title = "Login Page";
		this.description = "Using the login form on this page, you can " + "login to SoftLib.";
		return makePage("<LoginPage_Get />");
	}

	/**
	 * This method provides the functionality that changes the state of the
	 * portal.
	 * 
	 * @param request
	 *            The HTTP request that was sent to the server.
	 * @param response
	 *            The HTTP response that will be sent to the client.
	 * @return An XML string that contains the user's user profile, and the page
	 *         he should be redirected to.
	 */
	public String doPost(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			response.addCookie(new Cookie("SWP_SessionID",
					appFacade.login(request.getParameter("Username"), request.getParameter("Password")).toString()));
			response.addCookie(new Cookie("SWP_Username", request.getParameter("Username")));
			
			UserProfile profile = appFacade.findUser(request.getParameter("Username"));
			response.addCookie(new Cookie("SWP_DefaultPage", profile.getDefaultPage()));
			appFacade.setCurrentUserName(profile.getUsername());
			this.id = 10;
			this.title = "User Info Page";
			this.userName = profile.getUsername();
			response.sendRedirect(profile.getDefaultPage());
			return profile.getDefaultPage();
//			this.description = "This page shows you the data provided during the registration.";
//			return makePage("<LoginPage_Post>" + profile.asXml() + "<DefaultPage>" + profile.getDefaultPage()
//					+ "</DefaultPage>" + "</LoginPage_Post>");
		} catch (ApplicationException e) {
			return makePage("<LoginPage_Error>" + "<Message>" + e.getMessage() + "</Message>" + "</LoginPage_Error>");
		} catch (Exception e) {
			return makePage("<LoginPage_Error>" + "<Message>The login page has caught an " + "unexpected exception: "
					+ e.getMessage() + "</Message>" + "</LoginPage_Error>");
		}
	}
}
