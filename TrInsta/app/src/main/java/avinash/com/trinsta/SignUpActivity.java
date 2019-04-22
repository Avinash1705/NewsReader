package avinash.com.trinsta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "raw";
    private FirebaseAuth mAuth;
    EditText email,password,name;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        myRef=database.getReference();
        myRef.setValue("Hello World");

        email=findViewById(R.id.emailEditText);
        password=findViewById(R.id.passwordEditText);
        name=findViewById(R.id.nameEditText);
    }

    public void signUpAccount(View view) {

        String emaild=email.getText().toString();
        String passwordd=password.getText().toString();
        mAuth.createUserWithEmailAndPassword(emaild,passwordd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "Email pass created");
                    FirebaseUser user = mAuth.getCurrentUser();
                   // updateUI(user);
                    startActivity(new Intent(getApplicationContext(),CustomImageCreate.class));

                }else{
                    Log.d(TAG, "failed creation");
                }
            }
        });

    }
}
