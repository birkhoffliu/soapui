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

package com.eviware.soapui.security.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.config.SecurityScanConfig;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.security.ui.SecurityConfigurationDialogBuilder;
import com.eviware.soapui.support.types.StringToStringMap;

/**
 * Registry of SecurityScan factories
 * 
 * @author soapUI team
 */

public class SecurityScanRegistry
{
	protected static SecurityScanRegistry instance;
	private Map<String, SecurityScanFactory> availableSecurityChecks = new HashMap<String, SecurityScanFactory>();
	private StringToStringMap securityCheckNames = new StringToStringMap();

	public SecurityScanRegistry()
	{
		addFactory( new GroovySecurityScanFactory() );
		addFactory( new CrossSiteScriptingScanFactory() );
		addFactory( new XmlBombSecurityScanFactory() );
		addFactory( new MaliciousAttachmentSecurityScanFactory() );
		addFactory( new XPathInjectionSecurityScanFactory() );
		addFactory( new InvalidTypesSecurityScanFactory() );
		addFactory( new BoundarySecurityScanFactory() );
		addFactory( new SQLInjectionScanFactory() );
		addFactory( new MalformedXmlSecurityScanFactory() );
		addFactory( new FuzzerSecurityScanFactory() );
		
		for( SecurityScanFactory factory : SoapUI.getFactoryRegistry().getFactories( SecurityScanFactory.class ) )
		{
			addFactory( factory );
		}

	}

	/**
	 * Gets the right SecurityScan Factory, depending on the type
	 * 
	 * @param type
	 *           The securityScan to get the factory for
	 * @return
	 */
	public SecurityScanFactory getFactory( String type )
	{
		for( String cc : availableSecurityChecks.keySet() )
		{
			SecurityScanFactory scf = availableSecurityChecks.get( cc );
			if( scf.getSecurityScanType().equals( type ) )
				return scf;

		}
		return null;
	}

	/**
	 * Gets the right SecurityScan Factory using name
	 * 
	 * @param name
	 *           The securityScan name to get the factory for
	 * @return
	 */
	public SecurityScanFactory getFactoryByName( String name )
	{
		String type = getSecurityScanTypeForName( name );

		if( type != null )
		{
			return getFactory( type );
		}

		return null;
	}

	/**
	 * Adding a new factory to the registry
	 * 
	 * @param factory
	 */
	public void addFactory( SecurityScanFactory factory )
	{
		removeFactory( factory.getSecurityScanType() );
		availableSecurityChecks.put( factory.getSecurityScanName(), factory );
		securityCheckNames.put( factory.getSecurityScanName(), factory.getSecurityScanType() );
	}

	/**
	 * Removing a factory from the registry
	 * 
	 * @param type
	 */
	public void removeFactory( String type )
	{
		for( String scfName : availableSecurityChecks.keySet() )
		{
			SecurityScanFactory csf = availableSecurityChecks.get( scfName );
			if( csf.getSecurityScanType().equals( type ) )
			{
				availableSecurityChecks.remove( scfName );
				securityCheckNames.remove( scfName );
				break;
			}
		}
	}

	/**
	 * 
	 * @return The registry instance
	 */
	public static synchronized SecurityScanRegistry getInstance()
	{
		if( instance == null )
			instance = new SecurityScanRegistry();

		return instance;
	}

	/**
	 * Checking if the registry contains a factory.
	 * 
	 * @param config
	 *           A configuration to check the factory for
	 * @return
	 */
	public boolean hasFactory( SecurityScanConfig config )
	{
		return getFactory( config.getType() ) != null;
	}

	/**
	 * Returns the list of available scans
	 * 
	 * @param monitorOnly
	 *           Set this to true to get only the list of scans which can be
	 *           used in the http monitor
	 * @return A String Array containing the names of all the scans
	 */
	public String[] getAvailableSecurityScansNames()
	{
		List<String> result = new ArrayList<String>();

		for( SecurityScanFactory securityCheck : availableSecurityChecks.values() )
		{
			result.add( securityCheck.getSecurityScanName() );
		}

		String[] sortedResult = result.toArray( new String[result.size()] );
		Arrays.sort( sortedResult );

		return sortedResult;
	}

	// TODO drso: test and implement properly
	public String[] getAvailableSecurityScansNames( TestStep testStep )
	{
		List<String> result = new ArrayList<String>();

		for( SecurityScanFactory securityCheck : availableSecurityChecks.values() )
		{
			if( securityCheck.canCreate( testStep ) )
				result.add( securityCheck.getSecurityScanName() );
		}

		String[] sortedResult = result.toArray( new String[result.size()] );
		Arrays.sort( sortedResult );

		return sortedResult;
	}

	public SecurityConfigurationDialogBuilder getUIBuilder()
	{
		return new SecurityConfigurationDialogBuilder();
	}

	public String getSecurityScanTypeForName( String name )
	{
		return securityCheckNames.get( name );
	}

}