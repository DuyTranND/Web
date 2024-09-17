package simpleweb.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import simpleweb.models.UserModel;
import simpleweb.services.IUserService;
import simpleweb.services.impl.UserServiceImpl;
import simpleweb.ultis.Constant;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	IUserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/*
		 * HttpSession session = req.getSession(false); if (session != null &&
		 * session.getAttribute("account") != null) {
		 * resp.sendRedirect(req.getContextPath() + "/waiting"); return; } // Check
		 * cookie Cookie[] cookies = req.getCookies(); if (cookies != null) { for
		 * (Cookie cookie : cookies) { if (cookie.getName().equals("username")) {
		 * session = req.getSession(true); session.setAttribute("username",
		 * cookie.getValue()); resp.sendRedirect(req.getContextPath() + "/waiting");
		 * return; } } }
		 */
		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String remember = req.getParameter("rememberme");

		String alertMsg = "";
		boolean isRememberMe = false;
		if ("on".equals(remember)) {
			isRememberMe = true;
		}
		if (username.isEmpty() || password.isEmpty()) {
			alertMsg = "All fields are required.";
			req.setAttribute("Alert", alertMsg);
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			return;
		}
		UserModel user = service.login(username, password);
		if (user != null) {
			HttpSession session = req.getSession(true);
			session.setAttribute("account", user);
			if (isRememberMe) {
				saveRemeberMe(resp, username);
			}
			resp.sendRedirect(req.getContextPath() + "/waiting");
		} else {
			alertMsg = "Incorrect username or password.";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);

		}
	}

	private void saveRemeberMe(HttpServletResponse response, String username) {
		Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
		cookie.setMaxAge(30 * 60);
		response.addCookie(cookie);
	}
}