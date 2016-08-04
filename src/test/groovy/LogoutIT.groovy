import geb.spock.GebReportingSpec

class LogoutIT extends GebReportingSpec {

  private static String baseURI

  void setupSpec() {
    baseURI = System.getProperty('gretty.baseURI')
  }

  void setup() {
    go "${baseURI}/test/set-up/"
  }

  def 'log out'() {
    given:
    go "${baseURI}"
    $('form').username = 'user'
    $('form').password = 'password'
    $('form button[type=submit]').click()
    when:
    $('#logout').click();
    then:
    $('#logout').text() == null;
    $('.well div h1').text() == 'Don\'t have an account?'
  }

}
