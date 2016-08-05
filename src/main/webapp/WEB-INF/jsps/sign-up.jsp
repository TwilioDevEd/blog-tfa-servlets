<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
  <div class="container">
    <div>
      <h1 id="create_account">Create an account</h1>
      <p>Remember, this is just for demonstration purposes. Don't use a password here that you use anywhere else.</p>
    </div>
    <c:if test="${errorMessage != null}">
      <div class="alert alert-error">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <span id="error_message">${errorMessage}</span>
      </div>
    </c:if>
    <form method="POST">
      <fieldset>
        <label>Username</label>
        <input type="text" name="username" />
        <label>Password</label>
        <input type="password" name="password1" />
        <label>Password again</label>
        <input type="password" name="password2" />
        <br/>
        <button type="submit" class="btn">Create Account</button>
      </fieldset>
    </form>
  </div>
</t:layout>
