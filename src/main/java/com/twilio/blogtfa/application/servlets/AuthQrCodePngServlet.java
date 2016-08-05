package com.twilio.blogtfa.application.servlets;

import com.google.inject.Singleton;
import com.twilio.blogtfa.domain.models.User;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.jboss.aerogear.security.otp.Totp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Singleton
public class AuthQrCodePngServlet extends HttpServlet {

  static final Logger LOGGER = LoggerFactory.getLogger(AuthQrCodePngServlet.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    User user = (User) req.getSession().getAttribute("user");
    String otpauthUrl = new Totp(user.getTotpSecret()).uri(user.getUsername());
    LOGGER.info("otpauthUrl is {}", otpauthUrl);

    ByteArrayOutputStream out = QRCode.from(otpauthUrl)
      .withSize(300, 300).to(ImageType.PNG).stream();

    resp.setContentType("image/png");
    resp.setContentLength(out.size());
    OutputStream os = resp.getOutputStream();
    os.write(out.toByteArray());
    os.flush();
    os.close();
  }
}

