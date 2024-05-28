package enviroment;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:proyect.properties"
})
public interface Enviroment extends Config{
    @Key("jira.url.server")
    String jiraUrlServer();

    @Key("jira.user.name.server")
    String jiraUserNameServer();

    @Key("jira.pwd.server")
    String jiraPwdServer();

    @Key("jira.url.cloud")
    String jiraUrlCloud();

    @Key("jira.user.name.cloud")
    String jiraUserNameCloud();

    @Key("jira.api.token.cloud")
    String jiraApiTokenCloud();

    @Key("openai.api.key")
    String openaiApiKey();

}