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

package com.eviware.soapui.model.mock;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockResponse;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionContext;

/**
 * Context available for the duration of a MockServices execution
 * 
 * @author ole.matzura
 */

public interface MockRunContext extends PropertyExpansionContext
{
	public MockService getMockService();

	public MockResponse getMockResponse();

	public MockRunner getMockRunner();

	public void setMockResponse( WsdlMockResponse wsdlMockResponse );
}
