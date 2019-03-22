// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium;

/**
 * A JavaScript exception data holder for exceptions reported by a JavaScript
 * virtual machine.
 */
public interface ExceptionData {

  /**
   * @return the thrown exception value
   */
  JsValue getExceptionValue();

  /**
   * @return whether this exception is uncaught
   */
  boolean isUncaught();

  /**
   * @return the text of the source line where the exception was thrown or null
   */
  String getSourceText();

  /**
   * @return the exception description (plain text)
   */
  String getExceptionMessage();
}
