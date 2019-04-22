package avinash.com.baintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static avinash.com.baintrainer.R.id.go_textView;
import static avinash.com.baintrainer.R.id.resultTextView;
import static avinash.com.baintrainer.R.id.src_over;

public class MainActivity extends AppCompatActivity {
        TextView goTextView,timertv,resulttv,pointstv,sumtv;
        Button b1,b2,b3,b4,playAgainbt;
        int score=0;
        RelativeLayout gameLayout;
        int noofquestions=1;
        ArrayList<Integer> answers=new ArrayList<Integer>();
        int locationofcorrectans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        goTextView=(TextView)findViewById(R.id.go_textView);
        sumtv=findViewById(R.id.sumTextView);
        b1=findViewById(R.id.first_bt);
        b2=findViewById(R.id.sec_bt);
        b3=findViewById(R.id.third_bt);
        b4=findViewById(R.id.fourth_bt);
        resulttv=findViewById(R.id.resultTextView);
        pointstv=findViewById(R.id.pointsTextView);
        timertv=findViewById(R.id.timerTextView);
        playAgainbt=findViewById(R.id.playAgain_button);
        gameLayout=findViewById(R.id.GameRelativelayout);

            generatequestions();
        playAgain(findViewById(R.id.playAgain_button));

    }
//    public  void start(View view){
//        startButton.setVisibility(View.INVISIBLE);
//        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);
//
//        playAgain(findViewById(R.id.playAgainButton));
//    }
    public void hide(View view) {
        goTextView.setVisibility(View.INVISIBLE);
    }

    public void chooseAns(View view) {
       // Log.i("TAG", "chooseAns: "+view.getTag());
        if((view.getTag().toString()).equals(Integer.toString(locationofcorrectans))){
            resulttv.setText("Correct !");
            Log.i("raw", "correct ");
            score++;
            pointstv.setText(Integer.toString(score)+"/"+Integer.toString(noofquestions));
            generatequestions();
            }
        else
        {
            Log.i("raw", "Incorrect");
            resulttv.setText("Wrong !");
        }
        noofquestions++;
    }
    public void generatequestions(){
        Random random=new Random();
        int a=random.nextInt(21);
        int b=random.nextInt(21);
        sumtv.setText(Integer.toString(a)+"+"+Integer.toString(b));

        locationofcorrectans=random.nextInt(4);
        answers.clear();
        int incorrectanswer;
        for(int i=0;i<4;i++){
            if(i == locationofcorrectans){
                answers.add(a+b);
            }
            else{
                incorrectanswer = random.nextInt(41);
                while(incorrectanswer == a+b){
                    incorrectanswer = random.nextInt(41);
                }
                answers.add(incorrectanswer);
            }
        }
        b1.setText(Integer.toString(answers.get(0)));
        b2.setText(Integer.toString(answers.get(1)));
        b3.setText(Integer.toString(answers.get(2)));
        b4.setText(Integer.toString(answers.get(3)));
    }

    public void playAgain(View view) {
        score=0;
        noofquestions=0;
        timertv.setText("30 s");
        resulttv.setText("");
        pointstv.setText("0/0");
        playAgainbt.setVisibility(View.INVISIBLE);
        //gameLayout.setVisibility(View.VISIBLE);
        new CountDownTimer(30100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timertv.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                timertv.setText("Done");
                resulttv.setText("YOur Score"+Integer.toString(score)+"/"+Integer.toString(noofquestions));
                playAgainbt.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}
