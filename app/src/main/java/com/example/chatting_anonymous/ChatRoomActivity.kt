package com.example.chatting_anonymous

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting_anonymous.Model.ChatLeftYou
import com.example.chatting_anonymous.Model.ChatModel
import com.example.chatting_anonymous.Model.ChatRightMe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    private lateinit var auth :FirebaseAuth
    private val TAG = ChatRoomActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        //파이어베이스 초기화
        auth = FirebaseAuth.getInstance()

        val myUid = auth.uid
        val yourUid = intent.getStringExtra("yourUid")
        val name = intent.getStringExtra("name")

        val adapter = GroupAdapter<GroupieViewHolder>()

//        adapter.add(ChatLeftYou())
//        adapter.add(ChatRightMe())
//        adapter.add(ChatLeftYou())
//        adapter.add(ChatRightMe())
//        adapter.add(ChatLeftYou())
//        adapter.add(ChatRightMe())




        //데이터 베이스 선언
        val db = FirebaseFirestore.getInstance()


        //데이터 불러오기
        db.collection("message")
                .orderBy("time")
                .get()
                .addOnSuccessListener { result->
                    for(document in result){
                        Log.d(TAG,document.toString())

                        val senderUid = document.get("myUid")
                        val msg = document.get("message").toString()

                        //만약 내가 보낸 메세지일때
                        if (senderUid!!.equals(myUid)){
                            adapter.add(ChatRightMe(msg))

                        }
                        //만약 내가 보낸 메세지가 아닐때
                        else{
                            adapter.add(ChatLeftYou(msg))

                        }

                    }

                    recyclerView_chat.adapter = adapter

                }

        button.setOnClickListener {
            val message = editTextTextPersonName.text.toString()
            editTextTextPersonName.setText("")

            val chat = ChatModel(myUid.toString(), yourUid.toString(),message, System.currentTimeMillis())

            db.collection("message")
                    .add(chat)
                    .addOnSuccessListener {
                        Log.d(TAG,"성공")
                    }
                    .addOnFailureListener {
                        Log.d(TAG,"실패")

                    }
        }
    }
}