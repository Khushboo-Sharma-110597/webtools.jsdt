// Copyright (c) 2010 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.processor;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException;
import org.eclipse.wst.jsdt.chromium.internal.v8native.DebugSession;
import org.eclipse.wst.jsdt.chromium.internal.v8native.ScriptManager;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.EventNotification;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.EventNotificationBody;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.ScriptCollectedBody;

/**
 * Listens for scripts sent in the "scriptCollected" events and passes their ids to
 * the {@link ScriptManager}.
 */
public class ScriptCollectedProcessor extends V8EventProcessor {

  public ScriptCollectedProcessor(DebugSession debugSession) {
    super(debugSession);
  }

  @Override
  public void messageReceived(EventNotification eventMessage) {
    EventNotificationBody body = eventMessage.body();

    ScriptCollectedBody scriptCollectedBody;
    try {
      scriptCollectedBody = body.asScriptCollectedBody();
    } catch (JsonProtocolParseException e) {
      throw new RuntimeException(e);
    }

    long scriptId = scriptCollectedBody.script().id();

    getDebugSession().getScriptManager().scriptCollected(scriptId);
  }

}
