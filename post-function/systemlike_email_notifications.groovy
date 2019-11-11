import com.atlassian.jira.ComponentManager
import com.atlassian.mail.Email
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.util.BuildUtilsInfo

def issue = ComponentAccessor.getIssueManager().getIssueObject("ISSUE")
def description = issue.description
users = description.findAll(/[~]\w+/)
users.each{
  String string = """<a class="user-hover" rel="${it.replace("~","")}" id="email_${it.replace("~","")}" href=http://jira.main.velcom.by/secure/ViewProfile.jspa?name=${it.replace("~","")} style="color:#3f919e;; color: #3b73af; text-decoration: none">${ComponentAccessor.getUserManager().getUser(it.replace("~",""))["displayName"]}</a>"""
 description = description.replace("["+it+"]",string)
}

description = description.replaceAll("\n","""</p> <p style="margin-top:0;margin-bottom:10px;; margin: 10px 0 0 0">""")
def text = """<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"><html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <base href="http://jira.main.velcom.by">
        <title>Message Title</title>
    </head>
    <body class="jira" style="color: #333333; font-family: Arial, sans-serif; font-size: 14px; line-height: 1.429">
        <table id="background-table" cellpadding="0" cellspacing="0" width="100%" style="border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #f5f5f5; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt" bgcolor="#f5f5f5">
            <!-- header here -->
            <tbody>
                <tr>
                    <td id="header-pattern-container" style="padding: 0px; border-collapse: collapse; padding: 10px 20px">
                        <table id="header-pattern" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt">
                            <tbody>
                                <tr>
                                    <td id="header-text-container" valign="middle" style="padding: 0px; border-collapse: collapse; vertical-align: middle; font-family: Arial, sans-serif; font-size: 14px; line-height: 20px; mso-line-height-rule: exactly; mso-text-raise: 1px"> <a class="user-hover" rel="Anna_Do" id="email_Anna_Do" href="http://jira.main.velcom.by/secure/ViewProfile.jspa?name=${issue.reporter['name']}" style="color:#3f919e;; color: #3b73af; text-decoration: none">${issue.reporter['displayName']}</a> <strong>created</strong> an issue </td>
                                </tr>
                            </tbody>
                        </table> </td>
                </tr>
                <tr>
                    <td id="email-content-container" style="padding: 0px; border-collapse: collapse; padding: 0 20px"> <span style="display:none">---- Original Message ----</span>
                        <table id="email-content-table" cellspacing="0" cellpadding="0" border="0" width="100%" style="border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-spacing: 0; border-collapse: separate">
                            <tbody>
                                <tr>
                                    <!-- there needs to be content in the cell for it to render in some clients -->
                                    <td class="email-content-rounded-top mobile-expand" style="padding: 0px; border-collapse: collapse; color: #ffffff; padding: 0 15px 0 16px; height: 15px; background-color: #ffffff; border-left: 1px solid #cccccc; border-top: 1px solid #cccccc; border-right: 1px solid #cccccc; border-bottom: 0; border-top-right-radius: 5px; border-top-left-radius: 5px; height: 10px; line-height: 10px; padding: 0 15px 0 16px; mso-line-height-rule: exactly" height="10" bgcolor="#ffffff">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td class="email-content-main mobile-expand " style="padding: 0px; border-collapse: collapse; border-left: 1px solid #cccccc; border-right: 1px solid #cccccc; border-top: 0; border-bottom: 0; padding: 0 15px 0 16px; background-color: #ffffff" bgcolor="#ffffff">
                                        <table class="page-title-pattern" cellspacing="0" cellpadding="0" border="0" width="100%" style="border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt">
                                            <tbody>
                                                <tr>
                                                    <td class="page-title-pattern-first-line " style="padding: 0px; border-collapse: collapse; font-family: Arial, sans-serif; font-size: 14px; padding-top: 10px"> <a href="http://jira.main.velcom.by/browse/${issue.project['key']}" style="color: #3b73af; text-decoration: none">"""+issue.project['name']+"""</a> / <a href="http://jira.main.velcom.by/browse/${issue}" style="color: #3b73af; text-decoration: none"></a> <a href="http://jira.main.velcom.by/browse/${issue}" style="color: #3b73af; text-decoration: none">${issue}</a> </td>
                                                </tr>
                                                <tr>
                                                    <td style="vertical-align: top;; padding: 0px; border-collapse: collapse; padding-right: 5px; font-size: 20px; line-height: 30px; mso-line-height-rule: exactly" class="page-title-pattern-header-container"> <span class="page-title-pattern-header" style="font-family: Arial, sans-serif; padding: 0; font-size: 20px; line-height: 30px; mso-text-raise: 2px; mso-line-height-rule: exactly; vertical-align: middle"> <a href="http://jira.main.velcom.by/browse/${issue}" style="color: #3b73af; text-decoration: none">${issue.summary}</a> </span> </td>
                                                </tr>
                                            </tbody>
                                        </table> </td>
                                </tr>
                                <tr>
                                    <td class="email-content-main mobile-expand  wrapper-special-margin" style="padding: 0px; border-collapse: collapse; border-left: 1px solid #cccccc; border-right: 1px solid #cccccc; border-top: 0; border-bottom: 0; padding: 0 15px 0 16px; background-color: #ffffff; padding-top: 10px; padding-bottom: 5px" bgcolor="#ffffff">
                                        <table class="keyvalue-table" style="border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt">
                                            <tbody>
                                                <tr>
                                                    <th style="color: #707070; font: normal 14px/20px Arial, sans-serif; text-align: left; vertical-align: top; padding: 2px 0">Issue Type:</th>
                                                    <td style="padding: 0px; border-collapse: collapse; font: normal 14px/20px Arial, sans-serif; padding: 2px 0 2px 5px; vertical-align: top">${issue.issueType['name']}</td>
                                                </tr>
                                                <tr>
                                                    <th style="color: #707070; font: normal 14px/20px Arial, sans-serif; text-align: left; vertical-align: top; padding: 2px 0">Assignee:</th>
                                                    <td style="padding: 0px; border-collapse: collapse; font: normal 14px/20px Arial, sans-serif; padding: 2px 0 2px 5px; vertical-align: top">${issue.assignee['displayName']}</td>
                                                </tr>
                                                <tr>
                                                    <th style="color: #707070; font: normal 14px/20px Arial, sans-serif; text-align: left; vertical-align: top; padding: 2px 0">Created:</th>
                                                    <td style="padding: 0px; border-collapse: collapse; font: normal 14px/20px Arial, sans-serif; padding: 2px 0 2px 5px; vertical-align: top">${issue.created.format("dd/MM/YYYY HH:mm")}</td>
                                                </tr>
                                                ${date = (issue.dueDate == null) ? "" : """<tr><th style="color: #707070; font: normal 14px/20px Arial, sans-serif; text-align: left; vertical-align: top; padding: 2px 0">Due Date:</th><td style="padding: 0px; border-collapse: collapse; font: normal 14px/20px Arial, sans-serif; padding: 2px 0 2px 5px; vertical-align: top">${issue.dueDate.format("dd/MM/YYYY HH:mm")}</td></tr>"""}
                                                <tr>
                                                    <th style="color: #707070; font: normal 14px/20px Arial, sans-serif; text-align: left; vertical-align: top; padding: 2px 0">Priority:</th>
                                                    <td style="padding: 0px; border-collapse: collapse; font: normal 14px/20px Arial, sans-serif; padding: 2px 0 2px 5px; vertical-align: top">${issue.priority['name']}</td>
                                                </tr>
                                                <tr>
                                                    <th style="color: #707070; font: normal 14px/20px Arial, sans-serif; text-align: left; vertical-align: top; padding: 2px 0">Reporter:</th>
                                                    <td style="padding: 0px; border-collapse: collapse; font: normal 14px/20px Arial, sans-serif; padding: 2px 0 2px 5px; vertical-align: top"> <a class="user-hover" rel="Anna_Do" id="email_Anna_Do" href="http://jira.main.velcom.by/secure/ViewProfile.jspa?name=${issue.reporter['name']}" style="color:#3f919e;; color: #3b73af; text-decoration: none">${issue.reporter['displayName']}</a> </td>
                                                </tr>
                                            </tbody>
                                        </table> </td>
                                </tr>
                                <tr>
                                    <td class="email-content-main mobile-expand  issue-description-container" style="padding: 0px; border-collapse: collapse; border-left: 1px solid #cccccc; border-right: 1px solid #cccccc; border-top: 0; border-bottom: 0; padding: 0 15px 0 16px; background-color: #ffffff; padding-top: 5px; padding-bottom: 10px" bgcolor="#ffffff">
                                        <table class="text-paragraph-pattern" cellspacing="0" cellpadding="0" border="0" width="100%" style="border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-family: Arial, sans-serif; font-size: 14px; line-height: 20px; mso-line-height-rule: exactly; mso-text-raise: 2px">
                                            <tbody>
                                                <tr>
                                                    <td class="text-paragraph-pattern-container mobile-resize-text " style="padding: 0px; border-collapse: collapse; padding: 0 0 10px 0"> <p style="margin-top:0;margin-bottom:10px;; margin: 10px 0 0 0; margin-top: 0">${description}</p> </td>
                                                </tr>
                                            </tbody>
                                        </table> </td>
                                </tr>
                                <tr>
                                    <td class="email-content-main mobile-expand " style="padding: 0px; border-collapse: collapse; border-left: 1px solid #cccccc; border-right: 1px solid #cccccc; border-top: 0; border-bottom: 0; padding: 0 15px 0 16px; background-color: #ffffff" bgcolor="#ffffff">
                                        <table id="actions-pattern" cellspacing="0" cellpadding="0" border="0" width="100%" style="border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-family: Arial, sans-serif; font-size: 14px; line-height: 20px; mso-line-height-rule: exactly; mso-text-raise: 1px">
                                            <tbody>
                                                <tr>
                                                    <td id="actions-pattern-container" valign="middle" style="padding: 0px; border-collapse: collapse; padding: 10px 0 10px 24px; vertical-align: middle; padding-left: 0">
                                                        <table align="left" style="border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt">
                                                            <tbody>
                                                                <tr>
                                                                    <td class="actions-pattern-action-text-container" style="padding: 0px; border-collapse: collapse; font-family: Arial, sans-serif; font-size: 14px; line-height: 20px; mso-line-height-rule: exactly; mso-text-raise: 4px; padding-left: 5px"> <a href="http://jira.main.velcom.by/browse/"""+issue+"""#add-comment" target="_blank" title="Add Comment" style="color: #3b73af; text-decoration: none">Add Comment</a> </td>
                                                                </tr>
                                                            </tbody>
                                                        </table> </td>
                                                </tr>
                                            </tbody>
                                        </table> </td>
                                </tr>
                                <!-- there needs to be content in the cell for it to render in some clients -->
                                <tr>
                                    <td class="email-content-rounded-bottom mobile-expand" style="padding: 0px; border-collapse: collapse; color: #ffffff; padding: 0 15px 0 16px; height: 5px; line-height: 5px; background-color: #ffffff; border-top: 0; border-left: 1px solid #cccccc; border-bottom: 1px solid #cccccc; border-right: 1px solid #cccccc; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; mso-line-height-rule: exactly" height="5" bgcolor="#ffffff">&nbsp;</td>
                                </tr>
                            </tbody>
                        </table> </td>
                </tr>
                <tr>
                    <td id="footer-pattern" style="padding: 0px; border-collapse: collapse; padding: 12px 20px">
                        <table id="footer-pattern-container" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt">
                            <tbody>
                                <tr>
                                    <td id="footer-pattern-text" class="mobile-resize-text" width="100%" style="padding: 0px; border-collapse: collapse; color: #999999; font-size: 12px; line-height: 18px; font-family: Arial, sans-serif; mso-line-height-rule: exactly; mso-text-raise: 2px"> This message was sent by Atlassian JIRA <span id="footer-build-information">(${ComponentAccessor.getComponent(BuildUtilsInfo.class)})</span> </td>
                                </tr>
                            </tbody>
                        </table> </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
"""

def bc = "BC"
Email email = new Email(bc)
email.setMimeType("text/html")
email.setBcc(bc)
email.setSubject("""("""+issue+""") """+issue.summary)
email.setBody(text)
ComponentAccessor.getMailServerManager().getDefaultSMTPMailServer().send(email)
