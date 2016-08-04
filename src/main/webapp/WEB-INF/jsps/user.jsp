<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
  <div class="container">
    <div>
      <h1 id="logged_in">You are logged in.</h1>
    </div>
    <div class="row">
      <div class="span6">
        <!-- Source: http://openclipart.org/detail/176289/top-secret-by-joshbressers-176289 -->
        <img src="/static/img/top-secret.png" />
      </div>
      <div class="span6">
        <a class="btn" href="/enable-tfa-via-app/">Enable app based authentication</a>
        <div>
          <p>
            For apps like "Google Authenticator".
            <br/>
            <c:if test="${user != null && user.totpEnabledViaApp == true}">
              (Enabled)
            </c:if>
          </p>
        </div>
        <a class="btn" href="/enable-tfa-via-sms/">Enable SMS based authentication</a>
        <div>
          <p>
            For any phone that can receive SMS messages.
            <br/>
            <c:if test="${user != null && user.totpEnabledViaSms == true}">
              (Enabled for ${user.phoneNumber})
            </c:if>
          </p>
      </div>
    </div>
  </div>
</t:layout>
