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

package com.eviware.soapui.support.components;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.actions.FindAndReplaceDialog;
import com.eviware.soapui.support.actions.FindAndReplaceable;
import com.eviware.soapui.support.components.JEditorStatusBar.JEditorStatusBarTarget;
import com.eviware.soapui.support.swing.JTextComponentPopupMenu;

/**
 * JTextArea with Undo/Redo keyboard/popup support
 * 
 * @author Ole.Matzura
 */

public class JUndoableTextArea extends JTextArea implements Undoable, UndoableEditListener, FocusListener,
		FindAndReplaceable, JEditorStatusBarTarget
{
	public static final int UNDO_LIMIT = 1500;

	private UndoManager undoManager;
	private boolean discardEditsOnSet = false;
	private FindAndReplaceDialog findAndReplaceAction;

	public JUndoableTextArea()
	{
		super();
		init();
	}

	private void init()
	{
		// setBorder(BorderFactory.createEtchedBorder());
		getDocument().addUndoableEditListener( this );
		addFocusListener( this );

		setMinimumSize( new Dimension( 50, 50 ) );
		addKeyListener( new KeyAdapter()
		{

			public void keyPressed( KeyEvent e )
			{
				if( KeyStroke.getKeyStrokeForEvent( e ).equals( UISupport.getKeyStroke( "menu Z" ) ) )
					undo();
				else if( KeyStroke.getKeyStrokeForEvent( e ).equals( UISupport.getKeyStroke( "menu Y" ) ) )
					redo();
				else if( KeyStroke.getKeyStrokeForEvent( e ).equals( UISupport.getKeyStroke( "menu X" ) ) )
					cut();
				else if( KeyStroke.getKeyStrokeForEvent( e ).equals( UISupport.getKeyStroke( "menu C" ) ) )
					copy();
				else if( KeyStroke.getKeyStrokeForEvent( e ).equals( UISupport.getKeyStroke( "menu V" ) ) )
					paste();
				else if( KeyStroke.getKeyStrokeForEvent( e ).equals( UISupport.getKeyStroke( "F3" ) ) )
					findAndReplace();
				else
					return;

				e.consume();
			}
		} );

		JTextComponentPopupMenu.add( this );
	}

	public JUndoableTextArea( int i, int j )
	{
		super( i, j );
		init();
	}

	public JUndoableTextArea( String text )
	{
		super( text );
		init();
	}

	protected void findAndReplace()
	{
		if( findAndReplaceAction == null )
			findAndReplaceAction = new FindAndReplaceDialog( this );

		findAndReplaceAction.show();
	}

	public void setText( String text )
	{
		ensureUndoManager();
		super.setText( text == null ? "" : text );

		if( discardEditsOnSet && undoManager != null )
			undoManager.discardAllEdits();
	}

	public boolean isDiscardEditsOnSet()
	{
		return discardEditsOnSet;
	}

	public void setDiscardEditsOnSet( boolean discardEditsOnSet )
	{
		this.discardEditsOnSet = discardEditsOnSet;
	}

	public UndoManager getUndoManager()
	{
		return undoManager;
	}

	private void ensureUndoManager()
	{
		if( isEditable() && undoManager == null )
		{
			undoManager = new UndoManager();
			undoManager.setLimit( UNDO_LIMIT );
		}
	}

	public void focusGained( FocusEvent fe )
	{
		ensureUndoManager();
	}

	public void focusLost( FocusEvent fe )
	{
		// removeUndoMananger();
	}

	public void undoableEditHappened( UndoableEditEvent e )
	{
		if( undoManager != null )
			undoManager.addEdit( e.getEdit() );
	}

	public void undo()
	{
		if( !isEditable() )
		{
			getToolkit().beep();
			return;
		}

		try
		{
			if( undoManager != null )
				undoManager.undo();
		}
		catch( CannotUndoException cue )
		{
			Toolkit.getDefaultToolkit().beep();
		}
	}

	public void redo()
	{
		if( !isEditable() )
		{
			getToolkit().beep();
			return;
		}

		try
		{
			if( undoManager != null )
				undoManager.redo();
		}
		catch( CannotRedoException cue )
		{
			Toolkit.getDefaultToolkit().beep();
		}
	}

	public void setSelectedText( String txt )
	{
		replaceSelection( txt );
	}

	public boolean canRedo()
	{
		return undoManager != null && undoManager.canRedo();
	}

	public boolean canUndo()
	{
		return undoManager != null && undoManager.canUndo();
	}
}
