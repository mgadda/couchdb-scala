package com.github.mgadda.couchdb_scala

import scala.util.parsing.json.JSON
import com.github.mgadda.couchdb_scala.util.{JSON => DeepJSON}

object Main {
  def executeCmd(cmd: String): Any = {
    JSON.parseFull(cmd) match {
      case Some(List("add_fun", fun: String)) =>
        ViewServer.add_fun(fun) match {
          case Right(response) => response
          case Left(response) => DeepJSON(response)
        }
      case Some(List("reset", settings)) =>
        ViewServer.reset()
      case Some(List("map_doc", doc: Map[String, Any])) =>
        DeepJSON(ViewServer.map_doc(doc))
      case Some(List("reduce", reduceFuns: List[String], mapResults: List[Any])) =>
        DeepJSON(ViewServer.reduce(reduceFuns, mapResults))
      case Some(List("rereduce", rereduceFuns: List[String], reduceValues: List[Any])) =>
        DeepJSON(ViewServer.rereduce(rereduceFuns, reduceValues))
      case Some(x) => DeepJSON(ViewServer.log("Unknown command: " + x.toString))
      case None => DeepJSON(ViewServer.log("Could not parse: " + cmd))
    }
  }
  def main(args: Array[String]): Unit = {
    while(true) {
      for (cmd <- io.Source.stdin.getLines()) {
        val result: Any = executeCmd(cmd)

        println(result.toString)
      }

    }
  }
}
