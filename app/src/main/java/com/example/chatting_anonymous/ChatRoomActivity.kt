package com.example.chatting_anonymous

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatting_anonymous.Model.ChatLeftYou
import com.example.chatting_anonymous.Model.ChatRightMe
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    private lateinit var auth :FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        //파이어베이스 초기화
        auth = FirebaseAuth.getInstance()

        val myUid = auth.uid
        val yourUid = intent.getStringExtra("yourUid")
        val name = intent.getStringExtra("name")

        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatLeftYou())
        adapter.add(ChatRightMe())
        adapter.add(ChatLeftYou())
        adapter.add(ChatRightMe())
        adapter.add(ChatLeftYou())
        adapter.add(ChatRightMe())

        recyclerView_chat.adapter = adapter
    }
}