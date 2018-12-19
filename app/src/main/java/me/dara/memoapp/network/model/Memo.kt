package me.dara.memoapp.network.model

/**
 * @author sardor
 */
class Memo(val filePath: String, val title: String,
           val description: String, val todo: List<String>, val date: Long) {

  // Needed for Firebase database
  constructor() : this("", "", "", mutableListOf<String>(), 0L)
}