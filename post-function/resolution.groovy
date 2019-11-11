import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.config.ResolutionManager
issue.setResolution(ComponentAccessor.getComponent(ResolutionManager).getResolutionByName("Rejected"))
