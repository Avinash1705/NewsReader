package avinash.com.trinsta;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Queue;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="raw" ;
    EditText email,pass;
    Button login;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.enterEmailEditText);
        pass=findViewById(R.id.enterPassEditText);
        login=findViewById(R.id.loginButton);
        mAuth = FirebaseAuth.getInstance();

        }
    public void veryfingAccount(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null){
            String name = user.getDisplayName();
            String email = user.getEmail();
          //  Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
            Log.i(TAG, "veryfingAccount: Working");
            startActivity(new Intent(getApplicationContext(),CustomImageCreate.class));
        }
    }


    public void loginYourAccount(View view) {

        String Lemaild=email.getText().toString(),Lpassd=pass.getText().toString();

        mAuth.signInWithEmailAndPassword(Lemaild, Lpassd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(getApplicationContext(),"Correct Credencials",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            veryfingAccount();
                            
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
