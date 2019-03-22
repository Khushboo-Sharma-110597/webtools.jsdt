// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonNullable;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOverrideField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtype;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtypeCondition;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType;

/**
 * A reference (a pointer) to an object, that prefetches some of its key properties.
 * <p>Gets serialized in mirror-delay.js,
 * JSONProtocolSerializer.prototype.serializeReferenceWithDisplayData_
 */
@JsonType
public interface RefWithDisplayData extends JsonSubtype<SomeRef> {

  @JsonOverrideField
  long ref();

  @JsonSubtypeCondition
  String type();

  @JsonOptionalField
  String className();

  @JsonOptionalField
  @JsonNullable
  Object value();


  // For function.
  @JsonOptionalField
  String inferredName();

  @JsonOptionalField
  Long scriptId();
}
