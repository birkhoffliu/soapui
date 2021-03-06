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

package com.eviware.soapui.impl.wsdl.actions.project;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.support.SoapUIException;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;

public class OpenAndDecryptProjectAction extends AbstractSoapUIAction<WsdlProject>
{

	public static final String SOAPUI_ACTION_ID = "OpenAndDecryptProjectAction";

	public OpenAndDecryptProjectAction()
	{
		super( "Open and Decrypt", "Opens and decrypts this project" );
	}

	public void perform( WsdlProject project, Object param )
	{
		try
		{
			UISupport.select( project.getWorkspace().openProject( project ) );
		}
		catch( SoapUIException e )
		{
			UISupport.showErrorMessage( e );
		}
	}

}
