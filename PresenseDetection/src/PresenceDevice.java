import java.io.*;
import java.awt.*;

import org.cybergarage.upnp.*;
import org.cybergarage.upnp.device.*;
import org.cybergarage.upnp.control.*;

public class PresenceDevice extends Device implements ActionListener, QueryListener{
	private final static String DESCRIPTION_FILE_NAME = "description/description.xml";

	private StateVariable powerVar;
	private StateVariable presenceVar;

	public PresenceDevice() throws InvalidDescriptionException
	{
		super(new File(DESCRIPTION_FILE_NAME));

		Action getPowerAction = getAction("GetPower");
		getPowerAction.setActionListener(this);
		
		Action setPowerAction = getAction("SetPower");
		setPowerAction.setActionListener(this);
		
		Action getPresenceAction = getAction("GetPresence");
		getPowerAction.setActionListener(this);
		
		Action setPresenceAction = getAction("SetPresence");
		setPowerAction.setActionListener(this);
		
		ServiceList serviceList = getServiceList();
		Service service = serviceList.getService(0);
		service.setQueryListener(this);

		powerVar = getStateVariable("Power");
		presenceVar = getStateVariable("Presence");
		
		Argument powerArg = getPowerAction.getArgument("Power");
		StateVariable powerState = powerArg.getRelatedStateVariable();
		AllowedValueList allowList = powerState.getAllowedValueList();
		for (int n=0; n<allowList.size(); n++) 
			System.out.println("[" + n + "] = " + allowList.getAllowedValue(n));
			
		AllowedValueRange allowRange = powerState.getAllowedValueRange();
		System.out.println("maximum = " + allowRange.getMaximum());
		System.out.println("minimum = " + allowRange.getMinimum());
		System.out.println("step = " + allowRange.getStep());
		
		Argument presenceArg = getPresenceAction.getArgument("Presence");
		StateVariable presenceState = presenceArg.getRelatedStateVariable();
		AllowedValueList allowList_2 = presenceState.getAllowedValueList();
		for (int n=0; n<allowList_2.size(); n++) 
			System.out.println("[" + n + "] = " + allowList_2.getAllowedValue(n));
			
		AllowedValueRange allowRange_2 = presenceState.getAllowedValueRange();
		System.out.println("maximum = " + allowRange_2.getMaximum());
		System.out.println("minimum = " + allowRange_2.getMinimum());
		System.out.println("step = " + allowRange_2.getStep());

	}

	////////////////////////////////////////////////
	//	Affichage
	////////////////////////////////////////////////
	public void afficherInfo(){
		
	}
	
	////////////////////////////////////////////////
	//	Component
	////////////////////////////////////////////////

	private Component comp;
	
	public void setComponent(Component comp)
	{
		this.comp = comp;	
	}
	
	public Component getComponent()
	{
		return comp;
	}
	
	////////////////////////////////////////////////
	//	on/off
	////////////////////////////////////////////////

	private boolean onFlag = false;
	private boolean presenceFlag = false;
	
	public void on()
	{
		onFlag = true;
		powerVar.setValue("on");
	}

	public void off()
	{
		onFlag = false;
		powerVar.setValue("off");
	}

	public boolean isOn()
	{
		return onFlag;
	}
	
	public void present()
	{
		presenceFlag = true;
		presenceVar.setValue("yes");
	}
	
	public void notpresent()
	{
		presenceFlag = false;
		presenceVar.setValue("no");
	}
	
	public boolean isPresent()
	{
		return presenceFlag;
	}
	
	public void setPowerState(String state)
	{
		if (state == null) {
			off();
			return;
		}
		if (state.compareTo("1") == 0) {
			on();
			return;
		}
		if (state.compareTo("0") == 0) {
			off();
			return;
		}
	}
	
	public String getPowerState()
	{
		if (onFlag == true)
			return "1";
		return "0";
	}
	
	public void setPresenceState(String state)
	{
		if (state == null) {
			notpresent();
			return;
		}
		if (state.compareTo("1") == 0) {
			present();
			return;
		}
		if (state.compareTo("0") == 0) {
			notpresent();
			return;
		}
	}
	
	public String getPresenceState()
	{
		if (presenceFlag == true)
			return "1";
		return "0";
	}

	////////////////////////////////////////////////
	// ActionListener
	////////////////////////////////////////////////

	public boolean actionControlReceived(Action action)
	{
		String actionName = action.getName();

		boolean ret = false;
		
		if (actionName.equals("GetPower") == true) {
			String state = getPowerState();
			Argument powerArg = action.getArgument("Power");
			powerArg.setValue(state);
			ret = true;
		}
		if (actionName.equals("SetPower") == true) {
			Argument powerArg = action.getArgument("Power");
			String state = powerArg.getValue();
			setPowerState(state);
			state = getPowerState();
			Argument resultArg = action.getArgument("Result");
			resultArg.setValue(state);
			ret = true;
		}
		if (actionName.equals("GetPresence") == true) {
			String state = getPresenceState();
			Argument presenceArg = action.getArgument("Presence");
			presenceArg.setValue(state);
			ret = true;
		}
		if (actionName.equals("SetPresence") == true) {
			Argument presenceArg = action.getArgument("Presence");
			String state = presenceArg.getValue();
			setPresenceState(state);
			state = getPresenceState();
			Argument resultArg = action.getArgument("Result");
			resultArg.setValue(state);
			ret = true;
		}

		comp.repaint();

		return ret;
	}

	////////////////////////////////////////////////
	// QueryListener
	////////////////////////////////////////////////

	public boolean queryControlReceived(StateVariable stateVar)
	{
		stateVar.setValue(getPowerState());
		stateVar.setValue(getPresenceState());
		return true;
	}

	////////////////////////////////////////////////
	// update
	////////////////////////////////////////////////

	public void update()
	{
	}			
}
