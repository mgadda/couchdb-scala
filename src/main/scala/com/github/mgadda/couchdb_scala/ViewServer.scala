package com.github.mgadda.couchdb_scala

import scala.collection.mutable
import com.twitter.util.Eval

object ViewServer {
  val viewFunctions = mutable.MutableList[ViewFunction]()

  def reset(): String = {
    viewFunctions.clear()
    "true"
  }

  // Example:
  // ["add_fun", "(doc: Map[String, Any]) => { if (doc(\"score\").asInstanceOf[Double] > 50) { Some(List((None, Map(\"player_name\" -> doc(\"name\"))))) } else { None } }"]
  def addFunction(fun: String): Either[Map[String, String], String] = {

    val funWithImports =
      s"""
        |import com.github.mgadda.couchdb_scala._
        |$fun
      """.stripMargin

    val evaler = new Eval()
    try {
      viewFunctions += evaler[ViewFunction](funWithImports)
      Right("true")
    }
    catch {
      case e: Exception =>
        Left(Map("error" -> "1", "reason" -> (e.getMessage + "\n" +  e.getStackTraceString)))
    }
  }

  // Example:
  // ["map_doc", {"_id":"8877AFF9789988EE","_rev":"3-235256484","name":"John Smith","score": 60}]
  def mapDocument(doc: Document): List[Option[List[MapResult]]] = {
    viewFunctions.map(_(doc)).toList
  }

  // ["reduce",["function(k, v) { return sum(v); }"],[[[1,"699b524273605d5d3e9d4fd0ff2cb272"],10],[[2,"c081d0f69c13d2ce2050d684c7ba2843"],20],[[null,"foobar"],3]]]
  def reduce(reduceFuns: List[String], mapResults: List[Any]): List[Any] = {
    List("true", List(33))
  }

  def rereduce(rereduceFuns: List[String], reduceValues: List[Any]): List[Any] = {
    List("true", List(154))
  }

  def log(msg: String) = List("log", msg)


}
