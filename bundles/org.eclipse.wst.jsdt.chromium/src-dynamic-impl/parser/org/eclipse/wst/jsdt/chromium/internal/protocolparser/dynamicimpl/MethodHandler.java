// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.protocolparser.dynamicimpl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;

import org.eclipse.wst.jsdt.chromium.internal.protocolparser.dynamicimpl.JavaCodeGenerator.ClassScope;

/**
 * An abstract method handler for {@link JsonInvocationHandler}.
 */
abstract class MethodHandler {
  abstract Object handle(ObjectData objectData, Object[] args) throws Throwable;

  abstract void writeMethodImplementationJava(ClassScope classScope, Method m);

  protected static void appendMethodSignatureJava(ClassScope scope, Method m,
      Iterable<String> paramNames) {
    scope.append(m.getName());
    scope.append("(");
    boolean firstArg = true;
    Iterator<String> namesIt = paramNames.iterator();
    for (Type arg : m.getGenericParameterTypes()) {
      if (!firstArg) {
        scope.append(", ");
      }
      JavaCodeGenerator.Util.writeJavaTypeName(arg, scope.getStringBuilder());
      scope.append(" " + namesIt.next());
    }
    scope.append(")");
  }

  protected static void writeMethodDeclarationJava(ClassScope scope, Method m,
      Iterable<String> paramNames) {
    scope.startLine("@Override public ");
    JavaCodeGenerator.Util.writeJavaTypeName(m.getGenericReturnType(), scope.getStringBuilder());
    scope.append(" ");
    appendMethodSignatureJava(scope, m, paramNames);
  }
}
