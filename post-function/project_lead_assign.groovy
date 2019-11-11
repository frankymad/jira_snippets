import com.atlassian.jira.component.ComponentAccessor
def user = ComponentAccessor.getUserManager().getUser(issue.getProjectObject().getLead().getName())
issue.setAssignee(user)
