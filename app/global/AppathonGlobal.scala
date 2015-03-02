package global

import actors.{PersistenceActor, EmailActor, CountingActor}
import akka.actor.Props
import play.api.{Logger, GlobalSettings, Application}
import play.libs.Akka

/**
 * Created by android on 24/2/15.
 */
object AppathonGlobal extends GlobalSettings {
  
  lazy val appathonSystem = Akka.system()
  lazy val counter = appathonSystem.actorOf(Props[CountingActor], "CounterActor")
  lazy val mailer = appathonSystem.actorOf(Props[EmailActor], "EmailActor")
  lazy val persist = appathonSystem.actorOf(Props[PersistenceActor], "PersistenceActor")
  
  override def onStart(app: Application): Unit = {
    super.onStart(app)
    Logger.info("Appathon started")

    models.DAO.createIfNotExists()

  }
  
  override def onStop(app: Application): Unit = {
    super.onStop(app)
    appathonSystem.shutdown()
    Logger.info("Appathon Stopped")
  }
}
