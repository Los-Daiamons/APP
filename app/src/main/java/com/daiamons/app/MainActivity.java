package com.daiamons.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private WebSocketManager webSocketManager;
    private EditText messageEditText;
    private EditText ipEditText;
    private TextView mobileConnectionsTextView;
    private TextView desktopConnectionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectButton = findViewById(R.id.button);
        Button sendButton = findViewById(R.id.button2);
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
    public void actualizarTextView(String numMobiles, String numDesktops) {
        runOnUiThread(() -> {
            mobileConnectionsTextView.setText("Mobile Connections: " + numMobiles);
            desktopConnectionsTextView.setText("Desktop Connections: " + numDesktops);
        });
    }
}



