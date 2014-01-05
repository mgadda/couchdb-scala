package com.github.mgadda.couchdb_scala.util

object JSON {
  def apply(anything: Any): JSON = {
    anything match {
      case map: Map[String, _] => new JSONObject(map)
      case list: List[_] => new JSONArray(list)
      case num: Double => new JSONDouble(num)
      case num: Int => new JSONInt(num)
      case str: String => new JSONString(str)
      case tuple2: Tuple2[_,_] =>
        new JSONArray(tuple2.productIterator.toList)
      case tuple3: Tuple3[_,_,_] =>
        new JSONArray(tuple3.productIterator.toList)
      case tuple4: Tuple4[_,_,_,_] =>
        new JSONArray(tuple4.productIterator.toList)
      case tuple5: Tuple5[_,_,_,_,_] =>
        new JSONArray(tuple5.productIterator.toList)
      case tuple6: Tuple6[_,_,_,_,_,_] =>
        new JSONArray(tuple6.productIterator.toList)
      case tuple7: Tuple7[_,_,_,_,_,_,_] =>
        new JSONArray(tuple7.productIterator.toList)
      case tuple8: Tuple8[_,_,_,_,_,_,_,_] =>
        new JSONArray(tuple8.productIterator.toList)
      case tuple9: Tuple9[_,_,_,_,_,_,_,_,_] =>
        new JSONArray(tuple9.productIterator.toList)
      case tuple10: Tuple10[_,_,_,_,_,_,_,_,_,_] =>
        new JSONArray(tuple10.productIterator.toList)
      case None => new JSONNull
    }
  }
}

sealed abstract class JSON

class JSONObject(obj: Map[String, _]) extends JSON {
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

class JSONNull extends JSON {
  override def toString = "null"
}