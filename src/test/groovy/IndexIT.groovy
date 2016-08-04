import geb.spock.GebReportingSpec

class IndexIT extends GebReportingSpec {

  private static String baseURI

  void setupSpec() {
    baseURI = System.getProperty('gretty.baseURI')
  }

  void setup() {
    go "${baseURI}/test/set-up/"
  }

  def 'get default route'() {
    when:
    go "${baseURI}"
    then:
    $('.well div h1').text() == 'Don\'t have an account?'
  }

}
