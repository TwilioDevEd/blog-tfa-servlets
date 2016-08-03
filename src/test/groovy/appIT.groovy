import geb.spock.GebReportingSpec

class AppIT extends GebReportingSpec {

  private static String baseURI

  void setupSpec() {
    baseURI = System.getProperty('gretty.baseURI')
  }

  def 'should get default route'() {
    when:
      go "${baseURI}"
    then:
      $('.well div h1').text() == 'Don\'t have an account?'
  }

  def 'sign in with invalid username or password'() {
    given:
      go "${baseURI}"
    when:
      $('form button[type=submit]').click()
    then:
      $('#error_message').text() == 'Incorrect Username or Password';

  }
}
