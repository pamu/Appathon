package global

import play.api.{Logger, GlobalSettings, Application}

/**
 * Created by android on 24/2/15.
 */
object AppathonGlobal extends GlobalSettings {
  override def onStart(app: Application): Unit = {
    Logger.info("Appathon started")
  }
  override def onStop(app: Application): Unit = {
    Logger.info("Appathon Stopped")
  }
}
