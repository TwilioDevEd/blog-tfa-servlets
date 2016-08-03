import geb.spock.GebReportingSpec

class IndexIT extends GebReportingSpec {

  private static String baseURI

  void setupSpec() {
    baseURI = System.getProperty('gretty.baseURI')
  }

  def 'get default route'() {
    when:
    go "${baseURI}"
    then:
    $('.well div h1').text() == 'Don\'t have an account?'
  }

}