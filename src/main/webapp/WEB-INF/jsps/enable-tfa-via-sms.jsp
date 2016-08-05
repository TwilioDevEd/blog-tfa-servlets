<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
  <div class=".container">
    <div>
      <h1>Enable SMS based Two-Factor Auth</h1>
      <c:if test="${errorMessage != null}">
        <div class="alert alert-error">
          <button class="close" type="button" data-dismiss="alert">&times;</button>
          <span id="error_message">${errorMessage}</span>
        </div>
      </c:if>
      <c:if test="${successMessage != null}">
        <div class="alert alert-success">
          <button class="close" type="button" data-dismiss="alert">&times;</button>
          <span id="success_message">${successMessage}</span>
        </div>
      </c:if>
      <c:choose>
        <c:when test="${user.totpEnabledViaSms == true}">
          <div class="alert alert-success">
            <button class="close" type="button" data-dismiss="alert">&times;</button>
            <span id="toptp_enabled_via_sms_message">
              You are set up for Two-Factor Authentication via Twilio SMS!
            </span>
          </div>
        </c:when>
        <c:otherwise>
          <div>
            Enabling SMS based Two-Factor Authentication on your account is a two step process:
            <ol>
              <li>Enter your mobile phone number below and press the "Submit" button:</li>
              <li>
                A 6-digit verification code will be sent to your mobile phone. When you receive that number, enter it below:
              </li>
            </ol>
          </div>
          <form method="POST">
            <formset>
              <label>Your mobile phone number:</label>
              <input type="text" name="phoneNumber" placeholder="(415) 555-1212" />
              <button class="btn" type="submit">Send verification code</button>
              <a class="btn" href="/user">Cancel</a>
              <label>Enter your verification code:</label>
              <input type="text" name="token" placeholder="123456" />
              <button class=".btn" type="submit">Submit and verify</button>
              <a class="btn" href="/user">Cancel</a>
            </formset>
          </form>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</t:layout>
