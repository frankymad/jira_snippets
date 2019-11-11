import com.atlassian.jira.ComponentManager
import com.atlassian.mail.Email
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.util.BuildUtilsInfo
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMultipart
import javax.mail.util.ByteArrayDataSource
import javax.activation.DataHandler
import org.apache.commons.lang.RandomStringUtils

def bc = "BC"
Email mail = new Email(bc)
mail.setSubject("Jira calendar event Test")
mail.addHeader("method", "REQUEST");
mail.addHeader("charset", "UTF-8");
mail.addHeader("component", "VEVENT");
def mp = new MimeMultipart("alternative")
def calendarPart = new MimeBodyPart()
calendarPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
calendarPart.setHeader("Content-ID", "calendar_message");
calendarPart.setDataHandler(new DataHandler(new ByteArrayDataSource("""BEGIN:VCALENDAR
PRODID:-//Microsoft Corporation//Outlook 16.0 MIMEDIR//EN
VERSION:2.0
METHOD:REQUEST
X-MS-OLK-FORCEINSPECTOROPEN:TRUE
BEGIN:VTIMEZONE
TZID:Belarus Standard Time
BEGIN:STANDARD
DTSTART:16010101T000000
TZOFFSETFROM:+0300
TZOFFSETTO:+0300
END:STANDARD
END:VTIMEZONE
BEGIN:VEVENT
ATTENDEE;CN="FirstName LastName";RSVP=TRUE:mailto:MAIL -- attendee
CLASS:PUBLIC
CREATED:20191031T112700Z
DESCRIPTION: \n
DTEND;TZID="Belarus Standard Time":20191101T150000  -- event end time
DTSTAMP:20191101T150000Z
DTSTART;TZID="Belarus Standard Time":20191101T140000  -- event start time
LAST-MODIFIED:20191101T150000Z
ORGANIZER;CN="Organizer Name":mailto:MAIL -- organazer
PRIORITY:5
SEQUENCE:0
SUMMARY;LANGUAGE=ru:summary -- summary
TRANSP:OPAQUE
UID:"""+RandomStringUtils.randomNumeric(112)+"""
X-MICROSOFT-CDO-BUSYSTATUS:BUSY
X-MICROSOFT-CDO-IMPORTANCE:1
X-MICROSOFT-CDO-INTENDEDSTATUS:BUSY
X-MICROSOFT-DISALLOW-COUNTER:FALSE
END:VEVENT
END:VCALENDAR
""", "text/calendar")));
mp.addBodyPart(calendarPart)
mail.setMultipart(mp)
ComponentAccessor.getMailServerManager().getDefaultSMTPMailServer().send(mail)
