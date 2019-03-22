// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtypeCasting;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType;


/**
 * A serialized form of object. There may be 2 schemas: reference (like pointer) or full description
 * called "a handle" hereafter. It appears that it's not always statically known which of schemas
 * is used in every place; thus it requires a base type like this.
 * <p>Gets serialized in mirror-delay.js,
 * JSONProtocolSerializer.prototype.serialize_
 */
@JsonType
public interface SomeSerialized {
  @JsonSubtypeCasting
  SomeRef asSomeRef();

  @JsonSubtypeCasting
  SomeHandle asSmthWithHandle();
}
