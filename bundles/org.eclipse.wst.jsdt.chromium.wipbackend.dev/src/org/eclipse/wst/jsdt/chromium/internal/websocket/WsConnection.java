// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.websocket;

import java.io.IOException;

import org.eclipse.wst.jsdt.chromium.RelayOk;
import org.eclipse.wst.jsdt.chromium.SyncCallback;
import org.eclipse.wst.jsdt.chromium.util.SignalRelay;

/**
 * Abstract interface to WebSocket implementation that hides a particular specification
 * version.
 */
public interface WsConnection {

  void startListening(Listener listener);

  void sendTextualMessage(String message) throws IOException;

  RelayOk runInDispatchThread(Runnable runnable, SyncCallback syncCallback);

  SignalRelay<?> getCloser();

  interface Listener {
    void textMessageRecieved(String text);

    /**
     * Some non-fatal error happened.
     */
    void errorMessage(Exception ex);

    /**
     * Connection has been closed. Message is called from Dispatch thread.
     */
    void eofMessage();
  }
}