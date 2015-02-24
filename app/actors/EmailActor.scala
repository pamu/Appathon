package actors

import akka.actor.Actor

/**
 * Created by android on 24/2/15.
 */
object EmailActor {
  case class Email(from: String, to: String, subject: String, body: String)
}

class EmailActor extends Actor {
  
  import EmailActor._
  
  def receive = {
    
    case Email(from, to, subject, body) => {
      
    }
  
  }
}
