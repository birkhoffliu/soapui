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

package com.eviware.soapui.impl.wsdl.actions.mockresponse;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockResponse;
import com.eviware.soapui.impl.wsdl.support.soap.SoapVersion;
import com.eviware.soapui.impl.wsdl.support.wsa.WsaUtils;
import com.eviware.soapui.model.propertyexpansion.DefaultPropertyExpansionContext;
import com.eviware.soapui.support.UISupport;

/**
 * Removes WS-A headers from the specified WsdlRequests requestContent
 * 
 * @author dragica.soldo
 */

public class RemoveWsaHeadersFromMockResponseAction extends AbstractAction
{
	private final WsdlMockResponse mockResponse;

	public RemoveWsaHeadersFromMockResponseAction( WsdlMockResponse mockResponse )
	{
		super( "Remove WS-A headers" );
		this.mockResponse = mockResponse;
	}

	public void actionPerformed( ActionEvent e )
	{
		try
		{
			SoapVersion soapVersion = mockResponse.getOperation().getInterface().getSoapVersion();
			String content = mockResponse.getResponseContent();
			WsaUtils wsaUtils = new WsaUtils( content, soapVersion, mockResponse.getOperation(),
					new DefaultPropertyExpansionContext( mockResponse ) );
			content = wsaUtils.removeWSAddressing( mockResponse );
			mockResponse.setResponseContent( content );
		}
		catch( Exception e1 )
		{
			UISupport.showErrorMessage( e1 );
		}
		finally
		{
			UISupport.resetCursor();
		}
	}
}
