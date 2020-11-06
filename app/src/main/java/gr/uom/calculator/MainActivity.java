package gr.uom.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView operation;
    private Double op1 = null;
    private Double op2 = null;
    private String pendingOperation = "=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.resulttxt);
        newNumber = findViewById(R.id.newNumbertxt);
        operation = findViewById(R.id.operationLabel);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button buttonMinus = findViewById(R.id.buttonminus);
        Button buttonPlus = findViewById(R.id.buttonplus);
        Button buttonEq = findViewById(R.id.buttoneq);
        Button buttonDot = findViewById(R.id.buttondot);
        Button buttonClear = findViewById(R.id.buttonclear);

        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button)v;
                newNumber.append(buttonClicked.getText());
            }
        };

        button0.setOnClickListener(listener1);
        button1.setOnClickListener(listener1);
        button2.setOnClickListener(listener1);
        button3.setOnClickListener(listener1);
        button4.setOnClickListener(listener1);
        buttonDot.setOnClickListener(listener1);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNumber.setText("");
                pendingOperation = "=";
            }
        });

        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                String operationString = b.getText().toString();
                String newValueString = newNumber.getText().toString();

                try{
                    Double newValueDouble = Double.valueOf(newValueString);

                    performOperation(newValueDouble, operation);
                }catch (NumberFormatException nfe){
                    newNumber.setText("");
                }
                pendingOperation = operationString;
                operation.setText((pendingOperation));

            }
        };

        buttonPlus.setOnClickListener(operationListener);
        buttonMinus.setOnClickListener(operationListener);
        buttonEq.setOnClickListener(operationListener);
    }

    private void performOperation(Double newValueDouble, TextView operation) {
        if (op1 == null){
            op1 = newValueDouble;

        }
        else{
            op2 = newValueDouble;

            switch (pendingOperation){
                case "=":
                    op1 = op2;
                    break;
                case "+":
                    op1 += op2;
                    break;
                case "-":
                    op1 -= op2;
                    break;
            }

        }

        result.setText(op1.toString());
        newNumber.setText("");

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString("PENDING_OPERATION", pendingOperation);

        if (op1 != null){
            outState.putDouble("OPERAND1_STATE", op1);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        pendingOperation = savedInstanceState.getString("PENDING_OPERATION");
        op1 = savedInstanceState.getDouble("OPERAND1_STATE");
        operation.setText(pendingOperation);
    }
}