package com.example.recyclerview

class Friend (val name: String,
              val slackId: String,
              val home: String,
              val email: String,
              val phone: String) {
    override fun toString() = name
}
