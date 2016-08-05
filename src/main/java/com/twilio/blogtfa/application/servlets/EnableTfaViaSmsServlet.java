package com.twilio.blogtfa.application.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.blogtfa.application.util.ServletUtil;
import com.twilio.blogtfa.domain.models.User;
import com.twilio.blogtfa.domain.repositories.UserRepository;
import com.twilio.blogtfa.domain.services.SendSms;
import com.twilio.blogtfa.domain.services.ValidateToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Singleton
public class EnableTfaViaSmsServlet extends HttpServlet {

  private static final String SMS_SENT_SUCCESS_MESSAGE = "An SMS has been sent to the " +
    "phone number you entered. When you get the SMS, enter the code in the SMS where " +
    "it says \"Enter your verification code\" below.";

  private UserRepository userRepository;
  private SendSms sendSms;
  private ValidateToken validateToken;

  @Inject
  public EnableTfaViaSmsServlet(UserRepository userRepository, SendSms sendSms, ValidateToken validateToken) {
    this.userRepository = userRepository;
    this.sendSms = sendSms;
    this.validateToken = validateToken;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/jsps/enable-tfa-via-sms.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final String phoneNumber = req.getParameter("phoneNumber");
    final String token = req.getParameter("token");
    System.out.println(format("Params{phoneNumber=%s, token=%s}", phoneNumber, token));

    final User user = (User) req.getSession().getAttribute("user");
    try {
      if (!isEmpty(phoneNumber)) {
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
        sendSms.exec(user);
        req.setAttribute("successMessage", SMS_SENT_SUCCESS_MESSAGE);
      } else {
        validateToken.exec(user, token);
        user.setTotpEnabledViaSms(true);
        req.getSession().setAttribute("user", userRepository.save(user));
      }
      req.getRequestDispatcher("/WEB-INF/jsps/enable-tfa-via-sms.jsp").forward(req, resp);
    } catch (Exception e) {
      ServletUtil.handleException(e, req, resp, "/WEB-INF/jsps/enable-tfa-via-sms.jsp");
    }
  }
}
