package com.twilio.blogtfa.application.servlets;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Singleton
public class MainPageServlet extends HttpServlet {

  private UserRepository userRepository;

  @Inject
  public MainPageServlet(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    Optional<User> optUser = userRepository.findByUsername(req.getParameter("username"));
    if (!optUser.isPresent()) {
      req.setAttribute("errorMessage", "Incorrect Username or Password");
      req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
    } else {
      User user = optUser.get();
      if (!user.authenticate(req.getParameter("password"))) {
        req.setAttribute("errorMessage", "Incorrect Username or Password");
        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
      } else {
        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/user/");
      }
    }

  }
}
