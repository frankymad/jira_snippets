import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.opensymphony.workflow.InvalidInputException

def field = ComponentAccessor.getCustomFieldManager().getCustomFieldObject("FIELD")
def value = issue.getCustomFieldValue(field)

if (unpVal == "" || unpVal == null)
  return

else if (!(unpVal ==~ /[0-9]{9}/)) {
  throw new InvalidInputException("field", "Error")}
