package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var03ChooseNumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_choose_number);

        final EditText numberText = findViewById(R.id.number_txt);
        Button playBtn = findViewById(R.id.play_btn);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numberText.getText().toString();
                if (number.length() == 1) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02PlayActivity.class);
//                    Toast.makeText(getApplicationContext(), "" + number, Toast.LENGTH_LONG).show();
                    intent.putExtra("number", number);
                    startActivity(intent);
                }

            }
        });

    }
}
