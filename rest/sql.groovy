import groovy.sql.Sql
import java.sql.Driver
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

// def param = uriInfo.getQueryParameters()['param'].toString().replace('[', ' ').replace(']', ' ').trim()
def driver = Class.forName('oracle.jdbc.OracleDriver').newInstance() as Driver
def props = new Properties()
props.setProperty("user", "user")
props.setProperty("password", "password")
def items = []
def conn = driver.connect("jdbc", props)
def sql = new Sql(conn)
try {
    sql.eachRow("""SQL""") {
      def item = it
      items = items + item
    }
  	conn.close()
}
catch(Exception ex) {
  	conn.close()
}
finally {
    sql.close()
    conn.close()
}
conn.close()

return Response.ok(items).build()
