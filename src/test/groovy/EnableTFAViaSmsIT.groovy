import geb.spock.GebReportingSpec

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

  def 'enable tfa via sms'() {
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
