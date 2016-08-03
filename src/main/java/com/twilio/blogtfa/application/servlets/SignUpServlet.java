package com.twilio.blogtfa.application.servlets;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.domain.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class SignUpServlet  extends HttpServlet {

  private UserRepository userRepository;

  @Inject
  public SignUpServlet(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/sign-up.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    String username = req.getParameter("username");
    String password1 = req.getParameter("password1");
    String password2 = req.getParameter("password2");

    if (password1 != null && !password1.equals(password2)) {
      req.setAttribute("errorMessage", "Passwords do not match.");
    }
    req.getRequestDispatcher("/WEB-INF/sign-up.jsp").forward(req, resp);
  }

}
