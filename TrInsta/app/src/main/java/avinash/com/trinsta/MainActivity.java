package avinash.com.trinsta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        currentUser = mAuth.getCurrentUser();
//        // startActivity(new Intent(getApplicationContext(),CustomImageCreate.class));
//        //updateUI(currentUser);
//        if(currentUser!=null){
//            startActivity(new Intent(getApplicationContext(),CustomImageCreate.class));
//        }
//    }



    public void loginAccount(View view) {

        startActivity(new Intent(this,LoginActivity.class));
    }

    public void newAccount(View view) {

        startActivity(new Intent(this,SignUpActivity.class));
    }
}
