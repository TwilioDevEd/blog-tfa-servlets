package com.twilio.blogtfa.application.servlets;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.application.util.ServletUtil;
import com.twilio.blogtfa.domain.exceptions.DomainException;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.services.LogIn;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class IndexServlet extends HttpServlet {

  private LogIn logIn;

  @Inject
  public IndexServlet(LogIn logIn) {
    this.logIn = logIn;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

    req.getRequestDispatcher("/WEB-INF/jsps/index.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

    String username = req.getParameter("username");
    String password = req.getParameter("password");

    try {
      User user = logIn.exec(username, password);
      if (user.isTFAEnabled()) {
        req.getSession().setAttribute("username", username);
        req.getSession().setAttribute("stage", "password-validated");
        resp.sendRedirect("/verify-tfa/");
      } else {
        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/user/");
      }

    } catch (DomainException e) {
      ServletUtil.handleException(e, req, resp, "/WEB-INF/jsps/index.jsp");
    }
  }

}
