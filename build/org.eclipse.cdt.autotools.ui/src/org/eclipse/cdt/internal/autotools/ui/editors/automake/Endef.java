/*******************************************************************************
 * Copyright (c) 2000, 2015 QNX Software Systems and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     QNX Software Systems - Initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.internal.autotools.ui.editors.automake;

public class Endef extends Terminal {

	public Endef(Directive parent) {
		super(parent);
	}

	@Override
	public boolean isEndef() {
		return true;
	}

	@Override
	public String toString() {
		return GNUMakefileConstants.TERMINAL_ENDEF;
	}
}
