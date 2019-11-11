import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.util.PathUtils;
import com.atlassian.jira.util.AttachmentUtils;

def date = issue.attachments[0].created
def i = 0;
issue.attachments.each {
  if (it.created > date){
    date = it.created;
    i = issue.attachments.indexOf(it);
  }
}
def subTasksList = issue.getSubTaskObjects();
def subtask = subTasksList[0];
def attachment = issue.attachments[i];
def author = ComponentAccessor.getUserManager().getUser(attachment.author)
ComponentAccessor.getAttachmentManager().copyAttachment(attachment, author, subtask.getKey())
ComponentAccessor.attachmentManager.deleteAttachment(issue.attachments[i])
