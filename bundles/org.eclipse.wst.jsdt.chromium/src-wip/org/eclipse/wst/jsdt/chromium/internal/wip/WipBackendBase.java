// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.wip;

import java.io.IOException;
import java.util.List;

import org.eclipse.wst.jsdt.chromium.wip.WipBackend;
import org.eclipse.wst.jsdt.chromium.wip.WipBrowser;

/**
 * An internal interface to {@link WipBackend} implementation.
 */
public abstract class WipBackendBase implements WipBackend {
  private final String id;
  private final String description;

  public WipBackendBase(String id, String description) {
    this.id = id;
    this.description = description;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getDescription() {
    return description;
  }

  static WipBackendBase castArgument(WipBackend wipBackend) {
    try {
      return (WipBackendBase) wipBackend;
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Incorrect backend argument type", e);
    }
  }

  public abstract List<? extends WipBrowser.WipTabConnector> getTabs(
      WipBrowserImpl browserImpl) throws IOException;
}
