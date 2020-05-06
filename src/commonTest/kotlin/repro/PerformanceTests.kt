package repro

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlin.test.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class PerformanceTests {

    /*
JVM
parse as JsonArray 91.329014 millis
map JsonElement to List<String> 1.123484 millis
2nd run
parse as JsonArray 0.169734 millis
map JsonElement to List<String> 0.159045 millis

iOS x64
parse as JsonArray 0.397538 millis
map JsonElement to List<String> 0.164335 millis
2nd run
parse as JsonArray 0.119081 millis
map JsonElement to List<String> 0.153322 millis
*/
    @Test
    fun smallTest() {
        exec(SmallJson.content)
        println("2nd run")
        exec(SmallJson.content)
    }

    /*
JVM
parse as JsonArray 100.891387 millis
map JsonElement to List<String> 29.741769 millis
2nd run
parse as JsonArray 16.104879 millis
map JsonElement to List<String> 23.704069 millis

iOS x64
parse as JsonArray 133.770347 millis
map JsonElement to List<String> 168.310878 millis
2nd run
parse as JsonArray 160.062425 millis
map JsonElement to List<String> 170.953403 millis
*/
    @Test
    fun oneMBTest() {
        val json = toString(OneMBJson.content)
        exec(json)
        println("2nd run")
        exec(json)
    }

    /*
JVM
parse as JsonArray 141.153022 millis
map JsonElement to List<String> 210.782041 millis
2nd run
parse as JsonArray 79.185944 millis
map JsonElement to List<String> 182.276485 millis

iOS x64
parse as JsonArray 1586.538473 millis
map JsonElement to List<String> 1441.437562 millis
2nd run
parse as JsonArray 1835.876132 millis
map JsonElement to List<String> 1353.416678 millis
*/
    @Test
    fun fiveMBTest() {
        val json = toString(FiveMBJson.content)
        exec(json)
        println("2nd run")
        exec(json)
    }

    @OptIn(ExperimentalTime::class)
    fun exec(json: String) {
        var jsonArray: JsonArray
        val t = measureTime {
            jsonArray = Json.parseJson(json) as JsonArray
        }
        println("parse as JsonArray ${t.inMilliseconds} millis")

        var jsons: List<String>
        val t2 = measureTime {
            jsons = jsonArray.map { it.toString() }
        }
        println("map ${jsons.count()} JsonElement to List<String> ${t2.inMilliseconds} millis")
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun toString(content: List<String>): String {
        val sb = StringBuilder()
        content.forEach { sb.append(it) }
        return sb.substring(0)
    }
}