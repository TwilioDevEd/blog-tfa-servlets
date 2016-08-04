import geb.spock.GebReportingSpec

class SignUpIT extends GebReportingSpec {

  private static String baseURI

  void setupSpec() {
    baseURI = System.getProperty('gretty.baseURI')
    go "${baseURI}/test/set-up/"
  }

  def 'get sign up page'() {
    when:
    go "${baseURI}/sign-up/"
    then:
    $('#create_account').text() == 'Create an account'
  }

  def 'sign up with passwords not matching'() {
    given:
    go "${baseURI}/sign-up/"
    $('form').username = 'user'
    $('form').password1 = 'password1'
    $('form').password2 = 'password2'
    when:
    $('form button[type=submit]').click()
    then:
    $('#error_message').text() == 'Passwords do not match.'
  }

  def 'sign up with username that already exists'() {
    given:
    go "${baseURI}/sign-up/"
    $('form').username = 'user'
    $('form').password1 = 'password2'
    $('form').password2 = 'password2'
    when:
    $('form button[type=submit]').click()
    then:
    $('#error_message').text() == 'That username is already in use'
  }

  def 'sign up with correct username and password'() {
    given:
    go "${baseURI}/sign-up/"
    $('form').username = 'newuser'
    $('form').password1 = 'password'
    $('form').password2 = 'password'
    when:
    $('form button[type=submit]').click()
    then:
    $('#error_message').text() == null
    $('#logged_in').text() == 'You are logged in.'
    $('#logout').text() == 'Log out'
  }

}
