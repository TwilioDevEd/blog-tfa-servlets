import geb.spock.GebReportingSpec

class SignInIT extends GebReportingSpec {

  private static String baseURI

  void setupSpec() {
    baseURI = System.getProperty('gretty.baseURI')
  }

  void setup() {
    go "${baseURI}/test/set-up/"
  }

  def 'sign in with not existent username'() {
    given:
    go "${baseURI}"

    when:
    $('form button[type=submit]').click()

    then:
    $('#error_message').text() == 'Incorrect Username or Password'
  }

  def 'sign in with bad password'() {
    given:
    go "${baseURI}"
    $('form').username = 'user'
    $('form').password = 'badpassword'

    when:
    $('form button[type=submit]').click()

    then:
    $('#error_message').text() == 'Incorrect Username or Password'
  }

  def 'sign in with correct user and password'() {
    given:
    go "${baseURI}"
    $('form').username = 'user'
    $('form').password = 'password'

    when:
    $('form button[type=submit]').click()

    then:
    $('#error_message').text() == null
    $('#logged_in').text() == 'You are logged in.'
  }

  def 'sign in with correct user (case insensitive) and password'() {
    given:
    go "${baseURI}"
    $('form').username = 'uSeR'
    $('form').password = 'password'

    when:
    $('form button[type=submit]').click()

    then:
    $('#error_message').text() == null
    $('#logged_in').text() == 'You are logged in.'
  }

}
