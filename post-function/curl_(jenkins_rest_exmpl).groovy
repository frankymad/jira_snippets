import com.atlassian.jira.issue.Issue
def key = issue.getKey()
def curl = "/usr/bin/curl -X POST http://login:password@jenkins/job/vbanking/job/JOB/buildWithParameters?key_j=" + key
def output = curl.execute().text
