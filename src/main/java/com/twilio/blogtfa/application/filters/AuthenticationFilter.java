package com.twilio.blogtfa.application.filters;

import com.google.inject.Singleton;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class AuthenticationFilter implements Filter {

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response,
                       final FilterChain chain) throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    if (!isAuthenticated(httpRequest)) {
      httpResponse.sendRedirect("/");
    } else {
      chain.doFilter(request, response);
    }
  }

  private boolean isAuthenticated(final HttpServletRequest request) {
    return request.getSession().getAttribute("user") != null;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException { }

  @Override
  public void destroy() { }

}
