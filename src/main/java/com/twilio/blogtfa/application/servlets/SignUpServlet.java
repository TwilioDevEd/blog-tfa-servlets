package com.twilio.blogtfa.application.servlets;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.application.util.ServletUtil;
import com.twilio.blogtfa.domain.services.SignUp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class SignUpServlet extends HttpServlet {

  private SignUp signUp;

  @Inject
  public SignUpServlet(SignUp signUp) {
    this.signUp = signUp;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/jsps/sign-up.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

    String username = req.getParameter("username");
    String password1 = req.getParameter("password1");
    String password2 = req.getParameter("password2");
    try {
      req.getSession().setAttribute("user", signUp.exec(username, password1, password2));
      resp.sendRedirect("/user/");
    } catch (Exception e) {
      ServletUtil.handleException(e, req, resp, "/WEB-INF/jsps/sign-up.jsp");
    }
  }
}
