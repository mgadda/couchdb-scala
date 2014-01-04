package com.github.mgadda.couchdb_scala

import scala.collection.mutable
import com.twitter.util.Eval

object ViewServer {
    type ViewFunction = (Map[String, Any]) => List[(Option[Any], Any)]
    val viewFunctions = mutable.MutableList[ViewFunction]()

  def reset(): String = {
    viewFunctions.clear()
    "true"
  }

  // val cmd = "[\"add_fun\", \"def map(doc: Map[String, Any]) { if (doc(\\\"score\\\").asInstanceOf[Double] > 50) { List((None, Map(\\\"player_name\\\" -> doc(\\\"name\\\")))) } }\"]"
  // ["add_fun", "(doc: Map[String, Any]) => { if (doc(\"score\").asInstanceOf[Double] > 50) { List((None, Map(\"player_name\" -> doc(\"name\")))) } }"]
  def add_fun(fun: String): Either[Map[String, String], String] = {
    val evaler = new Eval()
    try {
      viewFunctions += evaler.applyProcessed[ViewFunction](fun, false)
      Right("true")
    }
    catch {
      case e: Exception =>
        Left(Map("error" -> "1", "reason" -> (e.getMessage + "\n" +  e.getStackTraceString)))
    }
  }

  // ["map_doc", {"_id":"8877AFF9789988EE","_rev":"3-235256484","name":"John Smith","score": 60}]
  def map_doc(doc: Map[String, Any]): List[List[Any]] = {
    //When the view function is stored in the view server, CouchDB starts sending in all the documents in the database, one at a time. The view server calls the previously stored functions one after another with the document and stores its result. When all functions have been called, the result is returned as a JSON string.
    //An array with the result for every function for the given document.

    viewFunctions.map(fun => fun(doc)).toList
  }

  def reduce(reduceFuns: List[String], mapResults: List[Any]): List[Any] = {
    List("true", List(33))
  }

  def rereduce(rereduceFuns: List[String], reduceValues: List[Any]): List[Any] = {
    List("true", List(154))
  }

  def log(msg: String) = List("log", msg)


}
