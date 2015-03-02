package actors

import akka.actor.Status.{Failure, Success}
import akka.actor.{ActorLogging, Actor}
import scala.concurrent.Future
import akka.pattern.pipe

//import play.api.libs.iteratee.Concurrent

/**
 * Created by android on 24/2/15.
 */
object CountingActor {
  case class Hit(ip: String)
  case object Hits
  //case object Stream
}

class CountingActor extends Actor with ActorLogging {
  
  //val (enumerator, channel) = Concurrent.broadcast[String]
  
  override def preStart(): Unit = {
    try {
      hits = models.DAO.getHits
    }catch {
      case _: Exception => hits = 0L
    }
  }

  import context.dispatcher
  
  var hits = 0L
  
  import CountingActor._
  
  def receive = {
  
    case Hit(ip) => {
      hits += 1
      //channel.push(hits toString)
      log.info("hit from {}", ip)
      log.info("no of hits = {}", hits.toString)
      
      val saveHits = Future {
        import models._
        DAO.updateHits(hits)
      }
      saveHits pipeTo self
    }
  
    case Hits => {
      sender ! hits
    }

    case Success => log.info("successful database update")
    case Failure => log.info("failed database update")
      
    /**
    case Stream => {
      sender ! enumerator
    }**/
  
  }
}
