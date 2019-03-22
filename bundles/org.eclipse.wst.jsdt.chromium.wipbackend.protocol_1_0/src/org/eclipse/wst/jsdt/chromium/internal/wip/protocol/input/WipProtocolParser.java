// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonParseMethod;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonParserRoot;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException;
import org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.RemoteObjectValue;
import org.json.simple.JSONObject;

/**
 * Interface to WIP protocol parser. It includes generated part {@link WipGeneratedParserRoot}.
 */
@JsonParserRoot
public interface WipProtocolParser extends WipGeneratedParserRoot {

  @JsonParseMethod
  WipCommandResponse parseWipCommandResponse(JSONObject incoming)
      throws JsonProtocolParseException;

  @JsonParseMethod
  WipEvent parseWipEvent(JSONObject jsonObject) throws JsonProtocolParseException;

  @JsonParseMethod
  WipTabList parseTabList(Object jsonValue) throws JsonProtocolParseException;

  // Used by WipContextBuilder because protocol declares exception value as raw object.
  @JsonParseMethod
  RemoteObjectValue parseRemoteObjectValue(Object jsonValue) throws JsonProtocolParseException;

}
