package com.example.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class CalcFragment extends Fragment {

    Button addButton;
    Button subtractButton;
    Button multiplyButton;
    Button divideButton;

    EditText num1Text;
    EditText num2Text;
    TextView resultText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calc, container, false);

        addButton = (Button) v.findViewById(R.id.addButton);
        subtractButton = (Button) v.findViewById(R.id.subtractButton);
        multiplyButton = (Button) v.findViewById(R.id.multiplyButton);
        divideButton = (Button) v.findViewById(R.id.divideButton);

        num1Text = (EditText) v.findViewById(R.id.num1Text);
        num2Text = (EditText) v.findViewById(R.id.num2Text);
        resultText = (TextView) v.findViewById(R.id.resultText);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                int num1 = Integer.parseInt(num1Text.getText().toString());
                int num2 = Integer.parseInt(num2Text.getText().toString());
                int sum = num1 + num2;
                resultText.setText(String.valueOf(sum));
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                int num1 = Integer.parseInt(num1Text.getText().toString());
                int num2 = Integer.parseInt(num2Text.getText().toString());
                int difference = num1 - num2;
                resultText.setText(String.valueOf(difference));
            }
        });

        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                int num1 = Integer.parseInt(num1Text.getText().toString());
                int num2 = Integer.parseInt(num2Text.getText().toString());
                int product = num1 * num2;
                resultText.setText(String.valueOf(product));
            }
        });

        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                int quotient;
                int num1 = Integer.parseInt(num1Text.getText().toString());
                int num2 = Integer.parseInt(num2Text.getText().toString());

                if(num1%num2==0) {
                    quotient = num1 / num2;
                    resultText.setText(String.valueOf(quotient));
                }
                else {
                    resultText.setText("Not divisible!");
                }


            }
        });

        return v;
    }
}
