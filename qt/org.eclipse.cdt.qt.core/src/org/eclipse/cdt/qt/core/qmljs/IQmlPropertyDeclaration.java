/*******************************************************************************
 * Copyright (c) 2015 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * QNX Software Systems - Initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.qt.core.qmljs;

public interface IQmlPropertyDeclaration extends IQmlObjectMember {
	@Override
	default public String getType() {
		return "QMLPropertyDeclaration"; //$NON-NLS-1$
	};

	public boolean isReadonly();

	public boolean isDefault();

	public IQmlPropertyType getKind();

	public IQmlPropertyType getModifier();

	public IJSIdentifier getIdentifier();

	public IQmlBinding getBinding();
}
