package com.example.instagram_clone.models

import java.io.Serializable

/*
  _id: ObjectId | string;
  email: string;
  password: string;
  name: string;
  username: string;
  role: string;
  createdAt: Date;
  updatedAt: Date;
  avatar: {
    url: string;
    publicId: string;
  };
  bio: string;
  resetToken: string;
  resetTokenExpiration: Date;

*/

data class User(
    val _id:String,
    val email:String,
    val name:String,
    val username:String,
    val bio:String,
    val role:String,
    val createdAt:String,
    val avatar:Avatar
):Serializable
