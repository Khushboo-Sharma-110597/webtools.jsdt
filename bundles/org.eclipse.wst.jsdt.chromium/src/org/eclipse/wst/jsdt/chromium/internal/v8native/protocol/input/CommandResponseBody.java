// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input;

import java.util.List;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtypeCasting;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.ScriptHandle;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.ValueHandle;
import org.json.simple.JSONObject;

/**
 * This is empty base type for all command response body types. The actual type
 * depends on a particular command. Note that in JSON sometimes it is an array rather than object
 * (for scripts).
 */
@JsonType(subtypesChosenManually=true)
public interface CommandResponseBody {
  @JsonSubtypeCasting
  BacktraceCommandBody asBacktraceCommandBody() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  List<ScriptHandle> asScripts() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  BreakpointBody asBreakpointBody() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  // map refId -> ValueHandle
  JSONObject asLookupMap() throws JsonProtocolParseException;

  @JsonSubtypeCasting(reinterpret=true)
  ValueHandle asEvaluateBody() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  ScopeBody asScopeBody() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  VersionBody asVersionBody() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  ChangeLiveBody asChangeLiveBody() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  RestartFrameBody asRestartFrameBody() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  ListBreakpointsBody asListBreakpointsBody() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  FlagsBody asFlagsBody() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  SetVariableValueBody asSetVariableValueBody() throws JsonProtocolParseException;
}
