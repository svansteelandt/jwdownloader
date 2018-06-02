package be.sylvainvansteelandt.projects.jwdownloader;

import java.io.IOException;

import jmtp.PortableDevice;
import jmtp.PortableDeviceManager;

public class ConnectionTester {
	
	public static boolean testMP3PlayerConnectivity(){
		PortableDeviceManager manager = new PortableDeviceManager();
		
		for(PortableDevice device : manager){
			device.open();
			if(device.getModel().equalsIgnoreCase("WALKMAN NWZ-E585")){
				return true;
			}
		}
		return false;
	}
	
	public static boolean testInternetConnectivity() throws IOException, InterruptedException{
		Process p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 www.jw.org");
	    int returnVal = p1.waitFor();
	    boolean reachable = (returnVal==0);
	    return reachable;
	}

}
