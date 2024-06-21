package com.kylix

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

//data class UserApplication(
//    @BsonId
//    val id: ObjectId,
//    var userId: String? = null,
//    var state: String? = null
//)

data class UserApplication(val id: Int, val token: String)
