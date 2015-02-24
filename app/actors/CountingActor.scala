package actors

import akka.actor.{ActorLogging, Actor}

/**
 * Created by android on 24/2/15.
 */
object CountingActor {
  case class Hit(ip: String)
}

class CountingActor extends Actor with ActorLogging {
  import CountingActor._
  def receive = {
    case Hit(ip) => {
      log.info("hit from {}", ip)
    }
  }
}
