// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal;

import org.eclipse.wst.jsdt.chromium.Breakpoint.Target;
import org.eclipse.wst.jsdt.chromium.BreakpointTypeExtension;
import org.eclipse.wst.jsdt.chromium.JavascriptVm;

/**
 * Common implementation of script regexp breakpoint target. User cannot access this class,
 * instead {@link JavascriptVm#getBreakpointTypeExtension()} provides the constructor
 * (or null if this target type is not supported).
 */
public class ScriptRegExpBreakpointTarget extends Target {
  private final String regExp;

  public ScriptRegExpBreakpointTarget(String regExp) {
    this.regExp = regExp;
  }

  @Override
  public <R> R accept(Visitor<R> visitor) {
    if (visitor instanceof BreakpointTypeExtension.ScriptRegExpSupport.Visitor) {
      BreakpointTypeExtension.ScriptRegExpSupport.Visitor<R> regExpVisitor =
          (BreakpointTypeExtension.ScriptRegExpSupport.Visitor<R>) visitor;
      return regExpVisitor.visitRegExp(regExp);
    } else {
      return visitor.visitUnknown(this);
    }
  }
}