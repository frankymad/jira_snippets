import groovy.sql.Sql
import java.sql.Driver
import groovy.json.JsonSlurper
import javax.ws.rs.core.Response
import javax.servlet.http.HttpServletRequest

def driver = Class.forName('oracle.jdbc.OracleDriver').newInstance() as Driver
def props = new Properties()
props.setProperty("user", user
props.setProperty("password", password
def items = []
def conn = driver.connect("jdbc", props)
def sql = new Sql(conn)
try {
    sql.eachRow("""SQL""") {
      def item = it
      items = items + item
    }
}
catch(Exception ex) {
    return Response.ok([]).build()
}
finally {
    return Response.ok([phones: items]).build()
    sql.close()
    conn.close()
}

// HTTP Feed Custom Fields Plugin
