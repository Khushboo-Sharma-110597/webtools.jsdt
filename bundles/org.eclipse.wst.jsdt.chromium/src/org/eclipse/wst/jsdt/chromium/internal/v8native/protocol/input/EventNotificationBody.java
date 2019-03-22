// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtypeCasting;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType;

/**
 * This is empty base type for all event notification body types. The actual type
 * depends on a particular event.
 */
@JsonType(subtypesChosenManually=true)
public interface EventNotificationBody {
  @JsonSubtypeCasting
  BreakEventBody asBreakEventBody() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  AfterCompileBody asAfterCompileBody() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  ScriptCollectedBody asScriptCollectedBody() throws JsonProtocolParseException;
}
