// Copyright (c) 2010 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input;

import org.eclipse.wst.jsdt.chromium.internal.liveeditprotocol.LiveEditResult;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.EnumValueCondition;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonNullable;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOverrideField;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtype;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtypeConditionCustom;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType;

@JsonType
public interface ChangeLiveBody extends JsonSubtype<CommandResponseBody>  {
  @JsonField(jsonLiteralName="change_log")
  Object getChangeLog();

  @JsonNullable
  @JsonField(jsonLiteralName="result")
  LiveEditResult getResultDescription();

  @JsonOptionalField
  Boolean stepin_recommended();

  @JsonType
  interface CompileErrorDetails extends JsonSubtype<FailedCommandResponse.ErrorDetails> {
    @JsonOverrideField
    @JsonSubtypeConditionCustom(condition=TypeCondition.class)
    FailedCommandResponse.ErrorDetails.Type type();

    String syntaxErrorMessage();

    @JsonOptionalField
    PositionRange position();

    @JsonType
    interface PositionRange {
      Position start();
      Position end();
    }

    @JsonType
    interface Position {
      // Offset text character sequence.
      long position();

      long line();
      long column();
    }


    class TypeCondition extends EnumValueCondition<FailedCommandResponse.ErrorDetails.Type> {
      public TypeCondition() {
        super(FailedCommandResponse.ErrorDetails.Type.LIVEEDIT_COMPILE_ERROR);
      }
    }
  }
}
