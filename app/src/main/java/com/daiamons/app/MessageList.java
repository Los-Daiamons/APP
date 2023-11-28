package com.daiamons.app;
import static com.daiamons.app.MainActivity.messages;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MessageList extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        recyclerView = findViewById(R.id.recycleViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(messages, this);
        recyclerView.setAdapter(messageAdapter);
    }

}