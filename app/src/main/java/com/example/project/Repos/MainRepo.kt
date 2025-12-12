package com.example.project.Repos

import android.R
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.project.data.BannerModel
import com.example.project.data.CategoryModel
import com.example.project.data.ItemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainRepo {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    fun saveItem(item: ItemModel) {
        val ref = firebaseDatabase.getReference("Items")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newId = snapshot.childrenCount.toInt() + 10
                val itemWithId = item.copy(Id = newId)
                ref.child(newId.toString()).setValue(itemWithId)
                
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun updateItem(item: ItemModel) {
        val ref = firebaseDatabase.getReference("Items")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val Id = item.Id

                ref.child(Id.toString()).setValue(item)
            }

            override fun onCancelled(error: DatabaseError) {
                // handle error
            }
        })
    }
    fun deleteItem(id: Int) {
        val ref = firebaseDatabase.getReference("Items")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref.child(id.toString()).removeValue()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    fun getCategories(callback: (List<CategoryModel>) -> Unit) {
        val categoriesRef = firebaseDatabase.getReference("Category")
        categoriesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categories = snapshot.children.mapNotNull {
                    it.getValue(CategoryModel::class.java)
                }
                callback(categories)
            }

            override fun onCancelled(error: DatabaseError) {

                callback(emptyList())
            }
        })
    }
    fun saveCategory(category: CategoryModel) {
        val ref = firebaseDatabase.getReference("Category")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newId = snapshot.childrenCount.toInt() + 10
                val categoryWithId = category.copy(id = newId)
                ref.child(newId.toString()).setValue(categoryWithId)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    fun deleteCategory(id: Int) {
        val ref = firebaseDatabase.getReference("Category")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref.child(id.toString()).removeValue()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        val listData = MutableLiveData<MutableList<BannerModel>>()
        val ref = firebaseDatabase.getReference("Banner")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<BannerModel>()
                for (CHsnap in snapshot.children) {
                    val item = CHsnap.getValue(BannerModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return listData
    }
    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        val listData = MutableLiveData<MutableList<CategoryModel>>()
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()
                for (CHsnap in snapshot.children) {
                    val item = CHsnap.getValue(CategoryModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return listData
    }
    fun loadPopular(): LiveData<MutableList<ItemModel>> {
        val listData = MutableLiveData<MutableList<ItemModel>>()
        val ref = firebaseDatabase.getReference("Popular")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ItemModel>()
                for (CHsnap in snapshot.children) {
                    val item = CHsnap.getValue(ItemModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return listData
    }
    fun loadItems(): LiveData<MutableList<ItemModel>> {
        val listData = MutableLiveData<MutableList<ItemModel>>()
        val ref = firebaseDatabase.getReference("Items")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ItemModel>()
                for (CHsnap in snapshot.children) {
                    val item = CHsnap.getValue(ItemModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        return listData
    }
    fun loadItemsPerCategory(categoryId: String): LiveData<MutableList<ItemModel>> {
        val itemsLiveData = MutableLiveData<MutableList<ItemModel>>()
        val ref = firebaseDatabase.getReference("Items")
        val query: Query = ref.orderByChild("categoryId").equalTo(categoryId)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ItemModel>()
                for (CHsnap in snapshot.children) {
                    val item = CHsnap.getValue(ItemModel::class.java)
                    item?.let { list.add(it) }
                }
                itemsLiveData.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return itemsLiveData
    }


}
