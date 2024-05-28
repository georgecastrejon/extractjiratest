package runner;

import helpers.jira.JiraReaderCloud;
import helpers.jira.JiraReaderServer;
import helpers.openia.OpenAIIntegration;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class UnitTest {

    @Test
    public void TestJiraServer() throws URISyntaxException, IOException {
        String projectKey = "LP";
        String projectName = JiraReaderServer.getProjectName(projectKey);

        System.out.println("Nombre del proyecto: "+projectName);
        //String textoGenerado = OpenAIIntegration.getDataOpenAi(projectName);
        //System.out.println("Quiero que actues como un investigador, dime algo resumido de 10 palabras sobre: "+textoGenerado);
    }

    @Test
    public void TestJiraCloud() {
        String projectKey = "LAPOSI";
        String projectName = JiraReaderCloud.getProjectName(projectKey);
        System.out.println("Nombre del proyecto cloud: " + projectName);
    }
}