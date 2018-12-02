import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class CalculatorClient
{

    private static final String HOST = "http://127.0.0.1:5000";

    private String createURL(OperationType operation, int a, int b)
            throws UnsupportedEncodingException {
        StringBuilder buldier = new StringBuilder();
        buldier
                .append(HOST)
                .append("/resource")
                .append("?")
                .append("method=")
                .append(URLEncoder.encode(operation.getValue(), "UTF-8"))
                .append("&a=")
                .append(a)
                .append("&b=")
                .append(b);
        return buldier.toString();
    }



    float calculate(OperationType operation, int a, int b)
        throws IOException {
        // Given
        HttpUriRequest request = new HttpGet(this.createURL(operation, a, b));

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        Gson g = new Gson();
        Response result = g.fromJson(responseHandler.handleResponse(httpResponse), Response.class);

        return result.result;
    }
}
