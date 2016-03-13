import org.apache.spark._
import org.apache.spark.streaming._

object StreamProcessingApp {
  def main(args: Array[String]): Unit = {
    val interval = args(0).toInt
    val conf = new SparkConf()
    val ssc = new StreamingContext(conf, Seconds(interval))

    // add your application specific data stream processing logic here


    ssc.start()
    ssc.awaitTermination()
  }
}
