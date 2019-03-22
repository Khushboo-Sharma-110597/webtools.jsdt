// Copyright (c) 2012 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium;

import org.eclipse.wst.jsdt.chromium.util.GenericCallback;

/**
 * An extension to {@link CallFrame} API that supports restart frame operation. Some backends
 * or VM versions may not support it.
 * @see JavascriptVm#getRestartFrameExtension()
 */
public interface RestartFrameExtension {
  /**
   * Restarts a frame (all frames above are dropped from the stack, this frame is started over).
   * @param callback is notified about operation outcome; for success the boolean parameter
   *     is true if VM has been resumed and is expected to get suspended again in a moment (with
   *     a standard 'resumed' notification), and is false if callframes list is already updated
   *     without VM state change (this case presently is never actually happening)
   */
  RelayOk restartFrame(CallFrame callFrame,
      GenericCallback<Boolean> callback, SyncCallback syncCallback);

  /**
   * @return whether reset operation is supported for the particular callFrame
   */
  boolean canRestartFrame(CallFrame callFrame);
}
