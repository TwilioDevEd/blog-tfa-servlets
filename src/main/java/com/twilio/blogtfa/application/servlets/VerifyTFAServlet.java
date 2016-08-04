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
public class VerifyTFAServlet extends HttpServlet {

  private UserRepository userRepository;

  @Inject
  public VerifyTFAServlet(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    String username = (String) req.getSession().getAttribute("username");
    req.setAttribute("user", userRepository.findByUsername(username).get());
    req.getRequestDispatcher("/WEB-INF/jsps/verify-tfa.jsp").forward(req, resp);
  }
}
