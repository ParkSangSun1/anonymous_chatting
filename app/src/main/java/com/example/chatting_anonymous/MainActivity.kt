package com.example.chatting_anonymous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting_anonymous.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.internal.InternalTokenProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val TAG = MainActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = FirebaseAuth.getInstance()



        login_button_main.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        join_button.setOnClickListener {

            val email=email_area.text.toString()
            val password = password_area.text.toString()

                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->

                                if (task.isSuccessful) {

                                    Log.d(TAG, "성공")

                                    val uid =FirebaseAuth.getInstance().uid ?:""



                                    val user = User(uid, username_area.text.toString())
                                    //여기에서 데이터베이스에 넣음
                                    val db = FirebaseFirestore.getInstance().collection("users")
                                    db.document(uid)
                                            .set(user)
                                            .addOnSuccessListener {
                                                Log.d(TAG, "데이터 베이스 성공")
                                            }
                                            .addOnFailureListener {
                                                Log.d(TAG, "데이터 베이스 실패 $it")
                                            }
                                    val intent = Intent(this,ChatListActivity::class.java)

                                    //전의 액티비티 삭제
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                } else {
                                    Log.d(TAG, "실패")

                                }

                            }
                            .addOnFailureListener {
                                Log.d(TAG, "에러 $it")
                            }

        }
    }
}