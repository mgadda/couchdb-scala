package com.github.mgadda

package object couchdb_scala {
  type Document = Map[String, Any]
  val Document = Map

  case class MapResult(key: Option[String], document: Document)
//  {
//    def unapply(mapResult: MapResult): List[Any] =
//      List(mapResult.key, mapResult.document)
//  }

  type ViewFunction = (Document) => Option[List[MapResult]]
}
