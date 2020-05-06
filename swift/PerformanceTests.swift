import XCTest
import Foundation

class PerformanceTests: XCTestCase {

    /*
    parse to list 25.418108 millis
    to String array 28.574864 millis
    size 909
    2nd run
    parse to list 24.019624 millis
    to String array 27.368558 millis
    */
    func test1MB(){
        let tl = readData(fileName: "json_1Mb")
        let sl  = asStringList(json: tl!)
        print("size \(sl.count)")
        print("2nd run")
        let sl2  = asStringList(json: tl!)
    }

    /*
     parse to list 149.930847 millis
     to String array 160.764755 millis
     size 5171
     2nd run
     parse to list 140.198165 millis
     to String array 160.042163 millis
     */
    func test5MB(){
        let tl = readData(fileName: "json_5Mb")
        let sl  = asStringList(json: tl!)
        print("size \(sl.count)")
        print("2nd run")
        let sl2  = asStringList(json: tl!)
    }

    func asStringList(json: String) -> [String] {
        let data = json.data(using: .utf8)
        let jsonList = measure(st: "parse to list", exe: {
            try! JSONSerialization.jsonObject(with: data!, options: []) as? [[String:Any?]]
        })

        return measure(st: "to String array", exe: {
            var array = [String]()
            for json in jsonList! {
                if let jsonData = try? JSONSerialization.data(withJSONObject: json, options: []) {
                    if let jsonString = String(data: jsonData, encoding: String.Encoding.utf8) {
                        array.append(jsonString)
                    }
                }
            }
            return array
        })
    }

    func readData(fileName: String, type: String = "json") -> String! {
        guard let filepath = Bundle.main.path(forResource: fileName, ofType: type) else { return nil }
        return try? String(contentsOfFile: filepath, encoding: .utf8)
    }

    func measure<T>(st: String, exe: () -> T) -> T {
        let start = DispatchTime.now()
        let res = exe()
        let end = DispatchTime.now()

        let nanoTime = end.uptimeNanoseconds - start.uptimeNanoseconds
        let timeInterval = Double(nanoTime) / 1_000_000

        print("\(st) \(timeInterval) millis")
        return res
    }
}
