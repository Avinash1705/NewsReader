package avinash.com.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class WriteNotes extends AppCompatActivity {
    private static final String TAG = "raw";
    int noteId;
    EditText saving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notes);

        saving=findViewById(R.id.savingTextView);

        Intent intent=getIntent();
        noteId=intent.getIntExtra("NoteId",-1);
        Log.i(TAG, "Id CHECK"+noteId);
        try {
            if(noteId != -1){
               saving.setText(MainActivity.notes.get(noteId));
               // saving.setText("Wrong Id Not Exist");
            }
            else{
                MainActivity.notes.add(" Enter the text");
                noteId=MainActivity.notes.size()-1;
                MainActivity.arrayAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onCreate: "+e);
        }
        //on Change of Text wb
        saving.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
