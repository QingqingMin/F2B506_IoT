import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import org.cybergarage.util.*;
import org.cybergarage.upnp.device.*;

public class PresenceFrame extends JFrame implements WindowListener{
	private final static String TITLE = "CyberLink Sample Light";
	private PresenceDevice presenceDev;
	private PresencePane presencePane;
	
	public PresenceFrame()
	{
		super(TITLE);

		try {
			presenceDev = new PresenceDevice();
		}
		catch (InvalidDescriptionException e) {
			Debug.warning(e);
		}
				
		getContentPane().setLayout(new BorderLayout());

		presencePane = new PresencePane();
		presencePane.setDevice(presenceDev);
		presenceDev.setComponent(presencePane);
		getContentPane().add(presencePane, BorderLayout.CENTER);

		addWindowListener(this);
		
		pack();
		setVisible(true);
		
		presenceDev.start();
	}

	public PresencePane getClockPanel()
	{
		return presencePane;
	}

	public PresenceDevice getClockDevice()
	{
		return presenceDev;
	}
		
	////////////////////////////////////////////////
	//	main
	////////////////////////////////////////////////
	
	public void windowActivated(WindowEvent e) 
	{
	}
	
	public void windowClosed(WindowEvent e) 
	{
	}
	
	public void windowClosing(WindowEvent e) 
	{
		presenceDev.stop();
		System.exit(0);
	}
	
	public void windowDeactivated(WindowEvent e) 
	{
	}
	
	public void windowDeiconified(WindowEvent e) 
	{
	}
	
	public void windowIconified(WindowEvent e) 
	{
	}
	
	public void windowOpened(WindowEvent e)
	{
	}
	

	////////////////////////////////////////////////
	//	main
	////////////////////////////////////////////////

	public static void main(String args[]) 
	{
		//Debug.on();
		PresenceFrame sampClock = new PresenceFrame();
	}
}
