import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.component.ComponentAccessor
import groovy.util.XmlSlurper

try
{
	conn = new URL("SOAP-URL").openConnection() as HttpURLConnection
	conn.setDoOutput(true);
	def username = "login"
	def password = "password"
	conn.setRequestProperty( "SOAPAction", "")
	conn.setRequestProperty( "Content-Type", "text/xml" )
	String authorizationString = "Basic " + (username + ":" +  password).bytes.encodeBase64()
	conn.setRequestProperty ("Authorization", authorizationString)
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())
	def xml = """
	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://soap.integration.fsm.comarch.com/">
		<soapenv:Header/>
		<soapenv:Body>
			<soap:action>
				<query>
					--- SAOP REQUEST
				</query>
			</soap:action>
		</soapenv:Body>
	</soapenv:Envelope>
	"""

	wr.write(xml)
	wr.close()
	responseStream = conn.getInputStream().getText("utf-8")
	def responseSuccess = new XmlParser(false,false).parseText(responseStream)
	return responseSuccess
}
catch(Exception ex) {
	responseStream = conn.getErrorStream().getText("utf-8")
  def responseError = new XmlParser(false,false).parseText(responseStream)
	return responseError
}
