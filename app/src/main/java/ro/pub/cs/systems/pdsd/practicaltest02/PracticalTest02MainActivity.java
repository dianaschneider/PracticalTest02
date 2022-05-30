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
    private boolean serverStarted = false;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = (EditText) findViewById(R.id.SERVER_PORT_EDIT_TEXT);
        serverStartButton = (Button) findViewById(R.id.SERVER_START_BUTTON);
        serverStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!serverStarted) {
                    if (serverPortEditText.getText() != null || !String.valueOf(serverPortEditText.getText()).equals("")) {
                        serverStarted = true;
                        server = new Server(Integer.parseInt(serverPortEditText.getText().toString()));
                        server.start();
                    } else {
                        Log.e(Constants.TAG, "Portul pentru pornirea serverului trebuie sa fie un numar");
                    }
                } else {
                    Log.v(Constants.TAG, "Serverul deja a fost pornit");
                }
            }
        });

        clientIpEditText = (EditText) findViewById(R.id.CLIENT_IP_EDIT_TEXT);
        clientPortEditText = (EditText) findViewById(R.id.CLIENT_PORT_EDIT_TEXT);
        clientRequestDataEditText = (EditText) findViewById(R.id.CLIENT_REQUEST_DATA_EDIT_TEXT);

        clientRequestDataButton = (Button) findViewById(R.id.CLIENT_REQUEST_DATA_BUTTON);
        clientRequestDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!serverStarted) {
                    Log.e(Constants.TAG, "Serverul nu este pornit");
                } else {
                    int port = Integer.parseInt(clientPortEditText.getText().toString());
                    String ip = clientIpEditText.getText().toString();
                    String word = clientRequestDataEditText.getText().toString();
                    Client client = new Client(port, ip, word, requestedDataTextView);
                    client.start();
                }
            }
        });

        requestedDataTextView = (TextView) findViewById(R.id.REQUESTED_DATA_TEXT_VIEW);
    }
}