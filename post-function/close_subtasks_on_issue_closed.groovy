import com.atlassian.jira.bc.issue.IssueService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.IssueInputParametersImpl
import com.atlassian.jira.config.ResolutionManager
import com.atlassian.jira.issue.index.IssueIndexingService
import com.atlassian.jira.util.ImportUtils
import com.atlassian.jira.event.type.EventDispatchOption

def currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def issue = ComponentAccessor.getIssueManager().getIssueObject("ISSUE")
issue.getSubTaskObjects().each{
  IssueService issueService = ComponentAccessor.getIssueService()
  def customFieldManager = ComponentAccessor.getCustomFieldManager()
  def transitionValidationResult = issueService.validateTransition(currentUser, it.id, 111, new IssueInputParametersImpl())
  if (transitionValidationResult.isValid()) {
    it.setResolution(ComponentAccessor.getComponent(ResolutionManager).getResolutionByName("Won't Fix"))
    ComponentAccessor.getIssueManager().updateIssue(currentUser, it, EventDispatchOption.DO_NOT_DISPATCH, false)
    def issueManager = ComponentAccessor.getIssueManager()
    def issueIndexingService = ComponentAccessor.getComponent(IssueIndexingService)
    boolean wasIndexing = ImportUtils.isIndexIssues()
    ImportUtils.setIndexIssues(true)
    issueIndexingService.reIndex(ComponentAccessor.getIssueManager().getIssueObject(it.id))
    ImportUtils.setIndexIssues(wasIndexing)
    def transitionResult = issueService.transition(currentUser, transitionValidationResult)
 }
}
