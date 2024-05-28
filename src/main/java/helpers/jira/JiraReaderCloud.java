package helpers.jira;

import enviroment.Enviroment;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

public class JiraReaderCloud {
    private static final Logger logger = Logger.getLogger(JiraReaderCloud.class);
    private static Enviroment env = ConfigFactory.create(Enviroment.class);

    private JiraReaderCloud() {
    }

    public static String getProjectName(String projectKey) {
        try {
            String url = env.jiraUrlCloud() + "/project/" + URLEncoder.encode(projectKey, "UTF-8");
            HttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(url);
            String auth = env.jiraUserNameCloud() + ":" + env.jiraApiTokenCloud();
            String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
            request.setHeader("Authorization", "Basic " + encodedAuth);
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(result.toString());
            return jsonNode.get("name").asText();
        } catch (IOException e) {
            logger.error("Ocurrió un error al obtener el nombre del proyecto.", e);
            return null;
        }
    }
}