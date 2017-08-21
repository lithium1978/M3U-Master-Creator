package lithium1978.m3uMasterCreator.gui;

import java.awt.Desktop;
import java.net.URI;

public class DonateButtonLogic {

	public static void openBrowser(){
		try 
		{
			// Create Desktop object
			Desktop d=Desktop.getDesktop();

			// Browse a URL, for example www.facebook.com
			d.browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=GL6CF8TPLGPBG")); 
			// This open facebook.com in your default browser.
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	}
}

