package ro.pub.cs.systems.pdsd.practicaltest02;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server extends Thread {
    private int port;
    private ServerSocket serverSocket;
    private HashMap<String, DictionaryData> data;

    public HashMap<String, DictionaryData> getData() {
        return data;
    }

    public void setData(HashMap<String, DictionaryData> data) {
        this.data = data;
    }

    public Server(int port) {
        this.port = port;
        this.data = new HashMap<>();
        try {
            this.serverSocket = new ServerSocket(port);
            Log.v(Constants.TAG, "Serverul pornit pe portul : " + port);
        } catch (IOException e) {
            Log.e(Constants.TAG, "Serverul nu a putut fi pornit");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Log.v(Constants.TAG, "Serverul asteapta un client ...");
            try {
                Socket newClientSocket = serverSocket.accept();
                Log.v(Constants.TAG, "Serverul accepta clientul cu ip-ul " + newClientSocket.getInetAddress() + " si portul: " + newClientSocket.getLocalPort());

                CommunicationThread communicationThread = new CommunicationThread(newClientSocket, this);
                communicationThread.start();
            } catch (IOException e) {
                Log.e(Constants.TAG, "Serverul nu a putut accepta clientul");
                e.printStackTrace();
            }
        }
        try {
            serverSocket.close();
            Log.v(Constants.TAG, "Serverul a fost inchis");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(Constants.TAG, "Eroare la inchiderea serverului");
        }
    }
}
