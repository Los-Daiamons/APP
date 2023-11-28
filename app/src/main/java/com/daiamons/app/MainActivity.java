package com.daiamons.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import android.text.TextUtils;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private WebSocketManager webSocketManager;
    private EditText messageEditText;
    private EditText ipEditText;
    private EditText ipEditText2;
    private EditText ipEditText3;
    private TextView mobileConnectionsTextView;
    private TextView desktopConnectionsTextView;
    private Button connectButton;
    private Button sendButton;
    private Button loginButton;
    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = findViewById(R.id.button);
        sendButton = findViewById(R.id.button2);
        messageEditText = findViewById(R.id.editText);
        ipEditText = findViewById(R.id.ipEditText);
        ipEditText2 = findViewById(R.id.ipEditText2);
        ipEditText3 = findViewById(R.id.ipEditText3);
        mobileConnectionsTextView = findViewById(R.id.mobileConnectionsTextView);
        desktopConnectionsTextView = findViewById(R.id.desktopConnectionsTextView);
        loginButton = findViewById(R.id.button4);
        Button messageListButton = findViewById(R.id.button3);

        checkConnectionStatus();
        connectButton.setEnabled(false);
        sendButton.setEnabled(false);
        messageListButton.setEnabled(isConnected);

        ipEditText2.addTextChangedListener(textWatcher);
        ipEditText3.addTextChangedListener(textWatcher);

        messageListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected) {
                    Intent intent = new Intent(MainActivity.this, MessageListActivity.class);
                    startActivity(intent);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String connectionName = "mobile";
                String ipAddress = ipEditText.getText().toString();
                String serverUrl = "ws://" + ipAddress + ":8887?name=" + connectionName;
                connectWebSocket(serverUrl);
                connectButton.setEnabled(true);
                sendButton.setEnabled(true);
            }
        });
    }

    private void checkConnectionStatus() {
        isConnected = webSocketManager != null && webSocketManager.getConnection().isOpen();
    }







































































































































































































































































































        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean credentialsEntered = !TextUtils.isEmpty(ipEditText2.getText().toString().trim()) &&
                    !TextUtils.isEmpty(ipEditText3.getText().toString().trim());
            connectButton.setEnabled(credentialsEntered);
            sendButton.setEnabled(credentialsEntered);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

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


