import com.atlassian.jira.component.ComponentAccessor;
def field = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectsByName("Approval Array")[0]
def approvalArray = issue.getCustomFieldValue(field)
def approvalList = approvalArray.toString().tokenize(',[]')
def user = ComponentAccessor.getUserManager().getUser(approvalList[0])
issue.setAssignee(user)
approvalList.remove(0)
issue.setCustomFieldValue(field, approvalList.toString().replaceAll(' ',''))
