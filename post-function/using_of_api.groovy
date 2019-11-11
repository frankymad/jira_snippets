import groovy.json.JsonSlurper

def authString = "login:password".getBytes().encodeBase64().toString();
connection = new URL("URL"+"${with_parameters}").openConnection() as HttpURLConnection
  connection.setRequestProperty("User-Agent", "groovy-2.4.4")
  connection.setRequestProperty("Accept", "application/json")
  connection.setRequestProperty("Authorization", "Basic ${authString}")
  if (connection.responseCode == 200) {
    result = connection.inputStream.text
    def jsonSlurper = new JsonSlurper()
    def json_object = jsonSlurper.parseText(result)
    return json_object
  }
  else
  {
    println connection.responseCode + ": " + connection.inputStream.text
  }
}
catch(Exception ex) {println("Catching the exception");}
