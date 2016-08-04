<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
  <div class="container">
    <div>
      <h1>Enable Google Authenticator</h1>
    </div>
    <c:if test="${errorMessage != null}">
      <div class="alert alert-error">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <span id="error_message">${errorMessage}</span>
      </div>
    </c:if>
    <c:choose>
      <c:when test="${user != null && user.totpEnabledViaApp == true}">
        <div class="alert alert-success">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          <span id="you_are_set">You are set up for Two-Factor Authentication via Google Authenticator!</span>
        </div>
      </c:when>
      <c:otherwise>
        <div>
          <ol>
            <li>
              <a id="install" href="http://support.google.com/accounts/bin/answer.py?hl=en&amp;answer=1066447">
                Install Google Authenticator
              </a>
              on your phone
            </li>
            <li>Open the Google Authenticator app.</li>
            <li>Tap menu, then tap "Set up account", then tap "Scan a barcode".</li>
            <li>Your phone will now be in a "scanning" mode. When you are in this mode, scan the barcode below:</li>
          </ol>
        </div>
        <img src="/auth-qr-code.png" style="width: 300px; height: 300px;">
        <div>
          <p>Once you have scanned the barcode, enter the 6-digit code below:</p>
        </div>
        <form method="POST">
          <fieldset>
            <label>Verification code</label>
            <input type="text" name="token" placeholder="123456"/>
            <br/>
            <button type="submit" class="btn">Submit</button>
            <a href="/user" class="btn">Cancel</a>
          </fieldset>
        </form>
      </c:otherwise>
    </c:choose>
  </div>
</t:layout>
