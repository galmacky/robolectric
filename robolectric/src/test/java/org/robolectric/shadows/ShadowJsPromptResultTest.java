package org.robolectric.shadows;

import static org.junit.Assert.assertNotNull;

import android.webkit.JsPromptResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.TestRunners;

@RunWith(TestRunners.MultiApiSelfTest.class)
public class ShadowJsPromptResultTest {

  @Test
  public void shouldConstruct() throws Exception {
    JsPromptResult result = ShadowJsPromptResult.newInstance();
    assertNotNull(result);
  }
}
