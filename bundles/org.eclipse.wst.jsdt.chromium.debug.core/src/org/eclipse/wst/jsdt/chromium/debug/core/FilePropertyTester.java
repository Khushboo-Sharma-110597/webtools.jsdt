// Copyright (c) 2010 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.debug.core;

import org.eclipse.wst.jsdt.chromium.debug.core.util.ChromiumDebugPluginUtil;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;

/**
 * Implementation of additional properties for files that are JavaScript sources.
 */
public abstract class FilePropertyTester extends PropertyTester {
  private static final String IS_JS_FILE_PROPERTY = "isJsFile"; //$NON-NLS-1$

  public static class ForFile extends FilePropertyTester {
    @Override
    protected IFile extractFile(Object receiver) {
      return (IFile) receiver;
    }
  }

  public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
    IFile file = extractFile(receiver);
    if (IS_JS_FILE_PROPERTY.equals(property)) {
      return testIsJsFile(file);
    } else {
      throw new RuntimeException("Unrecognized property name"); //$NON-NLS-1$
    }
  }

  protected abstract IFile extractFile(Object receiver);

  private static boolean testIsJsFile(IFile file) {
    return ChromiumDebugPluginUtil.SUPPORTED_EXTENSIONS.contains(file.getFileExtension());
  }
}
