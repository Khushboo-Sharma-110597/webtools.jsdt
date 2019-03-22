// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.internal.protocolparser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a user-provided main interface to JSON parser -- 'root' interface.
 * All methods of the interface must be 'parse' methods (see {@link JsonParseMethod}).
 * The interface may extend other {@link JsonParserRoot}-annotated interfaces
 * explicitly and implicitly.
 * @see JsonParseMethod
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JsonParserRoot {
}
