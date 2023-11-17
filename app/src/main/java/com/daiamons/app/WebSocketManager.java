package com.daiamons.app;

import android.app.Activity;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketManager extends WebSocketClient {

    private MainActivity mainActivity;

    public WebSocketManager(URI serverUri, MainActivity mainActivity) {
        super(serverUri);
        this.mainActivity = mainActivity;

    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
    }

    @Override
    public void onMessage(String message) {
        // Manejar el mensaje recibido
        try {
            JSONObject jsonObject = new JSONObject(message);
            String type = jsonObject.getString("type");

            if ("connection_count".equals(type)) {
                int mobileConnections = jsonObject.getInt("mobile_connections");
                int desktopConnections = jsonObject.getInt("desktop_connections");

                // Llamar al método en MainActivity para actualizar los TextView
                mainActivity.actualizarTextView(String.valueOf(mobileConnections), String.valueOf(desktopConnections));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
    }

    @Override
    public void onError(Exception ex) {
    }

}


