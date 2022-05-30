package ro.pub.cs.systems.pdsd.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PracticalTest02MainActivity extends AppCompatActivity {

    private EditText serverPortEditText;
    private Button serverStartButton;
    private EditText clientIpEditText;
    private EditText clientPortEditText;
    private EditText clientRequestDataEditText;
    private Spinner clientRequestDataSpinner;
    private Button clientRequestDataButton;
    private TextView requestedDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = (EditText) findViewById(R.id.SERVER_PORT_EDIT_TEXT);
        serverStartButton = (Button) findViewById(R.id.SERVER_START_BUTTON);
        serverStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        clientIpEditText = (EditText) findViewById(R.id.CLIENT_IP_EDIT_TEXT);
        clientPortEditText = (EditText) findViewById(R.id.CLIENT_PORT_EDIT_TEXT);
        clientRequestDataEditText = (EditText) findViewById(R.id.CLIENT_REQUEST_DATA_EDIT_TEXT);

        clientRequestDataButton = (Button) findViewById(R.id.CLIENT_REQUEST_DATA_BUTTON);
        clientRequestDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*[TODO 14]: Ex4.a client requests data when clicked -> start client request data execution on new thread*/

            }
        });

        requestedDataTextView = (TextView) findViewById(R.id.REQUESTED_DATA_TEXT_VIEW);
    }
}