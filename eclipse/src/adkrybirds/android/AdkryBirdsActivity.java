package adkrybirds.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Roman Shemshei.
 * User: admin
 * Date: 18.02.12
 * Time: 8:15
 * To change this template use File | Settings | File Templates.
 */
public class AdkryBirdsActivity extends Activity {
    private String angleStep;
    private LinearLayout controls;
    private TextView angleValue;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        controls = (LinearLayout) findViewById(R.id.angle);
        angleValue = (TextView) controls.findViewById(R.id.angle_value);
        controls.findViewById(R.id.angle_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO send command
                int spinnerStep = Integer.valueOf(spinner.getSelectedItem().toString());
                Log.d("TESTtest", spinner.getSelectedItem().toString());
            }
        });
        controls.findViewById(R.id.angle_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO send command
                int spinnerStep = Integer.valueOf(spinner.getSelectedItem().toString());
                Log.d("TESTtest", spinner.getSelectedItem().toString());
            }
        });
        spinner = (Spinner) controls.findViewById(R.id.angle_step);
    }
}
