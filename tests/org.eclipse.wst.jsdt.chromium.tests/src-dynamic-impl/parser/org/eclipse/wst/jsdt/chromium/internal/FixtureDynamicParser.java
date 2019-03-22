// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal;

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.wst.jsdt.chromium.internal.browserfixture.FixtureChromeStub.FixtureParser;
import org.eclipse.wst.jsdt.chromium.internal.browserfixture.FixtureChromeStub.Refs;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolModelParseException;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.dynamicimpl.DynamicParserImpl;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.V8DynamicParser;

/**
 * A dynamic implementation of a fixture parser.
 */
public class FixtureDynamicParser {
  public static DynamicParserImpl<FixtureParser> create() {
    try {
      return new DynamicParserImpl<FixtureParser>(FixtureParser.class, Arrays.asList(Refs.class),
          Collections.singletonList(V8DynamicParser.create()));
    } catch (JsonProtocolModelParseException e) {
      throw new RuntimeException(e);
    }
  }
}
