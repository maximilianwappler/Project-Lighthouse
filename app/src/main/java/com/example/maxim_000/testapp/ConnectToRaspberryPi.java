package com.example.maxim_000.testapp;

import android.os.AsyncTask;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by maxim_000 on 21.12.2016.
 * Class to Connect the Android-App to the RaspberryPi and send Messages
 */

public class ConnectToRaspberryPi extends AsyncTask<String, String, String> {
    WebsocketClientEndpoint clientEndPoint;

    public void sendMessage (String message){
        // send message to websocket
        clientEndPoint.sendMessage(message);

        // wait 2 seconds for messages from websocket or print exception
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException exception: " + e.getMessage());
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        //this method will be running on background thread so don't update UI frome here
        //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
        try {
            // open websocket
            if (strings[1].isEmpty()){
                clientEndPoint = new WebsocketClientEndpoint(new URI( "ws://192.168.2.104:8080/websocket" ));
            } else {
                clientEndPoint = new WebsocketClientEndpoint(new URI( "ws://" + strings[1] + ":8080/websocket" ));
            }

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                }
            });
            this.sendMessage(strings[0]);
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
        return null;
    }
}
