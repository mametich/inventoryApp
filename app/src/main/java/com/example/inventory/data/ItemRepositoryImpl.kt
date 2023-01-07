package com.example.inventory.data

import javax.inject.Inject

class ItemRepository @Inject constructor(private val itemDao: ItemDao)  {

 fun getAllItems() = itemDao.getItems()

 fun getItem(id: Int) = itemDao.getItem(id)

 suspend fun deleteItem(item: Item) = itemDao.delete(item)

 suspend fun updateItem(item: Item) = itemDao.update(item)

 suspend fun insertItem(item: Item) = itemDao.insert(item)
}