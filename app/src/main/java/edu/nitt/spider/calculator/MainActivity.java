package edu.nitt.spider.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView resultBox;

    CalculatorParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultBox = (TextView) findViewById(R.id.resultbox);
        parser = new CalculatorParser();
    }

    public void enterSymbol(View v) {
        Button symbolButton = (Button) v;
        CharSequence symbol = symbolButton.getText();
        setOutput(symbol);
    }

    private void setOutput(CharSequence symbol) {
        String content = resultBox.getText().toString();
        content += symbol.toString();
        resultBox.setText(content);
    }

    public void calculate(View v) {
        // calculate the value
        String result = parser.parse(resultBox.getText().toString());
        resultBox.setText(result);
    }

    public void clear(View v) {
        resultBox.setText("");
    }
}
