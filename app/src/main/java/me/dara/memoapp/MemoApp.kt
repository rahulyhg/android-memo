package me.dara.memoapp

import android.app.Application
import me.dara.memoapp.network.MemoService

/**
 * @author sardor
 */
class MemoApp : Application() {

  lateinit var appModule : AppModule

  override fun onCreate() {
    super.onCreate()
    val webService = MemoService()
    appModule = AppModule()

  }

}