/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.jsdt.core.tests.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.tests.junit.extension.TestCase;

	/**
	 * Run all java model tests.
	 */
	public class JSDTModelTests extends TestSuite {
	static {
		JavaScriptCore.getPlugin().getPluginPreferences().setValue("semanticValidation", true);
	}
	
	public JSDTModelTests() {
		this("JavaScript Model Tests");
	}
		
	public JSDTModelTests(String name) {
		super(name);
		Class[] classes = getAllTestClasses();
		for (int i = 0; i < classes.length; i++) {
			addTestSuite(classes[i]);
		}
	}

private static Class[] getAllTestClasses() {
	Class[] classes = new Class[] {
	
		// Enter each test here, grouping the tests that are related
			
		// Binding key tests
		BindingKeyTests.class,

//		
//		TEMPORARY COMMENT OUT TESTS
//		
//		
//		// creation of method
//		CreateMembersTests.class,
//		
//		// Java Naming convention tests
//		JavaConventionTests.class,
//	
//		// Project & Root API unit tests
//		JavaProjectTests.class,
//	
//		// Compilation unit tests
//		CompilationUnitTests.class,
//	
//		// Source attachment tests
//		AttachSourceTests.class,
//		
//		// Attached javadoc tests
//		AttachedJavadocTests.class,
//	
//		// Java search tests
//		RunJavaSearchTests.class,
//			
//		// Working copy tests
//		WorkingCopyTests.class,
//		WorkingCopyNotInClasspathTests.class,
//		HierarchyOnWorkingCopiesTests.class,
//		
//		// test IJavaScriptModel
//		JavaModelTests.class,
//	
//		// tests to check the encoding
//		EncodingTests.class,
//		
//		// test class name with special names like names containing '$'
//		ClassNameTests.class,
//		
		// IBuffer tests
		BufferTests.class,
//	
//		// Name lookup tests
//		NameLookupTests2.class,
//	
//		// Classpath and output location tests
//		ClasspathTests.class,
//	
//		// Delta tests
//		JavaElementDeltaTests.class,
//		ExternalJarDeltaTests.class,
//	
//		// Java element existence tests
//		ExistenceTests.class,
//		
//		// Support for "open on" feature tests
//		ResolveTests.class,
//		ResolveTests_1_5.class,
//		SelectionJavadocModelTests.class,
//
//		// Support for completion tests
		RunCompletionModelTests.class,
//		
//		// Prefix and suffix tests
//		NamingConventionTests.class,
//		
//		// Code correction tests
//		CodeCorrectionTests.class,
//		
//		// Options tests
//		OptionTests.class,
//		
//		// Type hierarchy tests
//		TypeHierarchyTests.class,
//		TypeHierarchyNotificationTests.class,
//		TypeHierarchySerializationTests.class,
//		
//		// Resolve type tests
//		TypeResolveTests.class,
//	
//		// Reconciler tests
//		ReconcilerTests.class,
//		ReconcilerStatementsRecoveryTests.class,
//		
//		// Copy and move operation tests
//		CopyMoveElementsTests.class,
//		CopyMoveResourcesTests.class,
//	
//		// Rename tests
//		RenameTests.class,
//		
//		// Exclusion patterns tests
//		ExclusionPatternsTests.class,
//		
//		// Inclusion patterns tests
//		InclusionPatternsTests.class,
//		
//		// Access restrictions tests
//		AccessRestrictionsTests.class,
//		
//		// Signature tests
//		SignatureTests.class,
//		
//		// Variable initializers and container initializers tests
//		ClasspathInitializerTests.class,
//	
//		// Java Model Factory tests
//		FactoryTests.class,
//				
//		// Java Element persistence tests
//		MementoTests.class,
//		
//		// Java Element sorting tests
//		SortCompilationUnitElementsTests.class,
//	
//		// Package fragment root manipulation tests
//		RootManipulationsTests.class,
//		
//		// Owverflowing cache tests
//		OverflowingCacheTests.class,
//		
//		// Working copy owner tests
//		WorkingCopyOwnerTests.class,
//	
//		// Delete Java element tests
//		DeleteTests.class,
//		
//		// Local element tests
//		LocalElementTests.class,
//		
//		// Get source tests
//		GetSourceTests.class,
//			
//		// Create packages tests
//		CreatePackageTests.class,
//	
//		// Create compilation units tests
//		CreateCompilationUnitTests.class,
//		
//		// Create search participant tests
//		SearchParticipantTests.class,
//		
//		// Class file tests
//		ClassFileTests.class,
//		
//		// Java-like extensions tests
//		JavaLikeExtensionsTests.class,
//		
//		// Creation of imports
//		CreateImportsTests.class,
		
		// 
		CodeSnippetParsingUtilTests.class
	};
	
	int classesLength = classes.length;
	Class[] result = new Class[classesLength];
	System.arraycopy(classes, 0, result, 0, classesLength);
	
	return result;
}

public static Test suite() {
	TestSuite suite = new TestSuite("JSDT Model Tests");

	// Hack to load all classes before computing their suite of test cases
	// this allow to reset test cases subsets while running all Java Model tests...
	Class[] classes = getAllTestClasses();

	// Reset forgotten subsets of tests
	TestCase.TESTS_PREFIX = null;
	TestCase.TESTS_NAMES = null;
	TestCase.TESTS_NUMBERS = null;
	TestCase.TESTS_RANGE = null;
	TestCase.RUN_ONLY_ID = null;
	
	for (int i = 0, length = classes.length; i < length; i++) {
		Class clazz = classes[i];
		Method suiteMethod;
		try {
			suiteMethod = clazz.getDeclaredMethod("suite", new Class[0]);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			continue;
		}
		Object test;
		try {
			test = suiteMethod.invoke(null, new Object[0]);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			continue;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			continue;
		}
		suite.addTest((Test) test);
	}

	return suite;
}

}
