package scrape.it.browser;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

public class MyEventListener implements EventListener{

	@Override
	public void handleEvent(Event evt) {
		// TODO Auto-generated method stub
		evt.preventDefault();
	}

}
