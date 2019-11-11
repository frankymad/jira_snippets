import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.bc.project.component.ProjectComponent
import com.atlassian.jira.security.roles.ProjectRoleManager

// group - role from Users & Roles
def project = issue.getProjectObject()
def role = ComponentAccessor.getComponent(ProjectRoleManager).getProjectRole(group.toString())
def actors = ComponentAccessor.getComponent(ProjectRoleManager).getProjectRoleActors(role, project)
def alternate = actors['roleActors'][0]['parameter']
def user = ComponentAccessor.getUserManager().getUser(alternate.toString()).name

// version 2
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.security.roles.ProjectRoleManager
def project = issue.getProjectObject()
def role = ComponentAccessor.getComponent(ProjectRoleManager).getProjectRole("Alternate")
def actors = ComponentAccessor.getComponent(ProjectRoleManager).getProjectRoleActors(role, project)
def alternate = actors['roleActors'][0]['parameter']
def user = ComponentAccessor.getUserManager().getUser(alternate)
issue.setAssignee(user)
