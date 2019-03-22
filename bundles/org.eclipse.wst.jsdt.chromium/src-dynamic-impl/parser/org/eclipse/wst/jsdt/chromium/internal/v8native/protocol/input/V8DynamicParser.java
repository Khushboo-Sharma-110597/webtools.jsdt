// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input;

import java.util.Arrays;
import java.util.List;

import org.eclipse.wst.jsdt.chromium.internal.liveeditprotocol.LiveEditDynamicParser;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolModelParseException;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.dynamicimpl.DynamicParserImpl;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.BreakpointInfo;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.ContextData;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.ContextHandle;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.FunctionValueHandle;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.ObjectValueHandle;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.PropertyObject;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.PropertyWithRef;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.PropertyWithValue;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.RefWithDisplayData;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.ScriptHandle;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.ScriptWithId;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.SomeHandle;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.SomeRef;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.SomeSerialized;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.ValueHandle;

/**
 * A dynamic implementation of a v8 protocol parser.
 */
public class V8DynamicParser {
  public static DynamicParserImpl<V8NativeProtocolParser> create() {
    try {
      List<Class<?>> interfaces = Arrays.asList(
          IncomingMessage.class,
          EventNotification.class,
          SuccessCommandResponse.class,
          FailedCommandResponse.class,
          FailedCommandResponse.ErrorDetails.class,
          CommandResponse.class,
          BreakEventBody.class,
          EventNotificationBody.class,
          CommandResponseBody.class,
          BacktraceCommandBody.class,
          FrameObject.class,
          BreakpointBody.class,
          ScopeBody.class,
          ScopeRef.class,
          VersionBody.class,
          AfterCompileBody.class,
          ChangeLiveBody.class,
          ChangeLiveBody.CompileErrorDetails.class,
          ChangeLiveBody.CompileErrorDetails.PositionRange.class,
          ChangeLiveBody.CompileErrorDetails.Position.class,
          RestartFrameBody.class,
          RestartFrameBody.ResultDescription.class,
          ListBreakpointsBody.class,
          ScriptCollectedBody.class,
          FlagsBody.class,
          FlagsBody.FlagInfo.class,
          SetVariableValueBody.class,

          SomeHandle.class,
          ScriptHandle.class,
          ValueHandle.class,
          RefWithDisplayData.class,
          PropertyObject.class,
          PropertyWithRef.class,
          PropertyWithValue.class,
          ObjectValueHandle.class,
          FunctionValueHandle.class,
          SomeRef.class,
          SomeSerialized.class,
          ContextHandle.class,
          ContextData.class,
          BreakpointInfo.class,
          ScriptWithId.class
          );

      List<DynamicParserImpl<?>> basePackages =
          Arrays.<DynamicParserImpl<?>>asList(LiveEditDynamicParser.create());

      return new DynamicParserImpl<V8NativeProtocolParser>(V8NativeProtocolParser.class,
          interfaces, basePackages, false);
    } catch (JsonProtocolModelParseException e) {
      throw new RuntimeException(e);
    }
  }
}
