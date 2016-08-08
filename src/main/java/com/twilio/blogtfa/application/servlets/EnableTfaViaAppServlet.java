package com.twilio.blogtfa.application.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.application.util.ServletUtil;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.services.EnableTfaViaApp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class EnableTfaViaAppServlet extends HttpServlet {

  private EnableTfaViaApp enableTfaViaApp;

  @Inject
  public EnableTfaViaAppServlet(EnableTfaViaApp enableTfaViaApp) {
    this.enableTfaViaApp = enableTfaViaApp;
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
      req.getSession().setAttribute("user", enableTfaViaApp.exec(user, token));
      req.getRequestDispatcher("/WEB-INF/jsps/enable-tfa-via-app.jsp").forward(req, resp);
    } catch (Exception e) {
      ServletUtil.handleException(e, req, resp, "/WEB-INF/jsps/enable-tfa-via-app.jsp");
    }
  }

}
