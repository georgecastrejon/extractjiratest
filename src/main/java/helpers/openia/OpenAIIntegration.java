package helpers.openia;

import enviroment.Enviroment;
import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenAIIntegration {
    private static final Logger logger = Logger.getLogger(OpenAIIntegration.class);
    private static Enviroment env = ConfigFactory.create(Enviroment.class);

    private OpenAIIntegration(){}

    public static String getDataOpenAi(String texto) {
        try {
            URL url = new URL("https://api.openai.com/v1/engines/davinci/completions");

            // Datos de la solicitud (en este caso, el prompt para la generación de texto)
            String requestData = "{\"prompt\": \"" + texto + "\", \"max_tokens\": 50}";

            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + env.openaiApiKey());
            connection.setDoOutput(true);

            // Enviar los datos de la solicitud
            OutputStream os = connection.getOutputStream();
            os.write(requestData.getBytes());
            os.flush();
            os.close();

            // Leer la respuesta de la API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Devolver la respuesta
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Ocurrió un error en el api de OpenAi: "+e);
            return null;
        }
    }
}
