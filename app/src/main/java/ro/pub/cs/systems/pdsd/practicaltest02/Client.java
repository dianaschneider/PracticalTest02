package ro.pub.cs.systems.pdsd.practicaltest02;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{
    private int port;
    private String ip;
    private String word;
    private TextView textView;

    public Client(int port, String ip, String word, TextView textView) {
        this.port = port;
        this.ip = ip;
        this.word = word;
        this.textView = textView;
        Log.v(Constants.TAG, "Client conectat la server la ip: " + ip + " pe port: " + port + " transmite " + word);
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(ip, port);

            //obtinere canale de comunicatie cu serverul
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            //transmitere date la server
            printWriter.println(word);
            Log.v(Constants.TAG, "Clientul trimite: " + word);

            //primire raspuns de la server
            String response = bufferedReader.readLine();
            Log.v(Constants.TAG, "Clientul primeste: " + response);

            //afisare raspuns in textview
            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(textView.getText() + "\n" + response);
                }
            });
        } catch (IOException e) {
            Log.e(Constants.TAG, "Clientul nu s-a conectat la server");
        }
    }
}
