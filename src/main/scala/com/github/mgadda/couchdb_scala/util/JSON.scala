package com.github.mgadda.couchdb_scala.util

object JSON {
  def apply(obj: Any): JSON = obj match {
    case list: List[Any] => JSON(list)
    case map: Map[String, Any] => JSON(map)
    case num: Double => JSON(num)
    case num: Int => JSON(num)
    case str: String => JSON(str)
    case _ => JSON(obj.asInstanceOf[String])
  }

  def apply(obj: Map[String, Any]): JSON = new JSONObject(obj)
  def apply(list: List[Any]): JSON = new JSONArray(list)
  def apply(str: String): JSON = new JSONString(str)
  def apply(num: Double): JSON = new JSONDouble(num)
  def apply(num: Int): JSON = new JSONInt(num)

}

sealed abstract class JSON

class JSONObject(obj: Map[String, Any]) extends JSON {
  override def toString = {
    val keyVals = obj.map {
      case(key, value) => s""""$key": ${JSON(value).toString}"""
    }.mkString(",")
    "{" + keyVals + "}"
  }
}

class JSONArray(list: List[Any]) extends JSON {
  override def toString = {
    val stringifiedList = list map(JSON(_).toString) mkString(", ")
    "[" + stringifiedList + "]"
  }
}

class JSONString(str: String) extends JSON {
  override def toString = "\"" + str + "\""
}

class JSONDouble(num: Double) extends JSON {
  override def toString = num.toString
}

class JSONInt(num: Int) extends JSON {
  override def toString = num.toString
}