package controllers

import com.sun.corba.se.spi.ior.ObjectId
import play.api._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import reactivemongo.api._
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.gridfs.Implicits._
import reactivemongo.bson._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.iteratee.Iteratee
import com.mongodb.casbah.Imports._

case class Person(id: BSONObjectID, title: String, url: String, source: String, extracted_text: String, location: String, date: String)
case class Per(id: BSONObjectID ,title:String)
class Application extends Controller {

    implicit val personHandler: BSONHandler[BSONDocument, Person] =
    Macros.handler[Person]

    implicit object PersonReader extends BSONDocumentReader[Person] {
      def read(doc: BSONDocument): Person = {
        val id = doc.getAs[BSONObjectID]("_id").get
        val title = doc.getAs[String]("title").get
        val url = doc.getAs[String]("url").get
        val source = doc.getAs[String]("source").get
        val extracted_text = doc.getAs[String]("extracted_text").get
        val location = doc.getAs[String]("location").get
        val date = doc.getAs[String]("date").get

        //Per(id,title)
        Person(id, title, url, source, extracted_text, location, date)
      }
    }

  implicit object PersonWriter extends BSONDocumentWriter[Person] {
    def write(person: Person): BSONDocument = {
      BSONDocument("title" -> person.title, "url" -> person.url, "source" -> person.source, "extracted_text" -> person.extracted_text, "location" -> person.location, "date" -> person.date)
    }
  }


  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }


/*
  def getarticles() = Action {

    val uri = MongoClientURI("mongodb://localhost:27017/")
    val mongoClient =  MongoClient(uri)

    val db = mongoClient("date_collections")
    val collection = db("date_19_08_2015")

    /*
    val o : DBObject = MongoDBObject("_id" -> o("55e0547c4a282955c0ebcd99"))
    val u = collection.findOneByID(o)
    println("hello" + u)
*/
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

    val query = MongoDBObject("_id" -> "55e0547c4a282955c0ebcd99")
    val obj = collection.findOne(query)
    val stock = convertDbObjectToStock(obj.get)  // convert it to a Stock
    println(stock)
    println(obj)

    /*val builder = collection.initializeOrderedBulkOperation
    builder.insert(MongoDBObject("_id" -> 1))
    builder.insert(MongoDBObject("_id" -> 2))
    builder.insert(MongoDBObject("_id" -> 3))

    builder.find(MongoDBObject("_id" -> 1)).updateOne($set("x" -> 2))
    builder.find(MongoDBObject("_id" -> 2)).removeOne()
    builder.find(MongoDBObject("_id" -> 3)).replaceOne(MongoDBObject("_id" -> 3, "x" -> 4))

    val result = builder.execute()*/

    var li:List[JsValue] = List()

    val json1: JsValue = Json.parse("""
    {
    "title" : "Maharashtra seeks speedy implementation of fee waiver scheme in region - The Times of India",
    "name" : "Watership Down",
    "category": "Lifestyle",
    "date" : "Jul 24, 2015, 06.35 AM IST"

  }
                                    """)

    val json2: JsValue = Json.parse("""
    {
    "title" : "HindustanTimes of India",
    "name" : "Watership Down",
    "category": "Economics",
    "date" : "Jul 24, 2015, 08.35 AM IST"

  }
                                    """)

    val json3: JsValue = Json.parse("""
    {
    "title" : "Navbharat times",
    "name" : "Watership Down",
    "category": "Crime",
    "date" : "Jul 25, 2015, 08.35 AM IST"

  }
                                    """)

    li = li:+ json1
    li = li:+ json2
    li = li:+ json3

    Ok(Json.toJson(li))

  }
