import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.CustomFieldManager

def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def dd = issue.getDueDate()
def repo = issue.getReporter()
def compon = issue.getComponents()
def summm = issue.getSummary()

def issueFactory = ComponentAccessor.getIssueFactory()
def subTaskManager = ComponentAccessor.getSubTaskManager()
def constantManager = ComponentAccessor.getConstantsManager()
def issueManager = ComponentAccessor.getIssueManager()

try
{
    MutableIssue newSubTask = issueFactory.getIssue()
    newSubTask.setSummary(summm)
    newSubTask.setParentObject(issue)
    newSubTask.setProjectObject(issue.getProjectObject())
    newSubTask.setIssueTypeId(constantManager.getAllIssueTypeObjects().find{ it.getName() == "SUB-TASK" }.id)
    newSubTask.setDescription(ComponentAccessor.getCommentManager().getComments(issue).body.last())
    newSubTask.setDueDate(dd)
    newSubTask.setReporter(repo)
    log.debug("New issue ${newSubTask}")
    Map<String,Object> newIssueParams = ["issue" : newSubTask] as Map<String,Object>
    issueManager.createIssueObject(user, newIssueParams)
    subTaskManager.createSubTaskIssueLink(issue, newSubTask, user)
    def issueService = ComponentAccessor.issueService
    def validateAssignResult = issueService.validateAssign(it,newSubTask.id, it.username)
        if(validateAssignResult.isValid()) {
            issueService.assign(user,validateAssignResult)
}
    log.info "Issue with summary ${newSubTask.summary} created"
}
catch(Exception ex) {println("Catching the exception");}
finally {println("The final block");}
