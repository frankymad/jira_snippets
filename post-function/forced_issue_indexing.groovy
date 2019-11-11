import com.atlassian.jira.issue.index.IssueIndexingService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.util.ImportUtils

def issue = ComponentAccessor.getIssueManager().getIssueObject("ISSUE")
def issueManager = ComponentAccessor.getIssueManager()
def issueIndexingService = ComponentAccessor.getComponent(IssueIndexingService)

boolean wasIndexing = ImportUtils.isIndexIssues();
ImportUtils.setIndexIssues(true);
issueIndexingService.reIndex(issueManager.getIssueObject(issue.id));
ImportUtils.setIndexIssues(wasIndexing);
