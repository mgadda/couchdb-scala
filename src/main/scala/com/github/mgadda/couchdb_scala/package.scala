package com.github.mgadda

package object couchdb_scala {
  type Document = Map[String, Any]
  val Document = Map

  case class MapResult(key: Option[String], document: Document)

  type ViewFunction = (Document) => Option[List[MapResult]]
}
