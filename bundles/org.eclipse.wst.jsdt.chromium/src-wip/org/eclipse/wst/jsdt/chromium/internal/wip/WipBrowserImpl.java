// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.wip;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import org.eclipse.wst.jsdt.chromium.Browser;
import org.eclipse.wst.jsdt.chromium.wip.WipBackend;
import org.eclipse.wst.jsdt.chromium.wip.WipBrowser;
import org.eclipse.wst.jsdt.chromium.wip.WipBrowserFactory;

/**
 * Implements {@link Browser} API that offers connection to a browser tab
 * via WebInspector 'WIP' Protocol.
 */
public class WipBrowserImpl implements WipBrowser {
  private final InetSocketAddress socketAddress;
  private final WipBrowserFactory.LoggerFactory connectionLoggerFactory;

  public WipBrowserImpl(InetSocketAddress socketAddress,
      WipBrowserFactory.LoggerFactory connectionLoggerFactory) {
    this.socketAddress = socketAddress;
    this.connectionLoggerFactory = connectionLoggerFactory;
  }

  @Override
  public List<? extends WipTabConnector> getTabs(WipBackend wipBackend) throws IOException {
    WipBackendBase backendBase = WipBackendBase.castArgument(wipBackend);
    return backendBase.getTabs(this);
  }

  public InetSocketAddress getSocketAddress() {
    return socketAddress;
  }

  public WipBrowserFactory.LoggerFactory getConnectionLoggerFactory() {
    return connectionLoggerFactory;
  }

  /**
   * A convenience method for any currently unsupported operation. It nicely co-works with
   * a return statements.
   */
  public static <T> T throwUnsupported() {
    throw new UnsupportedOperationException();
  }
}
