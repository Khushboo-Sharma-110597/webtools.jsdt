// Copyright (c) 2009 The Chromium Authors. All rights reserved.
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
 * Specifies a condition for a field-reading method. It is one from group of annotations that
 * mark key fields for choosing particular subtype. They set conditions on JSON fields in a subtype
 * interface that drive subtype auto-selection at parsing time.
 * <p>
 * Specifies condition on a boolean value (for boolean-valued fields).
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonSubtypeConditionBoolValue {
  /**
   * A value of boolean field that satisfies condition.
   */
  boolean value();
}
