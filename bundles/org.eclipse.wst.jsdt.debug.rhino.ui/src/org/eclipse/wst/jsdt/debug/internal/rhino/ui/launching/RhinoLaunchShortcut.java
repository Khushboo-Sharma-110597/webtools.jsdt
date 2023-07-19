/*******************************************************************************
 * Copyright (c) 2010, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Sumit Singh <sumit.singh@infineon.com> - 434158 - Debug runs even select 
 *          cancel for ElementListSelectionDialog for debug configuration if 
 *          multiple configuration exists for same file.
 *******************************************************************************/
package org.eclipse.wst.jsdt.debug.internal.rhino.ui.launching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.wst.jsdt.core.IJavaScriptElement;
import org.eclipse.wst.jsdt.debug.internal.rhino.ui.ILaunchConstants;
import org.eclipse.wst.jsdt.debug.internal.rhino.ui.RhinoUIPlugin;

/**
 * Shortcut for launching JavaScript using Rhino
 * 
 * @since 1.0
 */
public class RhinoLaunchShortcut implements ILaunchShortcut2 {

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.jface.viewers.ISelection, java.lang.String)
	 */
	public void launch(ISelection selection, String mode) {
		launch(getLaunchableResource(selection), mode);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.ui.IEditorPart, java.lang.String)
	 */
	public void launch(IEditorPart editor, String mode) {
		launch(getLaunchableResource(editor), mode);
	}

	/**
	 * Handles the launch
	 * 
	 * @param resource
	 * @param mode
	 */
	void launch(IResource resource, String mode) {
		if (resource != null) {
			List configs = getCandidates(resource);
			if (configs != null) {
				ILaunchConfiguration config = null;
				int count = configs.size();
				if (count == 1) {
					config = (ILaunchConfiguration) configs.get(0);
				} else if (count > 1) {
					config = chooseConfiguration(configs);
					if (config == null) {
						return;
					}
				}
				if (config == null) {
					config = createConfiguration(resource);
				}
				if (config != null) {
					DebugUITools.launch(config, mode);
				}
			}
		}
	}

	/**
	 * Creates and returns a new configuration for JavaScript based on the
	 * resource.
	 * 
	 * @param resource
	 *            the resource to find the configuration for
	 * @param config
	 * @return
	 */
	ILaunchConfiguration createConfiguration(IResource resource) {
		ILaunchConfiguration config = null;
		ILaunchConfigurationType type = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType(ILaunchConstants.LAUNCH_CONFIG_TYPE);
		String project = resource.getProject().getName();
		String script = (resource instanceof IFile ? resource.getFullPath().toString() : null);
		try {
			if (script != null) {
				ILaunchConfigurationWorkingCopy copy = type.newInstance(null, NLS.bind(Messages.config_name, new String[] { project, resource.getName() }));
				copy.setAttribute(ILaunchConstants.ATTR_SCRIPT, script);
				copy.setAttribute(ILaunchConstants.ATTR_LOG_INTERPRETER_EXCEPTIONS, true);
				copy.setAttribute(ILaunchConstants.ATTR_ECMA_VERSION, ILaunchConstants.ECMA_170);
				copy.setAttribute(ILaunchConstants.ATTR_OPT_LEVEL, -1);
				copy.setAttribute(ILaunchConstants.ATTR_STRICT_MODE, false);
				copy.setMappedResources(new IResource[] { resource });
				config = copy.doSave();
			}
		} catch (CoreException ce) {
			RhinoUIPlugin.log(ce);
		}
		return config;
	}

	/**
	 * Finds an existing configuration to launch, returns <code>null</code> if
	 * none found, prompts for selection if more than one found.
	 * 
	 * @param resource
	 *            the resource to find the configuration for
	 * @return
	 */
	ILaunchConfiguration findConfig(IResource resource) {
		List candidates = getCandidates(resource);
		int candidateCount = candidates.size();
		if (candidateCount == 1) {
			return (ILaunchConfiguration) candidates.get(0);
		} else if (candidateCount > 1) {
			return chooseConfiguration(candidates);
		}
		return null;
	}

	/**
	 * Returns a configuration from the given collection of configurations that
	 * should be launched, or <code>null</code> to cancel. Default
	 * implementation opens a selection dialog that allows the user to choose
	 * one of the specified launch configurations. Returns the chosen
	 * configuration, or <code>null</code> if the user cancels.
	 * 
	 * @param configList
	 *            list of configurations to choose from
	 * @return configuration to launch or <code>null</code> to cancel
	 */
	protected ILaunchConfiguration chooseConfiguration(List configList) {
		IDebugModelPresentation labelProvider = DebugUITools.newDebugModelPresentation();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(RhinoUIPlugin.getActiveWorkbenchWindow().getShell(), labelProvider);
		dialog.setElements(configList.toArray());
		dialog.setTitle(Messages.select_rhino_config);
		dialog.setMessage(Messages.select_existing_config);
		dialog.setMultipleSelection(false);
		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK) {
			return (ILaunchConfiguration) dialog.getFirstResult();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut2#getLaunchConfigurations(org.eclipse.jface.viewers.ISelection)
	 */
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		//framework will resolve based on resource mappings
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut2#getLaunchConfigurations(org.eclipse.ui.IEditorPart)
	 */
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		//framework will resolve based on resource mappings
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut2#getLaunchableResource(org.eclipse.jface.viewers.ISelection)
	 */
	public IResource getLaunchableResource(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object obj = ss.getFirstElement();
			if (obj instanceof IAdaptable) {
				return getResource((IAdaptable) obj);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut2#getLaunchableResource(org.eclipse.ui.IEditorPart)
	 */
	public IResource getLaunchableResource(IEditorPart editorpart) {
		return getResource(editorpart.getEditorInput());
	}

	/**
	 * Collect the listing of {@link ILaunchConfiguration}s that apply to the
	 * given {@link IResource}.
	 * 
	 * @param resource
	 * @return the list of {@link ILaunchConfiguration}s or an empty list, never
	 *         <code>null</code>
	 */
	List getCandidates(IResource resource) {
		List candidates = Collections.EMPTY_LIST;
		try {
			ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
			ILaunchConfigurationType type = lm.getLaunchConfigurationType(ILaunchConstants.LAUNCH_CONFIG_TYPE);
			ILaunchConfiguration[] configs = lm.getLaunchConfigurations(type);
			candidates = new ArrayList(configs.length);
			for (int i = 0; i < configs.length; i++) {
				ILaunchConfiguration config = configs[i];
				if (config.hasAttribute(ILaunchConstants.ATTR_SCRIPT)) {
					if (config.getAttribute(ILaunchConstants.ATTR_SCRIPT, ILaunchConstants.EMPTY_STRING).equals(resource.getFullPath().toString())) {
						candidates.add(config);
					}
				}
			}
		} catch (CoreException e) {
			RhinoUIPlugin.log(e);
		}
		return candidates;
	}

	/**
	 * Returns the resource backing the {@link IAdaptable} providing it has an
	 * {@link IJavaScriptElement} adapter.
	 * 
	 * @param adaptable
	 * @return the backing {@link IResource} or <code>null</code>
	 */
	IResource getResource(IAdaptable adaptable) {
		IJavaScriptElement element = adaptable.getAdapter(IJavaScriptElement.class);
		if (element != null) {
			return element.getResource();
		}
		return adaptable.getAdapter(IResource.class);
	}
}
