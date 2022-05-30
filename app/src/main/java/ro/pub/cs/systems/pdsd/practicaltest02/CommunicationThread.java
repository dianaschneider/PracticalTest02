package ro.pub.cs.systems.pdsd.practicaltest02;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class CommunicationThread extends Thread{
    private Socket socket;
    private Server server;

    public CommunicationThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            Log.v(Constants.TAG, "Comunicatia cu clientul a inceput pe un thread separat ");
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            String word = bufferedReader.readLine();
            String serverResponse = "";
            Log.v(Constants.TAG, "Clientul a cerut " + word);

            if (server.getData().containsKey(word)) {
                serverResponse = server.getData().get(word).getWordDefinition();
            }
            else {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(Constants.SERVICE_BASE_URL + word);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                if (httpEntity != null) {
                    String response = EntityUtils.toString(httpEntity);
                    JSONArray content = new JSONArray(response);
                    JSONObject object = content.getJSONObject(0);
                    JSONArray meanings = object.getJSONArray("meanings");
                    JSONObject definition = meanings.getJSONObject(0).getJSONArray("definitions").getJSONObject(0);
                    String desiredData = definition.getString("definition");
                    Log.v(Constants.TAG, "Preluat de la remote dictionary: " + desiredData);

                    DictionaryData genericResult = new DictionaryData(desiredData);
                    HashMap dataAux = server.getData();
                    dataAux.put(word, genericResult);
                    server.setData(dataAux);

                    /*save response to send back to client*/
                    serverResponse = desiredData;
                } else {
                    Log.e(Constants.TAG, "Null response de la remote server");
                }
            }
            Log.v(Constants.TAG, "Serverul trimite la client: " + serverResponse);
            printWriter.println(serverResponse);

            socket.close();
            Log.v(Constants.TAG, "Close communication with client");
        } catch (IOException | JSONException e) {
            Log.e(Constants.TAG, "Comunicatia cu clientul a fost intrerupta ");
            e.printStackTrace();
        }
    }
}
