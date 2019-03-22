// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium;

/**
 * This interface is used by the SDK to report browser-related debug
 * events for a certain tab to the clients.
 */
public interface TabDebugEventListener {
  /**
   * Every {@code TabDebugEventListener} should aggregate
   * {@code DebugEventListener}.
   */
  DebugEventListener getDebugEventListener();

  /**
   * Reports a navigation event on the target tab.
   *
   * @param newUrl the new URL of the debugged tab
   */
  void navigated(String newUrl);

  /**
   * Reports a closing event on the target tab. All following communications
   * with the associated tab are illegal. This call will be followed by
   * call to {@link DebugEventListener#disconnected()}.
   * TODO: consider adding close reason here.
   */
  void closed();
}
