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

  def 'sign in with not existent username'() {
    given:
      go "${baseURI}"
    when:
      $('form button[type=submit]').click()
    then:
      $('#error_message').text() == 'Incorrect Username or Password';

  }

  def 'sign in with bad password'() {
    given:
      go "${baseURI}"
      $('form').username = 'user'
      $('form').password= 'badpassword'
    when:
      $('form button[type=submit]').click()
    then:
      $('#error_message').text() == 'Incorrect Username or Password';
  }
}
