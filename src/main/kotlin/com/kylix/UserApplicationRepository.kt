package com.kylix

import com.mongodb.ConnectionString
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase
import kotlinx.coroutines.reactive.awaitFirst
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.reactivestreams.getCollection

class UserApplicationRepository(connectionString: String) {

    val client = KMongo.createClient(ConnectionString(connectionString)).coroutine
    val database = client.getDatabase("usersData")
    val users = database.getCollection<UserApplication>("usersCollection")

    suspend fun save(application: UserApplication) {
        users.insertOne(application)
    }

    suspend fun update(application: UserApplication) {
        users.replaceOne(UserApplication::id eq application.id, application)
    }

    suspend fun findByUserId(userId: Int, application: UserApplication): UserApplication? {
        return users.find(application::id eq userId).first()
    }
}