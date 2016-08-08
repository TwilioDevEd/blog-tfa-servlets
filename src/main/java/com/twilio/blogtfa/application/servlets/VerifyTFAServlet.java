package com.twilio.blogtfa.application.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.application.util.ServletUtil;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;
import com.twilio.blogtfa.domain.services.VerifyToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Singleton
public class VerifyTFAServlet extends HttpServlet {

  private UserRepository userRepository;
  private VerifyToken verifyToken;

  @Inject
  public VerifyTFAServlet(UserRepository userRepository, VerifyToken verifyToken) {
    this.userRepository = userRepository;
    this.verifyToken = verifyToken;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

    String username = (String) req.getSession().getAttribute("username");
    req.setAttribute("user", userRepository.findByUsername(username).get());
    req.getRequestDispatcher("/WEB-INF/jsps/verify-tfa.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

    String username = (String) req.getSession().getAttribute("username");
    try {
      if (isEmpty(username)) {
        req.setAttribute("errorMessage", "Error - no username");
      } else if (!"password-validated".equals(req.getSession().getAttribute("stage"))) {
        req.setAttribute("errorMessage", "Password is not validated");
      } else {
        User user = userRepository.findByUsername(username).get();
        req.setAttribute("user", user);
        String token = req.getParameter("token");
        verifyToken.exec(user, token);
        req.getSession().setAttribute("user", user);
        req.getSession().setAttribute("stage", "logged-in");
        resp.sendRedirect("/user/");
      }
    } catch (Exception e) {
      ServletUtil.handleException(e, req, resp, "/WEB-INF/jsps/verify-tfa.jsp");
    }
  }

}
