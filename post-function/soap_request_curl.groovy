import com.atlassian.jira.component.ComponentAccessor
import com.opensymphony.workflow.InvalidInputException

def username = "login"
def password = "password"
def url = 'URL'
def command = """curl -X POST -H 'Content-type: text/xml' -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:sap-com:document:sap:rfc:functions"><soapenv:Header/>
  <soapenv:Body>
    <urn:Z030_CREATE_CONTRACT_HR>
      ---------
    </urn:Z030_CREATE_CONTRACT_HR>
  </soapenv:Body>
</soapenv:Envelope>' -H 'Connection: keep-alive' -H 'Authorization: Basic ${(username + ":" +  password).bytes.encodeBase64()}' '${url}' --insecure"""

def process = [ 'bash', '-c', command.replace("\n","")].execute()
process.waitFor()
def list = new XmlParser(false, false).parseText(process.text)
def s = list.'SOAP:Body'.-----.text()
def number = list.'SOAP:Body'.-----.text()
def message = list.'SOAP:Body'.-----.text()
if (number != "100") {
  if (number == "901") {
  	throw new InvalidInputException("Error - number already exists")
  }
  else {
    throw new InvalidInputException("Error - ${message} (${number})")
  }
}
else {
  return true
}
