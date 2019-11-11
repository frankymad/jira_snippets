// REST endpoint with mygroovy for getting tasks and subtasks logged work.

import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.util.ImportUtils
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

def startDate = uriInfo.getQueryParameters()['start'].toString().replace('[', ' ').replace(']', ' ').trim()
def endDate = uriInfo.getQueryParameters()['end'].toString().replace('[', ' ').replace(']', ' ').trim()

try {
  if (Date.parse('yyyy-MM-dd HH:mm:ss', endDate + ' 23:59:59') - Date.parse('yyyy-MM-dd HH:mm:ss', startDate + ' 00:00:00') > 31){
    return Response.ok("Maximum 31 day please.").build()
  }
  else if (Date.parse('yyyy-MM-dd HH:mm:ss', endDate + ' 23:59:59') - Date.parse('yyyy-MM-dd HH:mm:ss', startDate + ' 00:00:00') < 0){
    return Response.ok("Start date can't be langer than end date.").build()
  }
}
catch(Exception ex) {
  if (ex.getClass().getCanonicalName() == "java.text.ParseException"){
    return Response.ok("Wrong date format. Please use YYYY-MM-DD date format. Ex: 2019-10-15").build()
  }
  else {
    return Response.ok("Something goes wrong. "+ ex.toString()).build()
  }
}
def jqlSearch = """project = PROJECT and worklogDate >= ${startDate} and worklogDate <= ${endDate}"""
SearchService searchService = ComponentAccessor.getComponent(SearchService.class)
def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def issues = []
def logIssues = []
SearchService.ParseResult parseResult = searchService.parseQuery(user, jqlSearch)
if (parseResult.isValid()) {
  def searchResult = searchService.search(user, parseResult.getQuery(), PagerFilter.getUnlimitedFilter())
  issues = searchResult.issues.collect {ComponentAccessor.getIssueManager().getIssueObject(it.id)}
}
if (issues == []) {
  return Response.ok("Empty issues list. Nothing to do.").build()
}
issues.sort()
issues.each { issue ->
  if (issue.issueType.name.toString() == "Sub-task" && !(issue.parentObject in issues) && !(issue.parentObject in logIssues)){logIssues.add(issue.parentObject)}
  else {logIssues.add(issue)}
}

def worklog = """<style type="text/css">.tg  {border-collapse:collapse;border-spacing:0;}.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}.tg .tg-0pky{border-color:inherit;text-align:left;vertical-align:top}.tg .tg-0lax{text-align:left;vertical-align:top}</style>"""
worklog = worklog + """<table class="tg"><tr><th>Период</th><th>${startDate}</th><th>${endDate}</th></tr><tr><th class="tg-0pky">Task</th><th class="tg-0lax">Sub-task</th><th class="tg-0lax">Component</th><th class="tg-0lax">Estimated</th><th class="tg-0lax">Time worked</th><th class="tg-0lax">Fix version</th></tr>"""

def total = 0
logIssues.each{ issue->
  def totalInTask = 0
  worklog = worklog + "<tr>"
  def log = ComponentAccessor.getWorklogManager().getByIssue(issue)
  def timeLog = 0
  log.each {
    if (it.startDate >= Date.parse('yyyy-MM-dd HH:mm:ss', startDate + ' 00:00:00') && it.startDate <= Date.parse('yyyy-MM-dd HH:mm:ss', endDate + ' 23:59:59')) {
      timeLog = timeLog + it.timeSpent
    }
  }
  if (timeLog != 0){
    worklog = worklog + """<td class="tg-0lax">${issue}</td><td class="tg-0lax"></td><td class="tg-0lax">${issue.components*.name.join(", ")}</td><td class="tg-0lax">${(issue.originalEstimate != null) ? String.format("%02d",(issue.originalEstimate/3600).toLong())+":"+String.format("%02d",(issue.originalEstimate%3600/60).toLong()) : "00:00"}</th><td class="tg-0lax">${String.format("%02d",(timeLog/3600).toLong())}:${String.format("%02d",(timeLog%3600/60).toLong())}</td><td class="tg-0lax">${issue.fixVersions*.name.join(", ")}</td>"""
    totalInTask += timeLog
  }
  else {
    worklog = worklog + """<td class="tg-0lax">${issue}</td><td class="tg-0lax"></td><td class="tg-0lax">${issue.components*.name.join(", ")}</td><td class="tg-0lax"></td><td class="tg-0lax"></td><td class="tg-0lax">${issue.fixVersions*.name.join(", ")}</td>"""
  }
  def subTasks = issue.getSubTaskObjects()
  worklog = worklog + "</tr>"
  subTasks.each { subTask ->
    def subTimeLog = 0
    ComponentAccessor.getWorklogManager().getByIssue(subTask).each {
      if (it.startDate >= Date.parse('yyyy-MM-dd HH:mm:ss', startDate + ' 00:00:00') && it.startDate <= Date.parse('yyyy-MM-dd HH:mm:ss', endDate + ' 23:59:59')) {
        subTimeLog += it.timeSpent
      }
    }
    if (subTimeLog != 0){
      worklog = worklog + """<tr><td class="tg-0lax"></td><td class="tg-0lax">${subTask}</td><td class="tg-0lax">${subTask.components*.name.join(", ")}</td><td class="tg-0lax">${(subTask.originalEstimate != null) ? String.format("%02d",(subTask.originalEstimate/3600).toLong())+":"+String.format("%02d",(subTask.originalEstimate%3600/60).toLong()) : "00:00"}</td><td class="tg-0lax">${String.format("%02d",(subTimeLog/3600).toLong())}:${String.format("%02d",(subTimeLog%3600/60).toLong())}</td><td class="tg-0lax">${subTask.fixVersions*.name.join(", ")}</td></tr>"""
      totalInTask += subTimeLog
    }
  }
  worklog = worklog + """<tr><td class="tg-0lax">Total</td><td class="tg-0lax"></td><td class="tg-0lax"></td><td class="tg-0lax"></td><td class="tg-0lax">${String.format("%02d",(totalInTask/3600).toLong())}:${String.format("%02d",(totalInTask%3600/60).toLong())}</td></tr>"""
  total += totalInTask
}
worklog = worklog + """</table>"""
return Response.ok(worklog).type(MediaType.TEXT_HTML).build()
