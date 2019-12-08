package com.homapps.kotlin_firebase_samplesolutions.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val userColRef: CollectionReference
        get() = firestoreInstance.collection("users")


     fun addUserToDatabase(name: String)
     {
         val user= hashMapOf("name" to name)
         userColRef.add(user)
     }

}