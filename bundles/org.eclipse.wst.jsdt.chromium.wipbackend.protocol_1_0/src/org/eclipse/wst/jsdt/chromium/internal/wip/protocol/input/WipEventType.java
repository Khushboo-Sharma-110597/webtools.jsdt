// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException;
import org.json.simple.JSONObject;

/**
 * This class describes event static information that helps to automate response parsing
 * and dispatching.
 */
public abstract class WipEventType<T> {
  private final String methodName;
  private final Class<T> eventType;

  public WipEventType(String methodName, Class<T> eventType) {
    this.methodName = methodName;
    this.eventType = eventType;
  }

  public String getMethodName() {
    return methodName;
  }

  public Class<T> getEventType() {
    return eventType;
  }

  public abstract T parse(WipGeneratedParserRoot parser, JSONObject obj)
      throws JsonProtocolParseException;
}
