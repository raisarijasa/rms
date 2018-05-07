package com.mitrais.rms.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mitrais.rms.dao.UserDao;
import com.mitrais.rms.dao.impl.UserDaoImpl;
import com.mitrais.rms.model.User;

import java.io.IOException;

@WebServlet("/auth/*")
public class LoginServlet extends AbstractController {
	private static final String HOME = "/index.jsp";
	private static final String LOGIN = "/login";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = HOME;
		String pathInfo = "";
		if (LOGIN.equalsIgnoreCase(req.getPathInfo())) {
			System.out.println("=======login");
			pathInfo = LOGIN;
			path = getTemplatePath(pathInfo);
			System.out.println("======= " + path);
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
			requestDispatcher.forward(req, resp);
			return;
		} else if ("/logout".equalsIgnoreCase(req.getPathInfo())) {
			HttpSession session = req.getSession(false);
			session.removeAttribute("username");
			session.getMaxInactiveInterval();
			System.out.println("=======logout");
			path = HOME;
			System.out.println("======= " + path);
		} else {
			System.out.println("=======home");
			path = HOME;
		}
		resp.sendRedirect(req.getContextPath() + path);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("=======login post");
		UserDao userDao = UserDaoImpl.getInstance();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = userDao.findByUserName(username).get();
		if (user.getUserName().equalsIgnoreCase(username) && user.getPassword().equalsIgnoreCase(password)) {
			HttpSession session = req.getSession();
			session.setAttribute("username", username);
//			RequestDispatcher requestDispatcher = req.getRequestDispatcher(HOME);
//			requestDispatcher.forward(req, resp);
			resp.sendRedirect(req.getContextPath() + HOME);
		}
	}
}
