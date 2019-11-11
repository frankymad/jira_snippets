import com.atlassian.jira.component.ComponentAccessor
def users = issue.getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_17136"))
users.each(){
  def user = ComponentAccessor.getUserManager().getUserObject(it.name)
  ComponentAccessor.getWatcherManager().startWatching(user, issue)
}
