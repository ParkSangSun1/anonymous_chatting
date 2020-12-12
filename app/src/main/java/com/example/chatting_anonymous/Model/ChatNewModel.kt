package com.example.chatting_anonymous.Model

//디테일하게 하고싶으면 메세지 보낸시간이랑 그런것도 추가 가능
class ChatNewModel(val myUid:String, val yourUid:String, val message:String, val time : Long, val who:String){
    constructor():this("","","",0,"")
}