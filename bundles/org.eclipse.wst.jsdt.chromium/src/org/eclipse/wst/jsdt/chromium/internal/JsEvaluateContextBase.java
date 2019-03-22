// Copyright (c) 2010 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal;

import java.util.Map;

import org.eclipse.wst.jsdt.chromium.CallbackSemaphore;
import org.eclipse.wst.jsdt.chromium.JsEvaluateContext;
import org.eclipse.wst.jsdt.chromium.JsValue;
import org.eclipse.wst.jsdt.chromium.RelayOk;
import org.eclipse.wst.jsdt.chromium.SyncCallback;
import org.eclipse.wst.jsdt.chromium.util.MethodIsBlockingException;

/**
 * Partial implementation of {@link JsEvaluateContext} that reduce all functionality
 * to single abstract method.
 */
public abstract class JsEvaluateContextBase implements JsEvaluateContext {
  @Override
  public void evaluateSync(String expression, Map<String, ? extends JsValue> additionalContext,
      EvaluateCallback evaluateCallback)
      throws MethodIsBlockingException {
    CallbackSemaphore callbackSemaphore = new CallbackSemaphore();
    RelayOk relayOk =
        evaluateAsync(expression, additionalContext, evaluateCallback, callbackSemaphore);
    boolean res = callbackSemaphore.tryAcquireDefault(relayOk);
    if (!res) {
      evaluateCallback.failure(new Exception("Timeout"));
    }
  }

  @Override
  public abstract RelayOk evaluateAsync(String expression,
      Map<String, ? extends JsValue> additionalContext,
      EvaluateCallback callback, SyncCallback syncCallback);
}
