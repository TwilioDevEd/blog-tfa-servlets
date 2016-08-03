package com.twilio.blogtfa.application.servlets;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.domain.exceptions.DomainException;
import com.twilio.blogtfa.domain.services.LogIn;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class MainPageServlet extends HttpServlet {

  private LogIn logIn;

  @Inject
  public MainPageServlet(LogIn logIn) {
    this.logIn = logIn;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

    String username = req.getParameter("username");
    String password = req.getParameter("password");

    try {
      req.getSession().setAttribute("user", logIn.exec(username, password));
      resp.sendRedirect("/user/");
    } catch (DomainException e) {
      req.setAttribute("errorMessage", e.getMessage());
      req.getRequestDispatcher("/WEB-INF/jsps/index.jsp").forward(req, resp);
    }
  }
}
