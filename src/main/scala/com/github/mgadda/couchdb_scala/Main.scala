package com.github.mgadda.couchdb_scala

import scala.util.parsing.json.{JSONObject, JSONArray, JSON}


object Main {

  def executeCmd(cmd: String): Any = {
    JSON.parseFull(cmd) match {
      case Some(List("add_fun", fun: String)) =>
        ViewServer.add_fun(fun)
      case Some(List("reset")) =>
        ViewServer.reset()
      case Some(List("map_doc", doc: Map[String, Any])) =>
        ViewServer.map_doc(doc)
      case Some(List("reduce", reduceFuns: List[String], mapResults: List[Any])) =>
        ViewServer.reduce(reduceFuns, mapResults)
      case Some(List("rereduce", rereduceFuns: List[String], reduceValues: List[Any])) =>
        ViewServer.rereduce(rereduceFuns, reduceValues)
      case Some(x) => ViewServer.log("Unknown command: " + x.toString)
      case None => ViewServer.log("Could not parse: " + cmd)
    }
  }
  def main(args: Array[String]): Unit = {
    while(true) {
      for (cmd <- io.Source.stdin.getLines()) {
        val result: Any = executeCmd(cmd)
        val response = result match {
          case _: List[Any] => JSONArray(result.asInstanceOf[List[Any]])
          case _: Map[String, String] => JSONObject(result.asInstanceOf[Map[String, String]])
          case _: String => result
        }

        println(response.toString)
      }

    }
  }
}
