// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.output;

import java.util.EnumMap;
import java.util.Map;

import org.eclipse.wst.jsdt.chromium.DebugContext.StepAction;
import org.eclipse.wst.jsdt.chromium.internal.v8native.DebuggerCommand;

/**
 * Represents a "continue" V8 request message.
 */
public class ContinueMessage extends DebuggerMessage {

  private static final Map<StepAction, String> stepActionToV8 =
      new EnumMap<StepAction, String>(StepAction.class);

  static {
    stepActionToV8.put(StepAction.IN, "in");
    stepActionToV8.put(StepAction.OUT, "out");
    stepActionToV8.put(StepAction.OVER, "next");
    stepActionToV8.put(StepAction.CONTINUE, null);
  }

  /**
   * @param stepAction the kind of step to perform
   * @param stepCount nullable number of steps to perform (positive if not null).
   *        Default is 1 step. Not used when {@code stepAction == CONTINUE}
   */
  public ContinueMessage(StepAction stepAction, Integer stepCount) {
    super(DebuggerCommand.CONTINUE.value);
    String stepActionString = stepActionToV8.get(stepAction);
    if (stepActionString != null) {
      putArgument("stepaction", stepActionString);
      putArgument("stepcount", stepCount);
    }
  }
}
