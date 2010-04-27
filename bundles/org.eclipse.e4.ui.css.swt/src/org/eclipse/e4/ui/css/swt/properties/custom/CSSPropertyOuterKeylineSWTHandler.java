/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.ui.css.swt.properties.custom;

import java.lang.reflect.Method;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.properties.AbstractCSSPropertySWTHandler;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.w3c.dom.css.CSSValue;

public class CSSPropertyOuterKeylineSWTHandler extends AbstractCSSPropertySWTHandler {

	
	public static final ICSSPropertyHandler INSTANCE = new CSSPropertyOuterKeylineSWTHandler();
	
	@Override
	protected void applyCSSProperty(Control control, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (!(control instanceof CTabFolder)) return;
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
			
			CTabFolderRenderer renderer = ((CTabFolder) control).getRenderer();
			Object appContext = control.getDisplay().getData("org.eclipse.e4.ui.css.context");
			if (appContext != null && appContext instanceof IEclipseContext) {
				IEclipseContext context = (IEclipseContext) appContext;
				IEclipseContext childContext = context.createChild();
				childContext.set("outerKeyline", newColor);
				ContextInjectionFactory.inject(renderer, childContext); 
			} else {
				Method[] methods = renderer.getClass().getMethods();
				for (int i = 0; i < methods.length; i++) {
					Method m = methods[i];
					if (m.getName().toLowerCase().contains("setouterkeyline")) {
						m.invoke(renderer, newColor);
					}
				}
			}
		}
	}
	

	@Override
	protected String retrieveCSSProperty(Control control, String property,
			String pseudo, CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
