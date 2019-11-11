import groovy.sql.Sql
import java.sql.Driver
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import javax.ws.rs.core.Response

def user = uriInfo.getQueryParameters()['user'].toString().replace('[', ' ').replace(']', ' ').trim()
connection = new URL("URL"+user).openConnection() as HttpURLConnection
connection.setRequestProperty( 'User-Agent', 'groovy-2.4.4' )
connection.setRequestProperty( 'Accept', 'application/json' )
result = connection.inputStream.text
def jsonSlurper = new JsonSlurper()
def json_object = jsonSlurper.parseText(result)
