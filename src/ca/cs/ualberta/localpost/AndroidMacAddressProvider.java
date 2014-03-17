package ca.cs.ualberta.localpost;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiManager;

public class AndroidMacAddressProvider extends Application implements MacAddressProvider {

	public String getMacAddress() {
		Context context = this.getApplicationContext();
	    WifiManager wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	    String macAddress = wimanager.getConnectionInfo().getMacAddress();
	    if (macAddress == null) {
	        macAddress = "Device don't have mac address or wi-fi is disabled";
	    }
	    return macAddress;
	}

}
