import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.component.ComponentAccessor

def issue = event.issue as Issue
def curas = issue.getAssignee()
def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def tgtField = customFieldManager.getCustomFieldObjects(event.issue).find {it.name == "Issue Updated"}
def optionsManager = ComponentAccessor.getOptionsManager()
def changeHolder = new DefaultIssueChangeHolder()
if (user == curas && issue.getCustomFieldValue(tgtField) != null) {
  tgtField.updateValue(null, issue, new ModifiedValue(issue.getCustomFieldValue(tgtField), []),changeHolder)}
else return
