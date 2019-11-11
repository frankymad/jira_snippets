import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.IssueFieldConstants
import webwork.action.ActionContext
import com.opensymphony.workflow.InvalidInputException

if (!transientVars.get("comment")) {
 throw new InvalidInputException(IssueFieldConstants.COMMENT,
 "Введите комментарий - он будет использован в качестве описания новой задачи")
}
