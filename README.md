couchdb-scala
=============

Scala view server for couchdb.

### Defining a Map Function

Map functions are defined as `(Document) => List[Option[List[MapResult]]]` where `Document` is actually just a `Map[String, Any]`.

Example map-only view:

```javascript
{
   "_id": "_design/test",
   "_rev": "15-ca87d46c5f5d5bd396d1d64920fd978a",
   "language": "scala",
   "views": {
       "high_scoring": {
           "map": "(doc: Document) => { if (doc(\"score\").asInstanceOf[Double] > 50) { Some(List(MapResult(None, doc))) } else { None } }"
       }
   }
}
```
