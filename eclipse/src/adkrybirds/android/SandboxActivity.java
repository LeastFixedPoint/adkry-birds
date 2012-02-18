package adkrybirds.android;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.TextView;

import com.android.future.usb.UsbAccessory;
import com.android.future.usb.UsbManager;

public class SandboxActivity extends Activity implements Runnable {
    private TextView statusView;
    private UsbAccessory accessory;
    private FileInputStream input;
    private FileOutputStream output;
    private UsbManager usbManager;
    private FileDescriptor fileDescriptor;
    private ParcelFileDescriptor parcelDescriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        usbManager = UsbManager.getInstance(this);
        
        setContentView(R.layout.main);
        statusView = (TextView) findViewById(R.id.status);
        statusView.setText("Created");

        processIntent(getIntent());  
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);  
    }

    private void processIntent(Intent intent) {
	if (UsbManager.ACTION_USB_ACCESSORY_ATTACHED.equals(intent.getAction())) {
            onAccessoryAttached(intent);
        } else if(UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(intent.getAction())) {
            onAccessoryDetached(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    private void onAccessoryAttached(Intent intent) {
	accessory = UsbManager.getAccessory(intent);
	parcelDescriptor = usbManager.openAccessory(accessory);
	fileDescriptor = parcelDescriptor.getFileDescriptor();
	input = new FileInputStream(fileDescriptor);
	output = new FileOutputStream(fileDescriptor);
	
	statusView.setText("Connected");
    }
    
    private void onAccessoryDetached(Intent intent) {
	try {
	    parcelDescriptor.close();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	} finally {
	    statusView.setText("Disconnected");
	}
    }

    @Override
    public void run() {
	
    }
}
