// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native;

import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.CommandResponse;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.FailedCommandResponse;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.SuccessCommandResponse;

/**
 * A basic implementation of {@link V8CommandProcessor.V8HandlerCallback} that introduces
 * command success and failure handlers and dispatches the V8 response accordingly.
 */
public abstract class V8CommandCallbackBase implements V8CommandProcessor.V8HandlerCallback {
  public abstract void success(SuccessCommandResponse successResponse);

  @Override
  final public void failure(String message) {
    failure(message, null);
  }

  public abstract void failure(String message, FailedCommandResponse.ErrorDetails errorDetails);

  @Override
  public void messageReceived(CommandResponse response) {
    SuccessCommandResponse successResponse = response.asSuccess();
    if (successResponse == null) {
      FailedCommandResponse failure = response.asFailure();
      failure("Remote error: " + failure.message(), failure.errorDetails());
    } else {
      success(successResponse);
    }
  }
}
