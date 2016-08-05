import geb.spock.GebReportingSpec
import org.jboss.aerogear.security.otp.Totp

class EnableTFAViaSmsIT extends GebReportingSpec {

  private static String baseURI

  void setupSpec() {
    baseURI = System.getProperty('gretty.baseURI')
  }

  void setup() {
    go "${baseURI}/test/set-up/"
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

//  @Ignore
  def 'enable tfa via sms'() {
    given:
    go "${baseURI}"
    $('form').username = 'user'
    $('form').password = 'password'
    $('form button[type=submit]').click()
    and:
    go "${baseURI}/enable-tfa-via-app/"
    $('form').token = new Totp("R6LPJTVQXJFRYNDJ").now()
    when:
    $('form button[type=submit]').click()
    then:
    $('#error_message').text() == null
    $('#toptp_enabled_via_sms_message') == 'You are set up for Two-Factor Authentication via Twilio SMS!'
  }
}
