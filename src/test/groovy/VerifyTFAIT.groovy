import geb.spock.GebReportingSpec
import org.jboss.aerogear.security.otp.Totp

class VerifyTFAIT extends GebReportingSpec {

  private static String baseURI

  void setupSpec() {
    baseURI = System.getProperty('gretty.baseURI')
  }

  void setup() {
    go "${baseURI}/test/set-up/"
  }

  def 'sign in with app no sms no'() {
    given:
    go "${baseURI}"
    $('form').username = 'user.app_no.sms_no'
    $('form').password = 'password'
    when:
    $('form button[type=submit]').click()
    then:
    $('#error_message').text() == null
    $('#logged_in').text() == 'You are logged in.'
  }

  def 'sign in with sms app no sms yes'() {
    given:
    go "${baseURI}"
    $('form').username = 'user.app_no.sms_yes'
    $('form').password = 'password'
    when:
    $('form button[type=submit]').click()
    then:
    $('#error_message').text() == null
    $('#send-sms-again-link').text() == 'Send me an SMS with my verification code again.'
    $('#google_authenticator').text() == null
  }

  def 'sign in with sms app yes sms no'() {
    given:
    go "${baseURI}"
    $('form').username = 'user.app_yes.sms_no'
    $('form').password = 'password'
    when:
    $('form button[type=submit]').click()
    then:
    $('#error_message').text() == null
    $('#verify-tfa-link').text() == null
    $('#google_authenticator').text() == 'Google Authenticator'
  }

  def 'open enable tfa via app page'() {
    given:
    go "${baseURI}"
    $('form').username = 'user'
    $('form').password = 'password'
    $('form button[type=submit]').click()
    when:
    go "${baseURI}/enable-tfa-via-app/"
    then:
    $('#error_message').text() == null
    $('#verify-tfa-link').text() == null
    $('#install').text() == 'Install Google Authenticator'
    $('.container div h1').text() == 'Enable Google Authenticator'
    $('#you_are_set').text() == null
  }

  def 'enable tfa via app with correct token'() {
    given:
    go "${baseURI}"
    $('form').username = 'user'
    $('form').password = 'password'
    $('form button[type=submit]').click()

    go "${baseURI}/enable-tfa-via-app/"
    $('form').token = new Totp("R6LPJTVQXJFRYNDJ").now()
    when:
    $('form button[type=submit]').click()
    then:
    $('#error_message').text() == null
    $('#you_are_set').text() == 'You are set up for Two-Factor Authentication via Google Authenticator!'
  }

  def 'enable tfa via app with incorrect token'() {
    given:
    go "${baseURI}"
    $('form').username = 'user'
    $('form').password = 'password'
    $('form button[type=submit]').click()

    go "${baseURI}/enable-tfa-via-app/"
    $('form').token = "-1"
    when:
    $('form button[type=submit]').click()
    then:
    $('#error_message').text() == 'There was an error verifying your token. Please try again.'
    $('#you_are_set').text() == null
  }

  def 'show enable tfa via sms page'() {
    given:
    go "${baseURI}"
    $('form').username = 'user'
    $('form').password = 'password'
    $('form button[type=submit]').click()

    when:
    go "${baseURI}/enable-tfa-via-sms/"
    then:
    $('h1').text() == 'Enable SMS based Two-Factor Auth'
  }

}
