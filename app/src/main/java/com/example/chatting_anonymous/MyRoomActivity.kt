package com.example.chatting_anonymous

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting_anonymous.Model.Useritem
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat_list.*

class MyRoomActivity : AppCompatActivity() {

    private val TAG=ChatListActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_room)

//        db.collection("message-user-list")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    adapter.add(Useritem(document.get("username").toString(), document.get("uid").toString()))
//                    //     Log.d(TAG, document.get("username").toString())
//                    //     Log.d(TAG, "${document.id} => ${document.data}")
//                }
//
//                recyclerview_list.adapter = adapter
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
    }
}