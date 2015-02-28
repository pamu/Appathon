package constants

/**
 * Created by android on 26/2/15.
 */
object Constants {
  
  val apptitudeEmail = "apptitude2015@gmail.com"
  
  def mailBody(line: String): String  =
    s"""
      |<pre>
      |<strong>Hi Developer,</strong>
      |
      |Welcome to the APPtitude community. 
      |
      |
      |Event Timeline :-
      |
      |March 5th : Registrations Open
      |
      |March 15th : Final Day for Registration & Idea Submission
      |
      |March 16th : Result Announcement of the First Round
      |
      |March 28th : Final Day for App Submission : Second Round
      |
      |March 30th : Announcement of the finalists for App Expo.
      |
      |April 4th : Showcasing of App : Final Round . ( Physical Presence not necessary. Thank you Google, for developing Hangout )
      |
      |April 4th : Result Announcement
      |
      |
      |For  Rules and Regulations, visit - www.apptitude.co.in/rules.
      |
      |You will be reminded when the registrations open. Till then keep developing Apps.
      |
      |$line
      |
      |Cheers!
      |<strong>Team Apptitude</strong>
      |</pre>
     """.stripMargin
}
