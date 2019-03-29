package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class PracticalTest01Var02PlayActivity extends AppCompatActivity {

    EditText guessTxt = null;
    EditText scoreTxt = null;
    Button generateBtn = null;
    Button checkBtn = null;
    Button backBtn = null;

    Integer numberReceived = null;


    private IntentFilter intentFilter = new IntentFilter();
    MessageBroadcastReceiver messageBroadcastReceiver = null;
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        EditText guessTxt = null;
        public MessageBroadcastReceiver(EditText guessTxt) {
            this.guessTxt = guessTxt;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            Log.d("Practical", data);
            guessTxt.setText(data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_play);

        intentFilter.addAction("action.practicaltest01");


        guessTxt = findViewById(R.id.guess_txt);
        scoreTxt = findViewById(R.id.score_txt);
        generateBtn = findViewById(R.id.generate_btn);
        checkBtn = findViewById(R.id.check_btn);
        backBtn = findViewById(R.id.back_btn);
        messageBroadcastReceiver = new MessageBroadcastReceiver(guessTxt);

        if(savedInstanceState != null) {
            if(savedInstanceState.getString("guess") != null) {
                guessTxt.setText(savedInstanceState.getString("guess"));
            }
            if(savedInstanceState.getString("score") != null) {
                scoreTxt.setText(savedInstanceState.getString("score"));
            }
        }

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras().containsKey("number")) {
                String numberRecv = intent.getStringExtra("number");
                numberReceived = Integer.parseInt(numberRecv);

                Toast.makeText(getApplicationContext(), "Number received: " + numberRecv, Toast.LENGTH_LONG).show();
            }
        }

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int digit = Math.abs(new Random().nextInt() % 10);
                guessTxt.setText(String.valueOf(digit));
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = guessTxt.getText().toString();
                if (guess.length() == 1) {
                    int guessNumber = Integer.parseInt(guess);
                    Toast.makeText(getApplicationContext(), "Number received: " + numberReceived, Toast.LENGTH_LONG).show();


                    if (numberReceived == guessNumber) {
                        int currentScore = Integer.parseInt(scoreTxt.getText().toString());

                        scoreTxt.setText(String.valueOf(currentScore + 1));
                    }

                }
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent serviceIntent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
        getApplicationContext().startService(serviceIntent);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getString("guess") != null) {
            guessTxt.setText(savedInstanceState.getString("guess"));
        }
        if(savedInstanceState.getString("score") != null) {
            scoreTxt.setText(savedInstanceState.getString("score"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("guess", guessTxt.getText().toString());

        outState.putString("score", scoreTxt.getText().toString());
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var06Service.class);
        stopService(intent);
        super.onDestroy();
    }
}
