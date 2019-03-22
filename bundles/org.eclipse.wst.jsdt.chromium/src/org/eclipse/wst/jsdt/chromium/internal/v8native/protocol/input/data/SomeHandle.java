// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtype;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtypeCasting;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtypeCondition;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.FrameObject;

/**
 * A serialized form of object when it is fully (though shallowly) described. Object always
 * has a type and a handle. (See {@link FrameObject} as a case that makes it a bit more messy).
 * <p>Gets serialized in mirror-delay.js,
 * JSONProtocolSerializer.prototype.serialize_, main part.
 */
@JsonType(subtypesChosenManually=true)
public interface SomeHandle extends JsonSubtype<SomeSerialized> {
  /**
   * An integer "handle" of the object. Normally it is unique (for particular suspended-to-resumed
   * period). Some auxiliary objects may have non-unique handles which should be negative.
   */
  @JsonSubtypeCondition
  long handle();

  String type();


  @JsonSubtypeCasting
  ScriptHandle asScriptHandle() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  ValueHandle asValueHandle() throws JsonProtocolParseException;

  @JsonSubtypeCasting
  ContextHandle asContextHandle() throws JsonProtocolParseException;
}
