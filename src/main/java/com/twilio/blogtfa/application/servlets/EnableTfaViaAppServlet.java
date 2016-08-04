package com.twilio.blogtfa.application.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.application.util.ServletUtil;
import com.twilio.blogtfa.domain.exceptions.DomainException;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;
import com.twilio.blogtfa.domain.services.ValidateToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class EnableTfaViaAppServlet extends HttpServlet {

  private UserRepository userRepository;
  private ValidateToken validateToken;

  @Inject
  public EnableTfaViaAppServlet(UserRepository userRepository, ValidateToken validateToken) {
    this.userRepository = userRepository;
    this.validateToken = validateToken;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/jsps/enable-tfa-via-app.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    String token = req.getParameter("token");
    User user = (User) req.getSession().getAttribute("user");
    try {
      validateToken.exec(user, token);
      user.setTotpEnabledViaApp(true);
      req.getSession().setAttribute("user", userRepository.save(user));
      req.getRequestDispatcher("/WEB-INF/jsps/enable-tfa-via-app.jsp").forward(req, resp);
    } catch (Exception e) {
      ServletUtil.handleException(e, req, resp, "/WEB-INF/jsps/enable-tfa-via-app.jsp");
    }
  }
}
