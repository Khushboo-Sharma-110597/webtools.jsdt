// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;

import org.eclipse.wst.jsdt.chromium.Script;
import org.eclipse.wst.jsdt.chromium.JavascriptVm.ScriptsCallback;
import org.eclipse.wst.jsdt.chromium.internal.browserfixture.AbstractAttachedTest;
import org.eclipse.wst.jsdt.chromium.internal.browserfixture.FixtureChromeStub;
import org.eclipse.wst.jsdt.chromium.internal.transport.FakeConnection;
import org.eclipse.wst.jsdt.chromium.util.MethodIsBlockingException;
import org.junit.Test;

/**
 * A test for the scripts processing.
 */
public class ScriptsTest extends AbstractAttachedTest<FakeConnection> {

  @Test
  public void checkAfterCompileScriptIsKnown() throws Exception {
    Collection<Script> scripts = getScripts();
    assertEquals(1, scripts.size());
    final CountDownLatch latch = new CountDownLatch(1);
    scriptLoadedCallback = new Runnable() {
      public void run() {
        latch.countDown();
      }
    };
    messageResponder.sendAfterCompile();
    latch.await();
    assertNotNull(loadedScript);
    Collection<Script> newScripts = getScripts();
    assertEquals(2, newScripts.size());
    newScripts.removeAll(scripts);
    Script compiledScript = newScripts.iterator().next();
    assertEquals(FixtureChromeStub.getCompiledScriptId(), compiledScript.getId());
    assertEquals(loadedScript.getId(), compiledScript.getId());
    String source = compiledScript.getSource();
    assertTrue(source != null && source.contains("compiled()")); //$NON-NLS-1$
  }

  @SuppressWarnings("unchecked")
  private Collection<Script> getScripts() throws MethodIsBlockingException {
    final Collection<Script>[] loadedScripts = new Collection[1];
    javascriptVm.getScripts(new ScriptsCallback() {
      public void success(Collection<Script> scripts) {
        loadedScripts[0] = scripts;
      }

      public void failure(String errorMessage) {
      }
    });
    return loadedScripts[0];
  }

  @Override
  protected FakeConnection createConnection() {
    return new FakeConnection(messageResponder);
  }
}
