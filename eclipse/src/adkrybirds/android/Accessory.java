package adkrybirds.android;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.android.future.usb.UsbAccessory;
import com.android.future.usb.UsbManager;

public class Accessory {
    private List<Listener> listeners = new ArrayList<Listener>();
    private byte degrees = 90;

    private UsbAccessory accessory;
    private FileInputStream input;
    private FileOutputStream output;
    private UsbManager usbManager;
    private FileDescriptor fileDescriptor;
    private ParcelFileDescriptor parcelDescriptor;

    public Accessory(Context context) {
        usbManager = UsbManager.getInstance(context);
    }
    
    public void addListener(Listener listener) {
	listeners.add(listener);
    }
    
    public void removeListener(Listener listener) {
	listeners.remove(listener);
    }
    
    public void aim(byte degrees) {
	try {
	    output.write(new byte[] { 0x02, 0x02, (byte) (90-degrees) });
	    output.flush();
	    this.degrees = degrees;
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
    
    public void shoot() {
	try {
	    output.write(new byte[] { 0x02,  0x01, degrees });
	    output.flush();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
    
    public interface Listener {
	void onAttached();
	void onDetached();
    }

    public void processIntent(Intent intent) {
	if (UsbManager.ACTION_USB_ACCESSORY_ATTACHED.equals(intent.getAction())) {
            onAccessoryAttached(intent);
        } else if(UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(intent.getAction())) {
            onAccessoryDetached(intent);
        }
    }
    
    private void onAccessoryAttached(Intent intent) {
	accessory = UsbManager.getAccessory(intent);
	Log.w("accessory", "Accessory: " + accessory);
	parcelDescriptor = usbManager.openAccessory(accessory);
	fileDescriptor = parcelDescriptor.getFileDescriptor();
	input = new FileInputStream(fileDescriptor);
	output = new FileOutputStream(fileDescriptor);
	for (Listener listener : listeners) {
	    listener.onAttached();
	}
    }
    
    private void onAccessoryDetached(Intent intent) {
	try {
	    parcelDescriptor.close();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	} finally {
		for (Listener listener : listeners) {
		    listener.onDetached();
		}
	}
    }
}
