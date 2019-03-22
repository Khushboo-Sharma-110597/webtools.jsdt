// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.output;

import org.eclipse.wst.jsdt.chromium.internal.v8native.DebuggerCommand;

/**
 * Represents a "backtrace" V8 request message.
 */
public class BacktraceMessage extends DebuggerMessage {

  /**
   * @param fromFrame nullable frame range start (0 by default)
   * @param toFrame nullable frame range end (last frame by default)
   * @param inlineRefs whether to inline object refs
   */
  public BacktraceMessage(Integer fromFrame, Integer toFrame, boolean inlineRefs) {
    super(DebuggerCommand.BACKTRACE.value);
    putArgument("fromFrame", fromFrame);
    putArgument("toFrame", toFrame);
    if (inlineRefs) {
      putArgument("inlineRefs", inlineRefs);
    }
  }
}
