package com.all_the_best.knock_knock.parent.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.all_the_best.knock_knock.parent.mypage.model.ParentMyPageBaby
import com.google.firebase.database.*

class ParentMyPageViewModel : ViewModel() {
    var tempParentMyPageBabyList: List<ParentMyPageBaby> =
        listOf(
            ParentMyPageBaby(null, "연주", "남", "2018.06.13", "쥬쥬"),
            ParentMyPageBaby(null, "지수", "여", "2018.07.13", "슈슈"),
            ParentMyPageBaby(null, "지호", "남", "2018.08.13", "죠죠"),
            ParentMyPageBaby(null, "윤정", "여", "2018.09.13", "졍졍")
        )

    private val _parentMyPageBabyList = MutableLiveData<MutableList<ParentMyPageBaby>>()
    val parentMyPageBabyList: LiveData<MutableList<ParentMyPageBaby>>
        get() = _parentMyPageBabyList

    fun setParentMyPageBabyList() {
        _parentMyPageBabyList.value = tempParentMyPageBabyList.toMutableList()
    }

    fun getDefaultUri() {
        for (i in 0..3) {
            getDefaultUri(i)
        }
    }

    fun getProfileImgFromStorage() {
        getImgFromStorage(0)
        getImgFromStorage(1)
        getImgFromStorage(2)
        getImgFromStorage(3)
    }

    private fun getDefaultUri(listNum: Int) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        // 데이터베이스의 인스턴스를 가져온다고 생각(즉, Root를 가져온다고 이해하면 쉬움)
        val databaseReference: DatabaseReference = database.reference
        val parentId = "부모1"
        val childName = "아이1"
        val myValue: DatabaseReference =
            databaseReference.child(parentId).child(parentId + "의 child " + childName)
                .child("imageUri($listNum)")
        myValue.get().addOnSuccessListener {
            tempParentMyPageBabyList[listNum].uri = it.value.toString()
            if (listNum == 3) {
                setParentMyPageBabyList()
            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }

    private fun getImgFromStorage(listNum: Int) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        // 데이터베이스의 인스턴스를 가져온다고 생각(즉, Root를 가져온다고 이해하면 쉬움)
        val databaseReference: DatabaseReference = database.reference
        val parentId = "부모1"
        val childName = "아이1"
        val myValue: DatabaseReference =
            databaseReference.child(parentId).child(parentId + "의 child " + childName)
                .child("imageUri($listNum)")
        myValue.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tempParentMyPageBabyList[listNum].uri = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}