<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
  <div class="container">
    <div class="well">
      <div>
        <h1>Account Verification</h1>
        <c:if test="${errorMessage != null}">
          <div class="alert alert-error">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <span id="error_message">${errorMessage}</span>
          </div>
        </c:if>
        <p>Please enter your verification code from:<p>
        <ul>
          <c:if test="${user != null && user.totpEnabledViaSms == true}">
            <li>The SMS that was just sent to you</li>
          </c:if>
          <c:if test="${user != null && user.totpEnabledViaApp == true}">
            <li>Google Authenticator</li>
          </c:if>
        </ul>
      </div>
      <form method="POST">
        <fieldset>
          <label>Enter your verification code here:</label>
          <input type="text" name="token" placeholder="123456"/>
          <br/>
          <button type="submit" class="btn">Submit</button>
        </fieldset>
      </form>
      <div>
        <p>
        ${user.username}
          <c:if test="${user != null && user.totpEnabledViaSms == true}">
            <a id="verify-tfa-link" href="/verify-tfa/">
              Send me an SMS with my verification code again.
            </a>
          </c:if>
        </p>
      </div>
    </div>
  </div>
</t:layout>
