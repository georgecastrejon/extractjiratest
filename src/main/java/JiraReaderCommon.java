import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class JiraReaderCommon {
    private static final String JIRA_URL = "http://localhost:8080";
    private static final String JIRA_USERNAME = "gcastrejon";
    private static final String JIRA_PASSWORD = "XXXXX";


    public static void main(String[] args) throws Exception {
        try {
            String projectKey = "LAP";
            String projectName = getProjectName(projectKey);

            System.out.println("Nombre del proyecto: " + projectName);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static String getProjectName(String projectKey) throws URISyntaxException, IOException {
        URI jiraServerUri = new URI(JIRA_URL);
        JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, JIRA_USERNAME, JIRA_PASSWORD);
        try {
            Project project = restClient.getProjectClient().getProject(projectKey).claim();
            return project.getName();
        } finally {
            restClient.close();
        }
    }

}
