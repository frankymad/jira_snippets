import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.user.ApplicationUser

def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def issueFactory = ComponentAccessor.getIssueFactory()
def subTaskManager = ComponentAccessor.getSubTaskManager()
def constantManager = ComponentAccessor.getConstantsManager()
def issueManager = ComponentAccessor.getIssueManager()
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def parentIssue = ComponentAccessor.getIssueManager().getIssueObject("ISSUE")
def newSummary = customFieldManager.getCustomFieldObject("customfield_16910")

//def lc = ComponentAccessor.getCommentManager().getComments(parentIssue).body.last() -- latest comment, if we need to set summary of new issue.
MutableIssue newSubTask = issueFactory.getIssue()
newSubTask.setSummary("Summary"/* or lc, or parentIssue.summary */)
newSubTask.setDescription(ComponentAccessor.getCommentManager().getComments(parentIssue).body.last())
/* Comment Delete
ComponentAccessor.commentManager.delete(ComponentAccessor.getCommentManager().getComments(issue).last())
*/
newSubTask.setParentObject(parentIssue)
newSubTask.setProjectObject(parentIssue.getProjectObject())
newSubTask.setIssueTypeId(constantManager.getAllIssueTypeObjects().find{
it.getName() == "SUB_TASK_TYPE_NAME"}.id)

def fields = ["FIELDS",
              "ARRAY",
              "LIST"]

fields.each{
  def field = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName(it)
  newSubTask.setCustomFieldValue(field, parentIssue.getCustomFieldValue(field))
}

newSubTask.setComponent(parentIssue.getComponents())
newSubTask.reporter = user

Map<String,Object> newIssueParams = ["issue" : newSubTask] as Map<String,Object>
issueManager.createIssueObject(user, newIssueParams)
subTaskManager.createSubTaskIssueLink(parentIssue, newSubTask, user)
