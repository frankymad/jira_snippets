import com.opensymphony.workflow.InvalidInputException

if(issue.getComponents().size() >= 2) {
 throw new InvalidInputException("components", "You have to select more than one component.")
}
