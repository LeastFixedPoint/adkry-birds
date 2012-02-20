package adkrybirds.android;

import adkrybirds.android.Accessory.Listener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Roman Shemshei.
 * User: admin
 * Date: 18.02.12
 * Time: 8:15
 * To change this template use File | Settings | File Templates.
 */
public class AdkryBirdsActivity extends Activity implements Listener {
    private Accessory accessory;
    private byte degrees = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        accessory = new Accessory(this);
        accessory.addListener(this);
        
        setContentView(R.layout.main);
        
        ((SeekBar)findViewById(R.id.angle_seek)).setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	    @Override
	    public void onStopTrackingTouch(SeekBar seekBar) {
	    }
	    
	    @Override
	    public void onStartTrackingTouch(SeekBar seekBar) {
	    }
	    
	    @Override
	    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		degrees = (byte) (progress + 50);
		accessory.aim(degrees);
		((TextView)findViewById(R.id.angle_text)).setText(String.valueOf(degrees));
	    }
	});

        findViewById(R.id.kill).setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		accessory.shoot();
	    }
	});

        accessory.processIntent(getIntent());
    }
/*    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        
        Toast.makeText(this, getIntent().toString(), Toast.LENGTH_SHORT).show();
        accessory.processIntent(intent);
    }
*/
    @Override
    public void onAttached() {
	Toast.makeText(this, "Attached", Toast.LENGTH_SHORT).show();
	findViewById(R.id.angle_seek).setEnabled(true);
	findViewById(R.id.kill).setEnabled(true);
        accessory.aim(degrees);
    }

    @Override
    public void onDetached() {
	Toast.makeText(this, "Detached", Toast.LENGTH_SHORT).show();
	findViewById(R.id.angle_seek).setEnabled(false);
	findViewById(R.id.kill).setEnabled(false);
	findViewById(R.id.kill).setEnabled(false);
    }
}
