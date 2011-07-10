package com.eviware.soapui.security;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.eviware.soapui.config.ExecutionStrategyConfig;
import com.eviware.soapui.config.StrategyTypeConfig;
import com.eviware.soapui.config.StrategyTypeConfig.Enum;

public class ExecutionStrategyHolder
{

	private ExecutionStrategyConfig config;
	private PropertyChangeSupport pcs = new PropertyChangeSupport( this );

	public ExecutionStrategyHolder( ExecutionStrategyConfig executionStrategy )
	{
		this.config = executionStrategy;
	}

	public Enum getStrategy()
	{
		return config.getStrategy();
	}

	public int getDelay()
	{
		return config.getDelay();
	}

	public void setDelay( int delay )
	{
		int oldValue = config.getDelay();
		config.setDelay( delay );

		pcs.firePropertyChange( "delay", oldValue, delay );
	}

	public void setStrategy( StrategyTypeConfig.Enum strategy )
	{
		Enum oldValue = config.getStrategy();
		config.setStrategy( strategy );

		pcs.firePropertyChange( "strategy", oldValue, strategy );
	}

	public void addPropertyChangeListener( PropertyChangeListener listener )
	{
		pcs.addPropertyChangeListener( listener );
	}

	public void addPropertyChangeListener( String propertyName, PropertyChangeListener listener )
	{
		pcs.addPropertyChangeListener( propertyName, listener );
	}

	public void removePropertyChangeListener( PropertyChangeListener listener )
	{
		pcs.removePropertyChangeListener( listener );
	}

	public void removePropertyChangeListener( String propertyName, PropertyChangeListener listener )
	{
		pcs.removePropertyChangeListener( propertyName, listener );
	}

	public void updateConfig( ExecutionStrategyConfig config )
	{
		this.config = config;
	}

	public Boolean getImmutable()
	{
		return config.getImmutable();
	}

	public void setImmutable( Boolean immutable )
	{
		config.setImmutable( immutable );
	}

}
