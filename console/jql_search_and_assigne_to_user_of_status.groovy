import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.config.ResolutionManager
import com.atlassian.jira.issue.index.IssueIndexingService
import com.atlassian.jira.issue.history.ChangeItemBean
import com.atlassian.jira.util.ImportUtils
import com.atlassian.jira.event.type.EventDispatchOption

def jqlSearch = """JQL"""
def string = []
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
    def changes = ComponentAccessor.getChangeHistoryManager().getAllChangeItems(it)
    changes.each{ change ->
      if(change.getTos().toString() == "{6=Closed}"){
        string.add(ComponentAccessor.getUserManager().getUserObject(change.getUserKey()))
        it.setAssignee(ComponentAccessor.getUserManager().getUserObject(change.getUserKey()))
        ComponentAccessor.getIssueManager().updateIssue(user, it, EventDispatchOption.DO_NOT_DISPATCH, false)
        boolean wasIndexing = ImportUtils.isIndexIssues();
        ImportUtils.setIndexIssues(true);
        ComponentAccessor.getComponent(IssueIndexingService).reIndex(ComponentAccessor.getIssueManager().getIssueObject(it.id));
        ImportUtils.setIndexIssues(wasIndexing);
      }
    }
  }
  return("Done for this tickets: " + issues.join(", "))
} else {
  return("Invalid JQL: " + jqlSearch);
}
