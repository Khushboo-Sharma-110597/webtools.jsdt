// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input;

import java.util.EnumSet;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.EnumValueCondition;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOverrideField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtype;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtypeCasting;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtypeConditionCustom;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType;

/**
 * A generic type for all command responses. There are 2 subtypes; one for
 * success responses and one for failure responses.
 */
@JsonType
public interface CommandResponse extends JsonSubtype<IncomingMessage> {

  @JsonOverrideField
  @JsonSubtypeConditionCustom(condition=TypeValueCondition.class)
  MessageType type();

  class TypeValueCondition extends EnumValueCondition<MessageType> {
    public TypeValueCondition() {
      super(EnumSet.of(MessageType.RESPONSE));
    }
  }

  /**
   * Id of the corresponding request sent to debugger.
   */
  @JsonField(jsonLiteralName="request_seq")
  long requestSeq();

  @JsonOptionalField
  String command();

  boolean success();

  @JsonSubtypeCasting
  SuccessCommandResponse asSuccess();

  @JsonSubtypeCasting
  FailedCommandResponse asFailure();
}
