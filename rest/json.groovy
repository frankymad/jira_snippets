import groovy.json.JsonSlurper
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

// def param = uriInfo.getQueryParameters()['param'].toString().replace('[', ' ').replace(']', ' ').trim()
def file = new File("json")
def jsonSlurper = new JsonSlurper()
def phones = jsonSlurper.parseText(file.text)
if (phones != null) {
    return Response.ok([phones: phones]).build()
}
else {
  return Response.noContent().build()
}
