package com.example.inventory.viewmodel

import androidx.lifecycle.*
import com.example.inventory.data.Item
import com.example.inventory.data.ItemRepository
import kotlinx.coroutines.launch


class InventoryViewModel(private val repository: ItemRepository): ViewModel() {

    val allItems: LiveData<List<Item>> = repository.getAllItems().asLiveData()

    private fun insertItem(item: Item){
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }

    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String) : Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem)
    }

    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String):Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()){
            return false
        }
        return true
    }


    private fun updateItem(item: Item){
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

    private fun getUpdateItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) : Item {
        return Item(
            id = itemId,
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) {
        val udatedItem = getUpdateItemEntry(itemId, itemName, itemPrice, itemCount)
        updateItem(udatedItem)
    }

    fun deleteItem(item: Item){
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return repository.getItem(id).asLiveData()
    }

    fun sellItem(item: Item){
        if(item.quantityInStock > 0){
            val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
            updateItem(newItem)
        }
    }

    fun isStockAvaialable(item: Item) : Boolean {
        return item.quantityInStock > 0
    }
}

class InventoryViewModelFactory(private val repository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}