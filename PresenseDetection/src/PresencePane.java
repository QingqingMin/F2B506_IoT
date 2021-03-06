import java.io.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.ImageIO;

import org.cybergarage.util.*;

public class PresencePane extends JPanel{
	////////////////////////////////////////////////
	//	Images
	////////////////////////////////////////////////
	
	private final static String LIGHT_ON_PANEL_IMAGE = "images/P.png";
	private final static String LIGHT_OFF_PANEL_IMAGE = "images/notP.png";
	
	////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////
	
	public PresencePane()
	{
		loadImage(LIGHT_OFF_PANEL_IMAGE);
		initPanel();
	}
	
	////////////////////////////////////////////////
	//	Background
	////////////////////////////////////////////////
	
	private BufferedImage panelmage;
	
	private void loadImage(String finename)
	{
		File f = new File(finename);
		try {
			panelmage = ImageIO.read(f);
		}
		catch (Exception e) {
			Debug.warning(e);
		}
	}
	
	private BufferedImage getPaneImage()
	{
		return panelmage;
	}
	
	////////////////////////////////////////////////
	//	Background
	////////////////////////////////////////////////
	
	private void initPanel()
	{
		BufferedImage panelmage = getPaneImage();
		setPreferredSize(new Dimension(panelmage.getWidth(), panelmage.getHeight()));
	}
	
	////////////////////////////////////////////////
	//	PresenceDevice
	////////////////////////////////////////////////
	
	private PresenceDevice PresenceDev = null;
	
	public void setDevice(PresenceDevice dev)
	{
		PresenceDev = dev;
	}
	
	public PresenceDevice getDevice()
	{
		return PresenceDev;
	}
	
	////////////////////////////////////////////////
	//	paint
	////////////////////////////////////////////////
	
	private void clear(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.clearRect(0, 0, getWidth(), getHeight());
	}
	
	private void drawPanelImage(Graphics g)
	{
		g.drawImage(getPaneImage(), 0, 0, null);
	}
	
	public void paint(Graphics g)
	{
		PresenceDevice dev = getDevice();
		if (dev.isOn() == true)
		loadImage(LIGHT_ON_PANEL_IMAGE);
		else
		loadImage(LIGHT_OFF_PANEL_IMAGE);
		
		clear(g);
		drawPanelImage(g);
	}
}
