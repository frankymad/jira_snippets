
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.attachment.Attachment
import com.atlassian.jira.util.AttachmentUtils
import com.atlassian.mail.Email
import com.atlassian.mail.queue.SingleMailQueueItem
import webwork.action.ActionContext
import com.atlassian.jira.issue.attachment.TemporaryWebAttachment
import com.atlassian.jira.issue.attachment.TemporaryWebAttachmentManager
import com.atlassian.jira.issue.IssueFieldConstants
import com.atlassian.jira.issue.attachment.FileSystemAttachmentDirectoryAccessor
import com.atlassian.jira.event.type.EventDispatchOption
import javax.mail.*
import javax.mail.internet.*
import java.sql.Timestamp
import java.util.*
import java.time.*


def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def emailAddr = issue.getReporter().getEmailAddress()
def copy = ''
  if (issue.getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_10000")).isEmpty() != true)
        {issue.getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_10000")).each{
            copy += it.getEmailAddress() + ','}
        }
if (copy.isEmpty() != true) {
    copy = copy.substring(0,copy.length() - 1);}
def subject = '(' + issue.getKey() + ') ' + issue.getSummary()
def body = transientVars.get("comment")
def from = issue.getProjectObject().getEmail() //адрес, который будет указан как отправитель этого сообщения. По умолчанию адрес JIRA.
def replyTo = issue.getProjectObject().getEmail()
def emailFormat = "text/plain"
def attachmenttoadd = ComponentAccessor.getAttachmentManager().getAttachments(issue)
def date = new Date()
def timestamp2 = date.toTimestamp().format("yyyy-MM-dd HH:mm", TimeZone.getTimeZone('UTC'))
def attachments = []
def string = ""
attachmenttoadd.each{
    if (timestamp2 == it.created.format("yyyy-MM-dd HH:mm", TimeZone.getTimeZone('UTC')))
        {attachments.add(it)}
}

void sendEmail(String emailAddr, String copy, String subject, String body, String from, String replyTo, String emailFormat, List<Attachment> attachments) {
    Email email = new Email(emailAddr, copy, '')
    email.setSubject(subject)
    Multipart multipart = new MimeMultipart("mixed")

    if (from) {
        email.setFrom(from)
    }
    if (replyTo) {
        email.setReplyTo(replyTo)
    }

    MimeBodyPart bodyPart = new MimeBodyPart()
    bodyPart.setContent(body, "${emailFormat ?: 'text/html'}; charset=utf-8")
    multipart.addBodyPart(bodyPart)

    attachments?.each {
        File attachment = AttachmentUtils.getAttachmentFile(it)

        MimeBodyPart attachmentPart = new MimeBodyPart()
        attachmentPart.attachFile(attachment, it.getMimetype(), null)
        attachmentPart.setFileName(it.getFilename())
        multipart.addBodyPart(attachmentPart)
    }

    email.setMultipart(multipart)
    email.setMimeType("multipart/mixed")
    SingleMailQueueItem smqi = new SingleMailQueueItem(email)
    results = smqi
    ComponentAccessor.getMailQueue().addItem(smqi)
}

sendEmail(emailAddr, copy, subject, body, from, replyTo, emailFormat, attachments)
