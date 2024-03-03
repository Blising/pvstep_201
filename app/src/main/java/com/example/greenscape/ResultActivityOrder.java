package com.example.greenscape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
;
import android.widget.TextView;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivityOrder extends AppCompatActivity {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;


    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_order);


        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

        button = findViewById(R.id.btnExitMainMenu);


        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra(OrderInfoUser.KEYORDER);



        tv1.setText("Numbers: " + order.getNumbers());
        tv2.setText("Name: " + order.getName());
        tv3.setText("Poshta: " + order.getPoshta());

        button.setOnClickListener(this::goMenu);


    }

    private void goMenu(View view) {
        Intent intent = new Intent(ResultActivityOrder.this, MenuActivity.class);
        startActivity(intent);
    }
}