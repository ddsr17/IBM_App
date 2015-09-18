package controllers

import com.mongodb.casbah.Imports._
import reactivemongo.bson.BSONObjectID

/**
 * Created by deepanshu on 17/9/15.
 */
object mongo {


  def convertDbObjectToStock(obj: MongoDBObject): Person = {
    val price = obj.getAs[BSONObjectID]("id").get
    val symbol = obj.getAs[String]("title").get
    val one = obj.getAs[String]("url").get
    val two = obj.getAs[String]("source").get
    val three = obj.getAs[String]("location").get
    val four = obj.getAs[String]("extracted_text").get
    val five = obj.getAs[String]("date").get
    Person(price, symbol,one,two,three,four,five)
  }

  def getarticles() = {

    val uri = MongoClientURI("mongodb://localhost:27017/")
    val mongoClient = MongoClient(uri)

    val db = mongoClient("sample")
    val collection = db("inventory")

    /*
    val o : DBObject = MongoDBObject("_id" -> o("55e0547c4a282955c0ebcd99"))
    val u = collection.findOneByID(o)
    println("hello" + u)
*/

    val objectId = "ObjectId(\"" + "55f7c763f7437172caa82378" + "\")"
    val query = MongoDBObject("_id" -> objectId)
    val obj = collection.findOne(query)
    //val stock = convertDbObjectToStock(obj.get) // convert it to a Stock
    //println(stock)
    println(obj)
  }

  def main(args: Array[String]) {
    getarticles()
  }
}
