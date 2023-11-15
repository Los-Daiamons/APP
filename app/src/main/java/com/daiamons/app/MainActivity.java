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
    private EditText ipEditText; // Nuevo campo para la dirección IP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectButton = findViewById(R.id.button);
        Button sendButton = findViewById(R.id.button2);
        messageEditText = findViewById(R.id.editText);
        ipEditText = findViewById(R.id.ipEditText); // Referencia al nuevo EditText

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String connectionName = "mobile";
                String ipAddress = ipEditText.getText().toString(); // Obtener la dirección IP ingresada
                String serverUrl = "ws://" + ipAddress + ":8887?name=" + connectionName; // Crear la URL con la dirección IP";
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



