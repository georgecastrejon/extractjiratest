package helpers.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import enviroment.Enviroment;
import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;


public class JiraReaderServer {
    private static final Logger logger = Logger.getLogger(JiraReaderServer.class);
    private static Enviroment env = ConfigFactory.create(Enviroment.class);

    private JiraReaderServer() {
    }

    public static String getProjectName(String projectKey) throws URISyntaxException, IOException {
        URI jiraServerUri = new URI(env.jiraUrlServer());
        JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, env.jiraUserNameServer(), env.jiraPwdServer());
        try {
            Project project = restClient.getProjectClient().getProject(projectKey).claim();
            return project.getName();
        } catch (Exception e) {
            logger.error("Error al obtener el nombre del proyecto", e);
            throw e;
        } finally {
            restClient.close();
            logger.debug("Cliente JiraRestClient cerrado");
        }
    }


}