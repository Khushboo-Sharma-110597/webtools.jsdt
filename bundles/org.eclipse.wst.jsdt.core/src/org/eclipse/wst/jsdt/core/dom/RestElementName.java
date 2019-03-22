/*******************************************************************************
 * Copyright (c) 2016 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * 	Contributors:
 * 		 Red Hat Inc. - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.wst.jsdt.core.dom;

import java.util.ArrayList;
import java.util.List;

/**
 * RestElement pattern
 *
 * Provisional API: This class/interface is part of an interim API that is
 * still under development and expected to change significantly before
 * reaching stability. It is being made available at this early stage to
 * solicit feedback from pioneering adopters on the understanding that any
 * code that uses this API will almost certainly be broken (repeatedly) as the
 * API evolves.
 *
 * @author Gorkem Ercan
 * @since 2.0
 */
public class RestElementName extends Name {


	/**
	 * The "argument" structural property of this node type
	 */
	public static final ChildPropertyDescriptor ARGUMENT_PROPERTY =
				new ChildPropertyDescriptor(RestElementName.class, "argument", Expression.class, MANDATORY, NO_CYCLE_RISK); //$NON-NLS-1$



	/**
	 * A list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;

	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(2);
		createPropertyList(RestElementName.class, propertyList);
		addProperty(ARGUMENT_PROPERTY, propertyList);
		PROPERTY_DESCRIPTORS = reapPropertyList(propertyList);
	}
	/**
	 * Returns a list of structural property descriptors for this node type.
	 * Clients must not modify the result.
	 *
	 * @param apiLevel the API level; one of the
	 * <code>AST.JLS*</code> constants

	 * @return a list of property descriptors (element type:
	 * {@link StructuralPropertyDescriptor})
	 *
	 */
	public static List<StructuralPropertyDescriptor> propertyDescriptors(int apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}

	private Expression argument;

	/**
	 * @param ast
	 */
	RestElementName(AST ast) {
		super(ast);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.core.dom.Name#appendName(java.lang.StringBuffer)
	 */
	@Override
	void appendName(StringBuffer buffer) {
		buffer.append(this.toString());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.core.dom.ASTNode#internalStructuralPropertiesForType(int)
	 */
	@Override
	List internalStructuralPropertiesForType(int apiLevel) {
		return propertyDescriptors(apiLevel);
	}

	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	@Override
	final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
		if (property == ARGUMENT_PROPERTY) {
			if (get) {
				return getArgument();
			} else {
				setArgument((Name) child);
				return null;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetChildProperty(property, get, child);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.core.dom.ASTNode#getNodeType0()
	 */
	@Override
	int getNodeType0() {
		return REST_ELEMENT_NAME;
	}


	public Expression getArgument() {
		return argument;
	}

	public void setArgument(Expression argument) {
		if (argument == null) {
			throw new IllegalArgumentException();
		}
		ASTNode oldChild = this.argument;
		preReplaceChild(oldChild, argument, ARGUMENT_PROPERTY);
		this.argument = argument;
		postReplaceChild(oldChild, argument, ARGUMENT_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.core.dom.ASTNode#subtreeMatch0(org.eclipse.wst.jsdt.core.dom.ASTMatcher, java.lang.Object)
	 */
	@Override
	boolean subtreeMatch0(ASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.core.dom.ASTNode#clone0(org.eclipse.wst.jsdt.core.dom.AST)
	 */
	@Override
	ASTNode clone0(AST target) {
		RestElementName result = new RestElementName(target);
		result.setSourceRange(this.getStartPosition(), this.getLength());
		result.setArgument((Expression) getArgument().clone(target));
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.core.dom.ASTNode#accept0(org.eclipse.wst.jsdt.core.dom.ASTVisitor)
	 */
	@Override
	void accept0(ASTVisitor visitor) {
		if(visitor.visit(this)){
			acceptChild(visitor, getArgument());
		}
		visitor.endVisit(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.core.dom.ASTNode#treeSize()
	 */
	@Override
	int treeSize() {
		return memSize()
			+ (this.argument == null ?0 :getArgument().treeSize());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.core.dom.ASTNode#memSize()
	 */
	@Override
	int memSize() {
		return BASE_NAME_NODE_SIZE + 4 ;
	}
}
