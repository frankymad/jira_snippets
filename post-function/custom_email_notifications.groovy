import javax.mail.*
import javax.mail.internet.*
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import org.apache.commons.io.IOUtils;
import java.io.FileOutputStream;

import com.atlassian.jira.util.io.InputStreamConsumer
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.Issue

Issue issue = ComponentAccessor.getIssueManager().getIssueObject("IssueKey")
def attachmentManager = ComponentAccessor.getAttachmentManager()
def attachmentPathManager = ComponentAccessor.getAttachmentPathManager().attachmentPath.toString()
def filelink, filename, file
issue.getAttachments().collect { att ->
    Long id = att.id
    def attachmentFile = attachmentManager.getAttachment(id);
    file = attachmentFile
}

def out = new FileOutputStream('/tmp/'+file.id);
ComponentAccessor.getAttachmentManager().streamAttachmentContent(file, new InputStreamConsumer<Void>() {
    @Override
    public Void withInputStream(final InputStream is) throws IOException
    {
        try{IOUtils.copy(is, out);}
        finally{IOUtils.closeQuietly(out);}
        return null;
    }
});

Thread.currentThread().setContextClassLoader(javax.mail.Message.class.getClassLoader());

def props = new Properties();
props.put("mail.transport.protocol", "smtp");
props.put("mail.smtp.host", "smtp.host");
props.put("mail.smtp.port", "25");
props.put("mail.smtp.starttls.enable","false");
props.put("mail.smtp.ssl.trust", "*");
props.put("mail.smtp.debug", "true");
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.socketFactory.port", "25");
props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
props.put("mail.smtp.socketFactory.fallback", "true");

Authenticator auth = new SMTPAuthenticator();
def session = Session.getInstance(props, auth);
def msg = new MimeMessage(session);
msg.setSubject("Subject");
msg.setFrom(new InternetAddress("name@domain.com", "Instance Name"));
msg.addRecipient(Message.RecipientType.TO, new InternetAddress("Recipient Email"));

MimeBodyPart messagePart = new MimeBodyPart();
messagePart.setText("Text.");

MimeBodyPart attachmentPart = new MimeBodyPart();
FileDataSource fileDataSource = new FileDataSource('/tmp/'+file.id)
attachmentPart.setDataHandler(new DataHandler(fileDataSource));
attachmentPart.setFileName(file.filename);

Multipart multipart = new MimeMultipart();
multipart.addBodyPart(messagePart);
multipart.addBodyPart(attachmentPart);
msg.setContent(multipart);
msg.getFrom().toString()

Transport transport = session.getTransport();
transport.connect();
transport.sendMessage(msg, msg.getAllRecipients());
transport.close();

class SMTPAuthenticator extends javax.mail.Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
        String username = "login";
        String password = "password";
        return new PasswordAuthentication(username, password);
 }
}
def clean = [ 'bash', '-c', "rm -rf /tmp/" + file.id ].execute().text
