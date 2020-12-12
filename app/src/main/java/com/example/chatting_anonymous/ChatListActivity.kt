package com.example.chatting_anonymous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting_anonymous.Model.Useritem
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_list.*

class ChatListActivity : AppCompatActivity() {

    private val TAG=ChatListActivity::class.java.simpleName
    val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        val adapter = GroupAdapter<GroupieViewHolder>()





        //유저 이름 받아오기기
       db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    adapter.add(Useritem(document.get("username").toString(), document.get("uid").toString()))
               //     Log.d(TAG, document.get("username").toString())
               //     Log.d(TAG, "${document.id} => ${document.data}")
                }

                recyclerview_list.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


        //채팅방으로 들어가기
        adapter.setOnItemClickListener{item, view ->

         //   Log.d(TAG, (item as Useritem).name)
         //   Log.d(TAG, (item as Useritem).uid)
            val uid :String = (item as Useritem).uid
            val name :String = (item as Useritem).name


            val intent = Intent(this, ChatRoomActivity::class.java)
            intent.putExtra("yourUid",uid)
            intent.putExtra("name", name)
            startActivity(intent)


        }


    }
}