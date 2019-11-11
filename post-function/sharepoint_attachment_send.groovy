import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.Issue
import java.net.URLEncoder
import com.atlassian.jira.util.io.InputStreamConsumer
import org.apache.commons.io.IOUtils

link = 'SHAREPOINT URL'
def attachmentManager = ComponentAccessor.getAttachmentManager()
def attachmentPathManager = ComponentAccessor.getAttachmentPathManager().attachmentPath.toString()
def attachments = issue.getAttachments()

attachments.eachWithIndex{ item, index ->
  def filename
    def filelink = attachmentPathManager + '/' + issue.projectObject.key + '/' + issue.key + '/' + item.id
    if (attachments.size() == 1) {
        filename =  issue.key + "." + item.filename.split("\\.")[-1]
    }
    else {
        filename =  issue.key + "-" + (index + 1) + "." + item.filename.split("\\.")[-1]
    }
  File file = new File("/tmp/", filename)
  OutputStream out = new FileOutputStream(file)
  ComponentAccessor.getAttachmentManager().streamAttachmentContent(item, new InputStreamConsumer<Void>() {
    @Override
    public Void withInputStream(final InputStream is) throws IOException
    {
        try{
        IOUtils.copy(is, out);
        }
        finally{
        IOUtils.closeQuietly(out);
        }
        return null;
    }
  });
    upload_to_sp(file)
}

def upload_to_sp(file){
    def link = URLEncoder.encode(link, "UTF-8").replace('%3A',':').replace('%2F','/').replace('+','%20')
    def process = [ 'bash', '-c', 'curl -X PUT -v -s --ntlm -u "LOGIN":"PASSWORD" -T '+ file + " " + link ].execute().text
    def clean = [ 'bash', '-c', "rm -rf " + file ].execute().text
}
