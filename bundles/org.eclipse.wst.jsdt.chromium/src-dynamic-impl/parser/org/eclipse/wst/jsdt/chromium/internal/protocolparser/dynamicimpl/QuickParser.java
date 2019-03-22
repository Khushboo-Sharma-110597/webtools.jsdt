// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.protocolparser.dynamicimpl;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonSubtypeCondition;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.dynamicimpl.JavaCodeGenerator.ClassScope;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.dynamicimpl.JavaCodeGenerator.FileScope;
import org.eclipse.wst.jsdt.chromium.internal.protocolparser.dynamicimpl.JavaCodeGenerator.MethodScope;

/**
 * A parser that accepts value of JSON field and outputs value in another form (e.g. string
 * is converted to enum constant) to serve field getters in JsonType interfaces.
 * The parser is called "quick" because it is supposed to employ only fast conversions.
 * The quick parser should be suitable for subtype conditions
 * (see {@link JsonSubtypeCondition} etc), because they should not take long to evaluate.
 */
abstract class QuickParser<T> extends SlowParser<T> {
  @Override
  public T parseValue(Object value, ObjectData thisData) throws JsonProtocolParseException {
    return parseValueQuick(value);
  }

  /**
   * Parses input value and returns output that doesn't need any post-processing
   * by {@link FieldLoadedFinisher} (see {@link SlowParser}).
   */
  public abstract T parseValueQuick(Object value) throws JsonProtocolParseException;

  @Override
  public QuickParser<T> asQuickParser() {
    return this;
  }

  @Override
  public FieldLoadedFinisher getValueFinisher() {
    return null;
  }

  @Override
  public JsonTypeParser<?> asJsonTypeParser() {
    return null;
  }

  @Override
  public void appendInternalValueTypeNameJava(FileScope scope) {
    appendFinishedValueTypeNameJava(scope);
  }

  @Override
  void writeParseCode(MethodScope scope, String valueRef,
      String superValueRef, String resultRef) {
    writeParseQuickCode(scope, valueRef, resultRef);
  }

  @Override
  abstract boolean javaCodeThrowsException();

  abstract void writeParseQuickCode(MethodScope scope, String valueRef, String resultRef);
}
