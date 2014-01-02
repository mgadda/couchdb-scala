package com.github.mgadda.couchdb_scala

import scala.util.parsing.json.JSON
import scala.collection.mutable
import com.twitter.util.Eval

object ViewServer {
//  type ViewFunction = Function1
//  val viewFunctions = mutable.MutableList[ViewFunction]

  def reset(): String = {
    // TODO: delete saved view functions
    "true"
  }

  def add_fun(fun: String) = {
    // TODO: add view function
    val evaldFun = new Eval()(fun)
    "true"
      //["add_fun", "def map(doc: Map[String, Any]) { if (doc(\"score\").asInstanceOf[Int] > 50) { (None, Map(\"player_name\" -> doc(\"name\"))) } }"]


  }

  def map_doc(doc: Map[String, Any]): List[List[Any]] = {
    List(List())
  }

  def reduce(reduceFuns: List[String], mapResults: List[Any]): List[Any] = {
    List("true", List(33))
  }

  def rereduce(rereduceFuns: List[String], reduceValues: List[Any]): List[Any] = {
    List("true", List(154))
  }

  def log(msg: String) = List("log", msg)
}
