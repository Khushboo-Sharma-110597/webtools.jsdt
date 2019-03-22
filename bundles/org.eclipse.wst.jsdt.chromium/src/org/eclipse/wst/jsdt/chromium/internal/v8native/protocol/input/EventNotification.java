// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input;

import java.util.EnumSet;
import java.util.List;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.EnumValueCondition;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonObjectBased;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOverrideField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtype;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtypeConditionCustom;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.SomeHandle;
import org.json.simple.JSONObject;

/**
 * A type for event notification message. Its structure is similar
 * to {@link SuccessCommandResponse}.
 */
@JsonType
public interface EventNotification extends JsonObjectBased, JsonSubtype<IncomingMessage> {
  @JsonOverrideField
  @JsonSubtypeConditionCustom(condition=TypeValueCondition.class)
  MessageType type();

  class TypeValueCondition extends EnumValueCondition<MessageType> {
    public TypeValueCondition() {
      super(EnumSet.of(MessageType.EVENT));
    }
  }

  String event();

  EventNotificationBody body();

  // TODO(peter.rybin): does this field really exist?
  @JsonOptionalField
  JSONObject exception();

  @JsonOptionalField
  List<SomeHandle> refs();
}
