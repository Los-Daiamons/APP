package com.daiamons.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WebSocketManager webSocketManager;
    private EditText messageEditText, ipEditText;
    private TextView mobileConnectionsTextView, desktopConnectionsTextView;

    public static List<Message> messages = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectButton = findViewById(R.id.button);
        Button sendButton = findViewById(R.id.button2);
        Button messageListButton = findViewById(R.id.messageListButton);
        messageEditText = findViewById(R.id.editText);
        ipEditText = findViewById(R.id.ipEditText);
        mobileConnectionsTextView = findViewById(R.id.mobileConnectionsTextView);
        desktopConnectionsTextView = findViewById(R.id.desktopConnectionsTextView);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String connectionName = "mobile";
                String ipAddress = ipEditText.getText().toString();
                String serverUrl = "ws://" + ipAddress + ":8887?name=" + connectionName;
                connectWebSocket(serverUrl);
                
            }
        });
        messageListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webSocketManager == null || !webSocketManager.isOpen()) {
                    Toast.makeText(MainActivity.this, "No hay conexión con el servidor.", Toast.LENGTH_SHORT).show();

                    return;
                }
                Intent intent = new Intent(MainActivity.this, MessageList.class);
                startActivity(intent);
            }
        }

        );

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void connectWebSocket(String serverUrl) {
        try {

            webSocketManager = new WebSocketManager(new URI(serverUrl), this);

            webSocketManager.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }



    private void sendMessage() {
        if (webSocketManager != null && webSocketManager.isOpen()) {
            String message = messageEditText.getText().toString();
            if (isMessageAlreadySent(message)) {
                Toast.makeText(this, "Mensaje repetido, no se enviará.", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (message.isEmpty()) {

                Toast.makeText(this, "No se puede enviar un mensaje vacío.", Toast.LENGTH_SHORT).show();
                return;
            }

            webSocketManager.send(message);
            messageEditText.setText("");
            messages.add(new Message(message, System.currentTimeMillis()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocketManager != null) {
            webSocketManager.close();
        }
    }
    public void actualizarTextView(String numMobiles, String numDesktops) {
        runOnUiThread(() -> {
            mobileConnectionsTextView.setText("Mobile Connections: " + numMobiles);
            desktopConnectionsTextView.setText("Desktop Connections: " + numDesktops);
        });
    }
    private boolean isMessageAlreadySent(String message) {
        for (Message msg : messages) {
            if (msg.getText().equals(message)) {
                return true;
            }
        }
        return false;
    }
}



