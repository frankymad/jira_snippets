import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.user.ApplicationUser
def field = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectsByName("Line_Manager_tech")[0]
def value = issue.getCustomFieldValue(ComponentAccessor.getCustomFieldManager().getCustomFieldObjectsByName("Line_Manager_tech")[0])
ApplicationUser user = ComponentAccessor.getUserManager().getUserByName(value.toString())
