package actors

import akka.actor.{ActorLogging, Actor}
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
  
  var hits = BigInt(0)
  
  import CountingActor._
  
  def receive = {
  
    case Hit(ip) => {
      hits += 1
      //channel.push(hits toString)
      log.info("hit from {}", ip)
      log.info("no of hits = {}", hits.toString)
    }
  
    case Hits => {
      sender ! hits
    }
      
    /**
    case Stream => {
      sender ! enumerator
    }**/
  
  }
}
