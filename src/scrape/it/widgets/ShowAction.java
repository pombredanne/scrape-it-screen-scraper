package scrape.it.widgets;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

class ShowAction extends AbstractAction {
	  Component parentComponent;

	  public ShowAction(Component pc) {
		    super("Login");
		    this.parentComponent = pc;
	}

	public void actionPerformed(ActionEvent actionEvent) {
	    Runnable runnable = new Runnable() {
	      public void run() {
	        MyLoginForm lf = new MyLoginForm();
	
	      }
	    };
	    EventQueue.invokeLater(runnable);
	  }
	}
