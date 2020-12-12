package com.example.chatting_anonymous

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting_anonymous.Model.ChatLeftYou
import com.example.chatting_anonymous.Model.ChatModel
import com.example.chatting_anonymous.Model.ChatNewModel
import com.example.chatting_anonymous.Model.ChatRightMe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
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
//        val db = FirebaseFirestore.getInstance()
//
//
//        //데이터 불러오기
//        db.collection("message")
//                .orderBy("time")
//                .get()
//                .addOnSuccessListener { result->
//                    for(document in result){
//                        Log.d(TAG,document.toString())
//
//                        val senderUid = document.get("myUid")
//                        val msg = document.get("message").toString()
//
//                        //만약 내가 보낸 메세지일때
//                        if (senderUid!!.equals(myUid)){
//                            adapter.add(ChatRightMe(msg))
//
//                        }
//                        //만약 내가 보낸 메세지가 아닐때
//                        else{
//                            adapter.add(ChatLeftYou(msg))
//
//                        }
//
//                    }
//
//                    recyclerView_chat.adapter = adapter
//
//                }

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        val readRef = database.getReference("message").child(myUid.toString()).child(yourUid.toString())

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "snapshot : "+snapshot)
                val model = snapshot.getValue(ChatNewModel::class.java)
                val msg = model?.message.toString()
                val who = model?.who

                if(who =="me"){
                    adapter.add(ChatRightMe(msg))
                }else{
                    adapter.add(ChatLeftYou(msg))
                }


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        recyclerView_chat.adapter = adapter
        readRef.addChildEventListener(childEventListener)

        val myRef_list=database.getReference("message-user-list")

        button.setOnClickListener {

            val message = editTextTextPersonName.text.toString()

            //보낸사람
            val chat = ChatNewModel(myUid.toString(), yourUid.toString(),message, System.currentTimeMillis(),"me")
            myRef.child(myUid.toString()).child(yourUid.toString()).push().setValue(chat)

            //받는사람
            val chat_get = ChatNewModel(yourUid.toString(), myUid.toString(),message, System.currentTimeMillis(),"you")
            myRef.child(yourUid.toString()).child(myUid.toString()).push().setValue(chat_get)

            //나와 대화한 사람 마지막 메세지 db업데이트
            myRef_list.child(myUid.toString()).child(yourUid.toString()).setValue(chat)

            editTextTextPersonName.setText("")

//            val message = editTextTextPersonName.text.toString()
//            editTextTextPersonName.setText("")
//
//            val chat = ChatModel(myUid.toString(), yourUid.toString(),message, System.currentTimeMillis())
//
//            db.collection("message")
//                    .add(chat)
//                    .addOnSuccessListener {
//                        Log.d(TAG,"성공")
//                    }
//                    .addOnFailureListener {
//                        Log.d(TAG,"실패")
//
//                    }


        }
    }
}