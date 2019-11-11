def username = "login"
def password = "password"
def url = "/Orchestrator2012/Orchestrator.svc/Jobs/"
def data = """
'<?xml version="1.0" encoding="utf-8" standalone="yes"?>
<entry xmlns:d="http://schemas.microsoft.com/ado/2007/08/dataservices" xmlns:m="http://schemas.microsoft.com/ado/2007/08/dataservices/metadata" xmlns="http://www.w3.org/2005/Atom">
<content type="application/xml">
<m:properties>
<d:RunbookId type="Edm.Guid">{ba449f5e-2445-4706-8ec0-f0956f7a5b35}</d:RunbookId>
<d:Parameters>&lt;Data&gt;&lt;Parameter&gt;&lt;ID&gt;{a3506f18-65f4-4293-823e-397552280cd8}&lt;/ID&gt;&lt;Value&gt;ELDOC &lt;/Value&gt;&lt;/Parameter&gt;&lt;/Data&gt;</d:Parameters>
</m:properties>
</content>
</entry>'
"""
def string = "curl -XPOST -H 'Content-Type: application/atom+xml' -d ${data.replace('\n','')} -v -s --ntlm -u '${username}':'${password}' ${url}"
def process = [ 'bash', '-c', string ].execute().text
