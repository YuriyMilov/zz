import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class HelloAppEngineTest {

  @Test
  public void test() throws IOException {
    MockHttpServletResponse response = new MockHttpServletResponse();
<<<<<<< HEAD:qdone21/src/test/java/HelloAppEngineTest.java
    new HelloAppEngine().doGet(null, response);
=======
    new rss().doGet(null, response);
>>>>>>> 1edb9d52bcff1235af15ce19e3296c7c3f97b538:qqqzzz/src/test/java/ku/HelloAppEngineTest.java
    Assert.assertEquals("text/plain", response.getContentType());
    Assert.assertEquals("UTF-8", response.getCharacterEncoding());
    Assert.assertEquals("Hello App Engine!\r\n", response.getWriterContent().toString());
  }
}
