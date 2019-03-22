// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.protocolparser.dynamicimpl;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.dynamicimpl.JavaCodeGenerator.FileScope;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.dynamicimpl.JavaCodeGenerator.MethodScope;

/**
 * A parser that accepts value of JSON field and outputs value in another form (e.g. string
 * is converted to enum constant) to serve field getters in JsonType interfaces.
 * <p>
 * First the input value should be processed by {@link #parseValue(Object, ObjectData)} method
 * that returns intermediate value (that may be stored in {@link ObjectData#getFieldArray()} array).
 * Then the output value may be obtained via value post-processor, available
 * from {@link #getValueFinisher()} (which is null in most cases, but not always).
 * <p>The parser's name "slow" reads "may be slow". It means that parser may do heavy operations.
 * Alternatively parser may be (optionally) castable to {@link QuickParser}
 * via {@link #asQuickParser()} method.
 */
abstract class SlowParser<T> {
  abstract T parseValue(Object value, ObjectData thisData) throws JsonProtocolParseException;

  abstract FieldLoadedFinisher getValueFinisher();
  abstract JsonTypeParser<?> asJsonTypeParser();

  QuickParser<T> asQuickParser() {
    return null;
  }

  abstract void appendFinishedValueTypeNameJava(FileScope scope);

  abstract void appendInternalValueTypeNameJava(FileScope scope);

  abstract void writeParseCode(MethodScope methodScope, String valueRef, String superValueRef,
      String resultRef);

  abstract boolean javaCodeThrowsException();
}
