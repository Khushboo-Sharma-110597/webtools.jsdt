// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v2.0 which accompanies
// this distribution, and is available at
// https://www.eclipse.org/legal/epl-2.0/

package org.eclipse.wst.jsdt.chromium.debug.ui.launcher;

import java.util.ArrayList;

import org.eclipse.wst.jsdt.chromium.debug.ui.launcher.ChromiumRemoteTab.HostChecker;
import org.eclipse.wst.jsdt.chromium.util.BasicUtil;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;

/**
 * The Chromium JavaScript debugger launch configuration tab group.
 */
public abstract class LaunchTabGroup extends AbstractLaunchConfigurationTabGroup {
  public static class StandaloneV8 extends LaunchTabGroup {
    @Override protected ChromiumRemoteTab<?> createRemoteTab() {
      return new ChromiumRemoteTab.Standalone();
    }
  }

  public static class Wip extends LaunchTabGroup {
    @Override protected ChromiumRemoteTab<?> createRemoteTab() {
      return new WipRemoteTab();
    }
  }

  public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
    setTabs(BasicUtil.toArray(createTabList(dialog, mode), ILaunchConfigurationTab.class));
  }

  protected ArrayList<ILaunchConfigurationTab> createTabList(ILaunchConfigurationDialog dialog,
      String mode) {
    ArrayList<ILaunchConfigurationTab> result = new ArrayList<ILaunchConfigurationTab>(4);
    ChromiumRemoteTab<?> remoteTab = createRemoteTab();
    result.add(remoteTab);
    result.add(new SourceLookupTab());
    result.add(new ScriptMappingTab(remoteTab.getParams()));
    result.add(new CommonTab());
    return result;
  }

  protected abstract ChromiumRemoteTab<?> createRemoteTab();

  static class Params {
    private final HostChecker hostChecker;
    private final String scriptNameDescription;
    private final boolean enableSourceWrapper;

    Params(HostChecker hostChecker, String scriptNameDescription,
        boolean enableSourceWrapper) {
      this.hostChecker = hostChecker;
      this.scriptNameDescription = scriptNameDescription;
      this.enableSourceWrapper = enableSourceWrapper;
    }

    HostChecker getHostChecker() {
      return hostChecker;
    }

    String getScriptNameDescription() {
      return scriptNameDescription;
    }

    boolean preEnableSourceWrapper() {
      return enableSourceWrapper;
    }
  }
}
