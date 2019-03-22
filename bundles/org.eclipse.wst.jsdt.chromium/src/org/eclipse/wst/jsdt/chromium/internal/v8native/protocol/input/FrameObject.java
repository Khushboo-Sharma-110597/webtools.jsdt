// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input;

import java.util.List;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.FieldLoadStrategy;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.PropertyObject;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.SomeHandle;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.SomeRef;
import org.json.simple.JSONObject;

/**
 * A frame mirror object type. Technically it is almost subtype of {@link SomeHandle}:
 * it gets serialized from the same code; however, it never gets handle field so
 * we have to treat it as a separate type. Hopefully frame object will never
 * get mixed with other objects on remote side.
 */
@JsonType
public interface FrameObject {

  long index();

  JSONObject func();

  String text();

  long line();

  String sourceLineText();

  @JsonOptionalField
  SomeRef script();

  @JsonField(loadStrategy=FieldLoadStrategy.LAZY)
  List<PropertyObject> arguments();

  @JsonField(loadStrategy=FieldLoadStrategy.LAZY)
  List<PropertyObject> locals();

  @JsonField(loadStrategy=FieldLoadStrategy.LAZY)
  SomeRef receiver();

  @JsonField(loadStrategy=FieldLoadStrategy.LAZY)
  List<ScopeRef> scopes();

  Boolean constructCall();

  String type();

  Long position();

  Long column();

  Boolean debuggerFrame();
}
