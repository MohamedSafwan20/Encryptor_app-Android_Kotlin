package com.microcodes.encryptor.utils

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.microcodes.encryptor.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Db(private val db: FirebaseFirestore = Firebase.firestore) {

    fun checkUserExists(deviceId: String): QuerySnapshot {
        lateinit var res: QuerySnapshot
        runBlocking(Dispatchers.IO) {
            res = Tasks.await(
                db.collection("users")
                    .whereEqualTo("deviceId", deviceId)
                    .limit(1)
                    .get()
            )
        }
        return res
    }

    fun registerUser(deviceId: String, password: String): String {
        val user = User(deviceId, password)

        lateinit var res:
                DocumentReference
        runBlocking(Dispatchers.IO) {
            res = Tasks.await(
                db.collection("users")
                    .add(user)
            )
        }
        return res.id
    }

    fun loginUser(deviceId: String, password: String): Boolean {
        lateinit var res:
                QuerySnapshot

        runBlocking(Dispatchers.IO) {
            res = Tasks.await(
                db.collection("users")
                    .whereEqualTo("deviceId", deviceId)
                    .get()
            )
        }
        return res.documents[0]["password"] == password
    }

    fun saveEncryption(message: String, data: String, deviceId: String): Boolean {
        lateinit var res:
                QuerySnapshot

        runBlocking(Dispatchers.IO) {
            res = Tasks.await(
                db.collection("users")
                    .whereEqualTo("deviceId", deviceId)
                    .get().addOnSuccessListener {
                        db.collection("users")
                            .document(it!!.documents[0].id)
                            .update("encryptions", FieldValue.arrayUnion(mapOf("message" to message, "value" to data)))
                    }
            )

        }
        return res.size() > 0
    }
}