import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.issue.ModifiedValue

def setField(field, text){
    def fieldCF = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectsByName(field.toString())[0]
    def changeHolder = new DefaultIssueChangeHolder()
    fieldCF.updateValue(null, issue, new ModifiedValue(issue.getCustomFieldValue(fieldCF), text.toString()),changeHolder)
}
