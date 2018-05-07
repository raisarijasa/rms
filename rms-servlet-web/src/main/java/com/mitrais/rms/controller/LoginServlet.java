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

@WebServlet("/login")
public class LoginServlet extends AbstractController {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("=======login");
		String path = getTemplatePath(req.getServletPath());
		System.out.println(path);
		RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
		requestDispatcher.forward(req, resp);
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
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("");
			requestDispatcher.forward(req, resp);
		}
	}
}
