// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.wip;

/**
 * Abstract interface to WIP implementation, that is delivered in a separate library.
 * It allows to have several versions of implementation in the system at the same time,
 * which may be needed because WIP is not stable yet and is evolving quite rapidly.
 * <p>
 * A particular set-up should choose it's own way to get backed instances. For example
 * Eclipse may use its extension point mechanism. Other frameworks can dynamically instantiate
 * {@link Factory}.
 */
public interface WipBackend {

  /**
   * @return a unique name of backend implementation
   */
  String getId();

  /**
   * @return a human-readable implementation description that should help to tell what protocol
   *     version it supports
   */
  String getDescription();

  /**
   * Used to dynamically instantiate {@link WipBackend}. Instance of {@link Factory} should be
   * available via {@link ClassLoader}.
   */
  interface Factory {
    WipBackend create();
  }
}
