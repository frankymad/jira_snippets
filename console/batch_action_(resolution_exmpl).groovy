import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.config.ResolutionManager
import com.atlassian.jira.issue.index.IssueIndexingService
import com.atlassian.jira.util.ImportUtils
import com.atlassian.jira.event.type.EventDispatchOption

def jqlSearch = """JQL"""
def resolution = "Done"
SearchService searchService = ComponentAccessor.getComponent(SearchService.class)
def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def issues = []
SearchService.ParseResult parseResult =  searchService.parseQuery(user, jqlSearch)
if (parseResult.isValid()) {
  def searchResult = searchService.search(user, parseResult.getQuery(), PagerFilter.getUnlimitedFilter())
  issues = searchResult.issues.collect {ComponentAccessor.getIssueManager().getIssueObject(it.id)}
  if (issues == []){
    return("Empty issues list. Nothing to do.")
  }
  issues.each{
    def resolutionManager = ComponentAccessor.getComponent(ResolutionManager)
    def issueIndexingService = ComponentAccessor.getComponent(IssueIndexingService)
    it.setResolution(resolutionManager.getResolutionByName(resolution))
    ComponentAccessor.getIssueManager().updateIssue(user, it, EventDispatchOption.DO_NOT_DISPATCH, false)
    boolean wasIndexing = ImportUtils.isIndexIssues();
    ImportUtils.setIndexIssues(true);
    issueIndexingService.reIndex(ComponentAccessor.getIssueManager().getIssueObject(it.id));
    ImportUtils.setIndexIssues(wasIndexing);
  }
  return((resolution == "") ? "Cleared resolution in: " + issues.join(", ") : "Set resolution to " + resolution + ": " + issues.join(", "))
} else {
  return("Invalid JQL: " + jqlSearch);
}
