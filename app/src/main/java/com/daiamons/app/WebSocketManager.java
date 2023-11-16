package com.daiamons.app;

import android.app.Activity;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketManager extends WebSocketClient {

    private TextView connectionInfoTextView; // Nuevo campo
    private Map<WebSocket, String> connectionNames = new ConcurrentHashMap<>();

    private String connectionName;
    private TextView mobileConnectionsTextView;
    private TextView desktopConnectionsTextView;
    private Gson gson = new Gson();
    private Activity activity;  // o Context si prefieres





    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public WebSocketManager(URI serverUri, TextView mobileConnectionsTextView, TextView desktopConnectionsTextView) {
        super(serverUri);
        this.mobileConnectionsTextView = mobileConnectionsTextView;
        this.desktopConnectionsTextView = desktopConnectionsTextView;
        this.activity = activity;

    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
    }

    @Override
    public void onMessage(String message) {
        Map<String, Object> data = parseJson(message);

        if (data != null && data.containsKey("type") && data.get("type").equals("connection_count")) {
            int mobileConnections = Integer.parseInt(data.get("mobile_connections").toString());
            int desktopConnections = Integer.parseInt(data.get("desktop_connections").toString());

            updateUI(mobileConnections, desktopConnections);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
    }

    @Override
    public void onError(Exception ex) {
    }

    private void updateAndSendConnectionCount() {
        int mobileConnections = 0;
        int desktopConnections = 0;

        for (String name : connectionNames.values()) {
            if (name.contains("?name=mobile")) {
                mobileConnections++;
            } else if (name.contains("?name=desktop")) {
                desktopConnections++;
            }
        }
        String connectionInfoText = "Conexiones móviles: " + mobileConnections +
                "\nConexiones de escritorio: " + desktopConnections;

        if (connectionInfoTextView != null) {
            connectionInfoTextView.setText(connectionInfoText);
        }

        String message = "{\"type\": \"connection_count\", \"mobile_connections\": " + mobileConnections +
                ", \"desktop_connections\": " + desktopConnections + "}";
        broadcast(message);
    }
    private void broadcast(String message) {
        for (WebSocket connection : connectionNames.keySet()) {
            connection.send(message);
        }    }


    private Map<String, Object> parseJson(String json) {
        try {
            return gson.fromJson(json, Map.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateUI(int mobileConnections, int desktopConnections) {

        if (activity != null) {

        activity.runOnUiThread(() -> {
            if (mobileConnectionsTextView != null) {
                mobileConnectionsTextView.setText("Mobile Connections: " + mobileConnections);
            }
            if (desktopConnectionsTextView != null) {
                desktopConnectionsTextView.setText("Desktop Connections: " + desktopConnections);
            }
        });
    }}


}