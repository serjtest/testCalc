import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;

class CalculatorPostClient {

    private static final String HOST = "http://127.0.0.1:5000";

    private String createURL() {
        return HOST + "/resource";
    }

    float calculate(OperationType operation, int a, int b)
            throws IOException {

        // Given
        HttpPost request = new HttpPost(createURL());
        request.setHeader("Content-Type", "application/json");

        Request requestBody = new Request();
        requestBody.method = operation.getValue();
        requestBody.a = a;
        requestBody.b = b;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        String json = objectMapper.writeValueAsString(requestBody);

        StringEntity params = new StringEntity(json);
        request.setEntity(params);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        Gson g = new Gson();
        Response result = g.fromJson(responseHandler.handleResponse(httpResponse), Response.class);

        return result.result;
    }
}
