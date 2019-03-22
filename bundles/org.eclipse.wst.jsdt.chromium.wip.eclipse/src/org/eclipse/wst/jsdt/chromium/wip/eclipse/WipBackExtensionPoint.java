// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.wip.eclipse;

import org.eclipse.wst.jsdt.chromium.wip.WipBackend;

/**
 * Holds several constants that defines extension point used to fetch {@link WipBackend} instances
 * in Eclipse.
 */
public interface WipBackExtensionPoint {
  String ID = "org.eclipse.wst.jsdt.chromium.wip.eclipse.WipBacked";
  String ELEMENT_NAME = "backend";
  String CLASS_PROPERTY = "class";
}
