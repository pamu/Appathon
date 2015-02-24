package global

import actors.{EmailActor, CountingActor}
import akka.actor.Props
import play.api.{Logger, GlobalSettings, Application}
import play.libs.Akka

/**
 * Created by android on 24/2/15.
 */
object AppathonGlobal extends GlobalSettings {
  
  lazy val appathonSystem = Akka.system()
  lazy val counter = appathonSystem.actorOf(Props[CountingActor])
  lazy val mailer = appathonSystem.actorOf(Props[EmailActor])
  
  override def onStart(app: Application): Unit = {
    super.onStart(app)
    Logger.info("Appathon started")
  }
  
  override def onStop(app: Application): Unit = {
    super.onStop(app)
    appathonSystem.shutdown()
    Logger.info("Appathon Stopped")
  }
}
