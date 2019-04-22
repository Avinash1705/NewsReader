package avinash.com.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView exp;
    Button showbt,hidebt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showbt=(Button) findViewById(R.id.show_bt);
        hidebt=(Button) findViewById(R.id.hide_bt);
        exp=(TextView) findViewById(R.id.experiment_tv);

//
//        public void show (View view){
//            exp.setVisibility();
//        }


    }

    public void hide(View view) {
        exp.setVisibility(View.INVISIBLE);
    }

    public void show(View view) {
        exp.setVisibility(View.VISIBLE);
    }
}
