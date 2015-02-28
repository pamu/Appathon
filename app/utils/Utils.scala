package utils

/**
 * Created by android on 1/3/15.
 */
object Utils {
  def mailBody(line: String): String  =
    s"""
       |<pre style="font-family: monospace;">
       |
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
       |For  Rules and Regulations, visit - www.apptitude.co.in/rules.
       |
       |<strong style="color: red;">${line}</strong>
       |
       |Finally we will be thankful to you, if you can like our fb page https://www.facebook.com/apptitude15
       |
       |Twitter handle @Apptitude2015 and Our Twitter page is here https://twitter.com/Apptitude2015
       |
       |Cheers!
       |<strong>Team Apptitude</strong>
       |
       |</pre>
     """.stripMargin
}

