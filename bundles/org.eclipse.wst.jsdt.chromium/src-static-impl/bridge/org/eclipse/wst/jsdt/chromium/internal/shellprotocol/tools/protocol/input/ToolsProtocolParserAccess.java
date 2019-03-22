// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.shellprotocol.tools.protocol.input;

/**
 * An accessor to generated implementation of DevTools protocol parser.
 */
public class ToolsProtocolParserAccess {
  public static ToolsProtocolParser get() {
    return INSTANCE;
  }

  private static final GeneratedToolsProtocolParser INSTANCE = new GeneratedToolsProtocolParser();
}
