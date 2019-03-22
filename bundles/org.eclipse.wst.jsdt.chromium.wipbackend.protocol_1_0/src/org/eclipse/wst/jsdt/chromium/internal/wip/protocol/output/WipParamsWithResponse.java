// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException;
import org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipGeneratedParserRoot;
import org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipCommandResponse;

/**
 * A base class for all method parameter classes that implies non-empty responses.
 * @param <R> a type of the corresponding response
 */
public abstract class WipParamsWithResponse<R> extends WipParams {
  public abstract R parseResponse(WipCommandResponse.Data success, WipGeneratedParserRoot parser)
      throws JsonProtocolParseException;
}
