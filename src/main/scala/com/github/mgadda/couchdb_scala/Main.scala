package com.github.mgadda.couchdb_scala

import scala.util.parsing.json.JSON
import com.github.mgadda.couchdb_scala.util.{JSON => DeepJSON}
import com.twitter.logging._
import com.twitter.logging.config.{FileHandlerConfig, LoggerConfig}
import com.twitter.util.Eval

case class Foo(i: Int)

object Main {
  val logger = Logger.get(getClass)
  logger.clearHandlers()
//  val config = new LoggerConfig {
//    node = ""
//    level = Logger.INFO
//    handlers = new FileHandlerConfig {
//      filename = "/tmp/couchdb-scala.log"
//      roll = Policy.Hourly
//    }
//  }
//  LoggerFactory
//
//  config()
  //logger.addHandler(new FileHandler())
//  logger.addHandler(new FileHandler("/tmp/couchdb-scala.log", Policy.Hourly, true, 5, BasicFormatter, Logger.ALL))
  LoggerFactory(node = "", level = Some(Level.INFO), handlers = List(FileHandler("/tmp/couchdb-scala.log", Policy.Hourly))).apply()

  //assert( logger.getHandlers().length == 1 )

  def executeCmd(cmd: String): Any = {
    JSON.parseFull(cmd) match {
      case Some(List("add_fun", fun: String)) =>
        ViewServer.add_fun(fun) match {
          case Right(response) => response
          case Left(response) => DeepJSON(response)
        }
      case Some(List("reset", settings)) =>
        ViewServer.reset()
      case Some(List("map_doc", doc: Document)) =>
        val results = ViewServer.map_doc(doc)
        DeepJSON(results)
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
        logger.info(cmd)
        val result: Any = try { executeCmd(cmd) }
        catch {
          case e:Exception => logger.critical(e, cmd)
        }

        logger.info(result.toString)
        println(result.toString)
      }

    }
  }
}
