/*******************************************************************************
 *  Copyright (c) 2004, 2009 IBM Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     Andrew Niefer (IBM Corporation) - initial API and implementation
 *     Markus Schorn (Wind River Systems)
 *******************************************************************************/
package org.eclipse.cdt.internal.core.dom.parser.cpp;

import org.eclipse.cdt.core.dom.ast.ASTTypeUtil;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IType;
import org.eclipse.cdt.core.dom.ast.ITypedef;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPBasicType;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.IGPPASTSimpleDeclSpecifier;
import org.eclipse.cdt.internal.core.index.IIndexType;

/**
 * Integral c++ type.
 */
public class CPPBasicType implements ICPPBasicType {
	public static int UNIQUE_TYPE_QUALIFIER= -1;
	private final Kind fKind;
	private final int fQualifierBits;
	private IASTExpression fExpression;

	public CPPBasicType(Kind kind, int qualifiers, IASTExpression expression) {
		if (kind == Kind.eUnspecified) {
			if ( (qualifiers & (IS_COMPLEX | IS_IMAGINARY)) != 0) {
				fKind= Kind.eFloat;
			} else if ( (qualifiers & (IS_LONG | IS_SHORT | IS_SIGNED | IS_UNSIGNED | IS_LONG_LONG)) != 0 ) {
				fKind= Kind.eInt;
			} else {
				fKind= Kind.eUnspecified;
			}
		} else {
			fKind= kind;
		}
		fQualifierBits= qualifiers;
		fExpression= expression;
	}

	public CPPBasicType(Kind kind, int qualifiers) {
		this(kind, qualifiers, null);
	}
	
	public CPPBasicType(ICPPASTSimpleDeclSpecifier sds) {
		this (getKind(sds), getQualifiers(sds), null);
	}
	
	private static int getQualifiers(ICPPASTSimpleDeclSpecifier sds) {
		int qualifiers=
			( sds.isLong()    ? ICPPBasicType.IS_LONG  : 0 ) |
			( sds.isShort()   ? ICPPBasicType.IS_SHORT : 0 ) |
			( sds.isSigned()  ? ICPPBasicType.IS_SIGNED: 0 ) |
			( sds.isUnsigned()? ICPPBasicType.IS_UNSIGNED : 0 );
		if (sds instanceof IGPPASTSimpleDeclSpecifier) {
			IGPPASTSimpleDeclSpecifier gsds= (IGPPASTSimpleDeclSpecifier) sds;
			qualifiers |=
				( gsds.isLongLong()? ICPPBasicType.IS_LONG_LONG : 0 ) |
				( gsds.isComplex() ? ICPPBasicType.IS_COMPLEX : 0 ) |
				( gsds.isImaginary()?ICPPBasicType.IS_IMAGINARY : 0 );
		}
		return qualifiers;
	}
	
	private static Kind getKind(ICPPASTSimpleDeclSpecifier sds) {
		return getKind(sds.getType());
	}

	static Kind getKind(final int simpleDeclSpecType) {
		switch(simpleDeclSpecType) {
		case ICPPASTSimpleDeclSpecifier.t_bool:
			return Kind.eBoolean;
		case IASTSimpleDeclSpecifier.t_char:
			return Kind.eChar;
		case ICPPASTSimpleDeclSpecifier.t_wchar_t:
			return Kind.eWChar;
		case IASTSimpleDeclSpecifier.t_double:
			return Kind.eDouble;
		case IASTSimpleDeclSpecifier.t_float:
			return Kind.eFloat;
		case IASTSimpleDeclSpecifier.t_int:
			return Kind.eInt;
		case IASTSimpleDeclSpecifier.t_void:
			return Kind.eVoid;
		default:
			return Kind.eUnspecified;
		}
	}


	public boolean isSameType(IType object) {
		if (object == this)
			return true;

		if (fQualifierBits == -1)
			return false;

	    if (object instanceof ITypedef || object instanceof IIndexType)
	        return object.isSameType(this);

		if (!(object instanceof CPPBasicType))
			return false;

		CPPBasicType t = (CPPBasicType) object;
		if (fKind != t.fKind)
			return false;

		if (fKind == Kind.eInt) {
			//signed int and int are equivalent
			return (fQualifierBits & ~IS_SIGNED) == (t.fQualifierBits & ~IS_SIGNED);
		}
		return fQualifierBits == t.fQualifierBits;
	}

	public Kind getKind() {
		return fKind;
	}
	
	public boolean isSigned() {
		return (fQualifierBits & IS_SIGNED) != 0;
	}

	public boolean isUnsigned() {
		return (fQualifierBits & IS_UNSIGNED) != 0;
	}

	public boolean isShort() {
		return (fQualifierBits & IS_SHORT) != 0;
	}

	public boolean isLong() {
		return (fQualifierBits & IS_LONG) != 0;
	}

	public boolean isLongLong() {
		return (fQualifierBits & IS_LONG_LONG) != 0;
	}

	public boolean isComplex() {
		return (fQualifierBits & IS_COMPLEX) != 0;
	}

	public boolean isImaginary() {
		return (fQualifierBits & IS_IMAGINARY) != 0;
	}

	@Override
	public Object clone() {
        IType t = null;
   		try {
            t = (IType) super.clone();
        } catch (CloneNotSupportedException e) {
            //not going to happen
        }
        return t;
    }

	public void setFromExpression(IASTExpression val) {
		fExpression = val;
	}

	/**
	 * Returns the expression the type was created for, or <code>null</code>.
	 */
	public IASTExpression getCreatedFromExpression() {
		return fExpression;
	}
	
	public int getQualifierBits() {
		return fQualifierBits;
	}

	@Override
	public String toString() {
		return ASTTypeUtil.getType(this);
	}
	
	@Deprecated
	public int getType() {
		switch (fKind) {
		case eBoolean:
			return t_bool;
		case eChar:
			return t_char;
		case eWChar:
			return t_wchar_t;
		case eDouble:
			return t_double;
		case eFloat:
			return t_float;
		case eInt:
			return t_int;
		case eVoid:
			return t_void;
		case eUnspecified:
			return t_unspecified;
		}
		return t_unspecified;
	}
    /**
     * @deprecated types don't have values
     */
	@Deprecated
	public IASTExpression getValue() {
		return fExpression;
	}
}
