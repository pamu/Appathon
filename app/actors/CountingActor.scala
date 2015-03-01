package actors

import java.util.Date

import akka.actor.Status.{Failure, Success}
import akka.actor.{ActorLogging, Actor}
import scala.concurrent.Future
import akka.pattern.pipe
import java.sql.Timestamp

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
        DAO.save(Hit(hits, new Timestamp(new Date().getTime)))
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
