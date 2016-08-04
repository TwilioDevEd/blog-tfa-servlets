import geb.spock.GebReportingSpec

class VerifyTFAIT extends GebReportingSpec {

  private static String baseURI

  void setupSpec() {
    baseURI = System.getProperty('gretty.baseURI')
  }

  def 'sign in with sms enabled'() {
    given:
    go "${baseURI}"
    $('form').username = 'user.app_no.sms_yes'
    $('form').password = 'password'
    when:
    $('form button[type=submit]').click()
    then:
    $('#error_message').text() == null;
    $('#verify-tfa-link').text() == 'Send me an SMS with my verification code again.';
  }

}
