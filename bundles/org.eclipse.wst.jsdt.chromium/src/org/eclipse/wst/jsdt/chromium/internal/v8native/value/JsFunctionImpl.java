// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.v8native.value;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.wst.jsdt.chromium.FunctionScopeExtension;
import org.eclipse.wst.jsdt.chromium.JsArray;
import org.eclipse.wst.jsdt.chromium.JsFunction;
import org.eclipse.wst.jsdt.chromium.JsScope;
import org.eclipse.wst.jsdt.chromium.JsVariable;
import org.eclipse.wst.jsdt.chromium.Script;
import org.eclipse.wst.jsdt.chromium.TextStreamPosition;
import org.eclipse.wst.jsdt.chromium.internal.v8native.DebugSession;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.ScopeRef;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.data.FunctionValueHandle;
import org.eclipse.wst.jsdt.chromium.util.MethodIsBlockingException;

/**
 * Generic implementation of {@link JsFunction}.
 */
public class JsFunctionImpl extends JsObjectBase<JsObjectBase.BasicPropertyData>
    implements JsFunction {
  private volatile TextStreamPosition openParenPosition = null;

  JsFunctionImpl(ValueLoader valueLoader, ValueMirror valueState) {
    super(valueLoader, valueState);
  }

  @Override
  public Script getScript() throws MethodIsBlockingException {
    final FunctionValueHandle functionValueHandle = getAdditionalPropertyData();
    Long scriptId = functionValueHandle.scriptId();
    if (scriptId == null) {
      return null;
    }
    DebugSession debugSession = getInternalContext().getDebugSession();
    return debugSession.getScriptManager().findById(scriptId);
  }

  private List<? extends JsScope> getVariableScopes() {
    List<ScopeRef> rawScopes = getAdditionalPropertyData().scopes();
    List<JsScopeImpl<?>> result = new ArrayList<JsScopeImpl<?>>(rawScopes.size());
    for (ScopeRef scopeRef : rawScopes) {
      result.add(JsScopeImpl.create(JsScopeImpl.Host.create(JsFunctionImpl.this), scopeRef));
    }
    return result;
  }

  @Override
  public TextStreamPosition getOpenParenPosition() throws MethodIsBlockingException {
    if (openParenPosition == null) {
      final FunctionValueHandle functionValueHandle = getAdditionalPropertyData();
      openParenPosition = new TextStreamPosition() {
        @Override public int getOffset() {
          return castLongToInt(functionValueHandle.position(), NO_POSITION);
        }
        @Override public int getLine() {
          return castLongToInt(functionValueHandle.line(), NO_POSITION);
        }
        @Override public int getColumn() {
          return castLongToInt(functionValueHandle.column(), NO_POSITION);
        }
        private int castLongToInt(Long objValue, int defaultValue) {
          if (objValue == null) {
            return defaultValue;
          } else {
            return objValue.intValue();
          }
        }
      };
    }
    return openParenPosition;
  }

  private FunctionValueHandle getAdditionalPropertyData() throws MethodIsBlockingException {
    SubpropertiesMirror subpropertiesMirror = getBasicPropertyData(false).getSubpropertiesMirror();
    return (FunctionValueHandle) subpropertiesMirror.getAdditionalPropertyData();
  }

  @Override
  public JsArray asArray() {
    return null;
  }

  @Override
  public JsFunction asFunction() {
    return this;
  }

  @Override
  protected BasicPropertyData wrapBasicData(BasicPropertyData basicPropertyData) {
    return basicPropertyData;
  }

  @Override
  protected BasicPropertyData unwrapBasicData(BasicPropertyData additionalPropertyStore) {
    return additionalPropertyStore;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("[JsFunction: type=").append(getType());
    try {
      for (JsVariable prop : getProperties()) {
        result.append(',').append(prop);
      }
    } catch (MethodIsBlockingException e) {
      return "[JsObject: Exception in retrieving data]";
    }
    result.append(']');
    return result.toString();
  }

  public static final FunctionScopeExtension FUNCTION_SCOPE_EXTENSION =
      new FunctionScopeExtension() {
        @Override
        public List<? extends JsScope> getScopes(JsFunction function)
            throws MethodIsBlockingException {
          JsFunctionImpl functionImpl = (JsFunctionImpl) function;
          return functionImpl.getVariableScopes();
        }
      };
}