*/


  def getarticles() = Action {

    val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))

    val db = connection("date_collections")

    //val collection = db("date_19_08_2015")

    //val query = Json.obj("_id" -> Json.obj("$oid" -> "55e0547c4a282955c0ebcd99"))
    val collection = db[BSONCollection]("date_19_08_2015")
    //val query = BSONDocument("_id" -> "55e0547c4a282955c0ebcd99")

    //val query = BSONDocument("title" -> "Bihar alliance")
    //var query = BSONDocument(Json.obj("_id" -> Json.obj("$oid" -> "55e0547c4a282955c0ebcd99")))
    val query = ("_id" -> Json.obj("$oid" -> "55e0547c4a282955c0ebcd99"))

    val cursor = collection.find(query).
      cursor[Person].collect[List]()

    println("*****************************************")
   // val cursor = collection.find(query).cursor[Person].collect[List]()
    // got the list of documents (in a fully non-blocking way)

    //val cursor = collection.find(query).cursor[BSONDocument]
    // Let's enumerate this cursor and print a readable representation of each document in the response


    //val cursor = collection.find(query).cursor[BSONDocument].collect[List]()
    /*val cursor = collection.find(
      BSONDocument("_id" -> "55e0547c4a282955c0ebcd99")).cursor[Person].collect[List]()*/


    cursor.map { people =>
    {
       // val firstName = person.title
      people.foreach(data=>{

        println("id " + data.id)
        println("title " + data.title)
        println("url " + data.url)
        println("source " + data.source)
        println("extracted_text " + data.extracted_text)
        println("location " + data.location)
        println("date " + data.date)

      })
       // println(s"found " + people)
      }
    }

    //val id = do.getAs[BSONObjectID]("_id").get
    val futureList = cursor.toString
    //println(futureList)


    var li:List[JsValue] = List()

    val json1: JsValue = Json.parse("""
    {
    "title" : "Maharashtra seeks speedy implementation of fee waiver scheme in region - The Times of India",
    "name" : "Watership Down",
    "category": "Lifestyle",
    "date" : "Jul 24, 2015, 06.35 AM IST"

  }
                                   """)

    val json2: JsValue = Json.parse("""
    {
    "title" : "HindustanTimes of India",
    "name" : "Watership Down",
    "category": "Economics",
    "date" : "Jul 24, 2015, 08.35 AM IST"

  }
                                   """)

    val json3: JsValue = Json.parse("""
    {
    "title" : "Navbharat times",
    "name" : "Watership Down",
    "category": "Crime",
    "date" : "Jul 25, 2015, 08.35 AM IST"

  }
                                    """)

    li = li:+ json1
    li = li:+ json2
    li = li:+ json3

    Ok(Json.toJson(li))
  }

/*  def listDocs(collection: BSONCollection) = {
    // Select only the documents which field 'firstName' equals 'Jack'
    val query = BSONDocument("firstName" -> "Jack")
    // select only the fields 'lastName' and '_id'
    val filter = BSONDocument(
      "lastName" -> 1,
      "_id" -> 1)

    /* Let's run this query then enumerate the response and print a readable
     * representation of each document in the response */
    collection.
      find(query, filter).
      cursor[BSONDocument].
      enumerate().apply(Iteratee.foreach { doc =>
      println(s"found document: ${BSONDocument pretty doc}")
    })

    // Or, the same with getting a list
    val futureList: Future[List[BSONDocument]] =
      collection.
        find(query, filter).
        cursor[BSONDocument].
        collect[List]()

    futureList.map { list =>
      list.foreach { doc =>
        println(s"found document: ${BSONDocument pretty doc}")
      }
    }
  }*/

}



 /* 55e0547b4a282955c0ebcd98

  55e0520c4a282955c0ebcbfa

  55e0520e4a282955c0ebcbfb

  55e052114a282955c0ebcbfc

  55e052db4a282955c0ebcc7c

  55e052dd4a282955c0ebcc7d

  55e052df4a282955c0ebcc7e

  55e052e14a282955c0ebcc7f

  55e052e44a282955c0ebcc80

  55e052e64a282955c0ebcc81

  55e0547c4a282955c0ebcd99
*/