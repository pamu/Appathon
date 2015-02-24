package actors

import akka.actor.{ActorLogging, Actor}

/**
 * Created by android on 24/2/15.
 */
object CountingActor {
  case class Hit(ip: String)
  case object Hits
}

class CountingActor extends Actor with ActorLogging {
  var hits = 0
  import CountingActor._
  def receive = {
    case Hit(ip) => {
      hits += 1
      log.info("hit from {}", ip)
    }
    case Hits => {
      sender ! hits
    }
  }
}
