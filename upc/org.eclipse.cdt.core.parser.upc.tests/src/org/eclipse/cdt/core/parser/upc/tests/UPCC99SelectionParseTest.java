/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.core.parser.upc.tests;

import org.eclipse.cdt.core.dom.c99.C99Language;
import org.eclipse.cdt.core.dom.upc.UPCLanguage;
import org.eclipse.cdt.core.parser.c99.tests.C99SelectionParseTest;

public class UPCC99SelectionParseTest extends C99SelectionParseTest {

	public UPCC99SelectionParseTest() {
	}

	public UPCC99SelectionParseTest(String name, Class className) {
		super(name, className);
	}

	public UPCC99SelectionParseTest(String name) {
		super(name);
	}

	protected C99Language getLanguage() {
		return new UPCLanguage();
	}

}
