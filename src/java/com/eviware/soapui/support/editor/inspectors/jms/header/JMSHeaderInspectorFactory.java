/*
 *  soapUI, copyright (C) 2004-2011 eviware.com 
 *
 *  soapUI is free software; you can redistribute it and/or modify it under the 
 *  terms of version 2.1 of the GNU Lesser General Public License as published by 
 *  the Free Software Foundation.
 *
 *  soapUI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 *  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *  See the GNU Lesser General Public License for more details at gnu.org.
 */

package com.eviware.soapui.support.editor.inspectors.jms.header;

import com.eviware.soapui.impl.support.AbstractHttpRequest;
import com.eviware.soapui.impl.wsdl.submit.transports.jms.util.JMSUtils;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.support.editor.Editor;
import com.eviware.soapui.support.editor.EditorInspector;
import com.eviware.soapui.support.editor.registry.RequestInspectorFactory;
import com.eviware.soapui.support.editor.registry.ResponseInspectorFactory;

public class JMSHeaderInspectorFactory implements RequestInspectorFactory, ResponseInspectorFactory
{
	public static final String INSPECTOR_ID = "JMS Header";

	public String getInspectorId()
	{
		return INSPECTOR_ID;
	}

	public EditorInspector<?> createRequestInspector( Editor<?> editor, ModelItem modelItem )
	{
		if( modelItem instanceof AbstractHttpRequest )
		{
			RequestJMSHeaderInspector inspector = new RequestJMSHeaderInspector( ( ( AbstractHttpRequest<?> )modelItem ) );
			inspector.setEnabled( JMSUtils.checkIfJMS( modelItem ) );
			return inspector;
		}
		return null;
	}

	public EditorInspector<?> createResponseInspector( Editor<?> editor, ModelItem modelItem )
	{
		return null;
	}
}
