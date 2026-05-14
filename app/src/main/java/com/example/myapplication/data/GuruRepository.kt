package com.example.myapplication.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class GuruRepository {
    private val db = FirebaseFirestore.getInstance()
    private val gurusCollection = db.collection("gurus")
    private val sessionsCollection = db.collection("sessions")
    private val appreciationsCollection = db.collection("appreciations")

    fun getGurus(): Flow<List<Guru>> = callbackFlow {
        val subscription = gurusCollection.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val gurus = snapshot.toObjects(Guru::class.java)
                trySend(gurus)
            }
        }
        awaitClose { subscription.remove() }
    }

    suspend fun addGuru(guru: Guru) {
        gurusCollection.document(guru.id).set(guru).await()
    }

    fun getSessions(): Flow<List<Session>> = callbackFlow {
        val subscription = sessionsCollection.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val sessions = snapshot.toObjects(Session::class.java)
                trySend(sessions)
            }
        }
        awaitClose { subscription.remove() }
    }

    fun getAppreciations(guruId: String): Flow<List<Appreciation>> = callbackFlow {
        val subscription = appreciationsCollection
            .whereEqualTo("guruId", guruId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val appreciations = snapshot.toObjects(Appreciation::class.java)
                    trySend(appreciations)
                }
            }
        awaitClose { subscription.remove() }
    }

    suspend fun postAppreciation(appreciation: Appreciation) {
        appreciationsCollection.add(appreciation).await()
        // Increment thank you count in Guru profile
        val guruRef = gurusCollection.document(appreciation.guruId)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(guruRef)
            val currentCount = snapshot.getLong("thankYouCount") ?: 0
            transaction.update(guruRef, "thankYouCount", currentCount + 1)
        }.await()
    }
}
