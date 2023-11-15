package com.daiamons.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private WebSocketManager webSocketManager;
    private EditText messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectButton = findViewById(R.id.button);
        Button sendButton = findViewById(R.id.button2);
        messageEditText = findViewById(R.id.editText);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String connectionName = "app";
                String serverUrl = "ws://192.168.211.1:8887";
                connectWebSocket(serverUrl, connectionName);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void connectWebSocket(String serverUrl, String connectionName) {
        try {
            webSocketManager = new WebSocketManager(new URI(serverUrl));
            webSocketManager.setConnectionName(connectionName);
            webSocketManager.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        if (webSocketManager != null && webSocketManager.isOpen()) {
            String message = messageEditText.getText().toString();
            webSocketManager.send(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocketManager != null) {
            webSocketManager.close();
        }
    }
}



