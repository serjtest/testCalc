import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.apache.http.*;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(DataProviderRunner.class)
public class CalcPostTest {

    @Test
    @UseDataProvider("dataProviderCalculate")
    public void givenArithmeticOperationWithArgument_whenCalculationRequestSent_then200CodeAndCorrectResultReceived(
            String method, String a, String b, String result
    )
            throws IOException {

        // Given
        HttpPost request = new HttpPost("http://127.0.0.1:5000/resource");

        StringEntity params = new StringEntity(String.format("{\"method\": \"%1$s\",\"a\": %2$s,\"b\": %3$s}", method, a, b));
        request.setEntity(params);


        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        // Then
        Assert.assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK)
        );
        Assert.assertThat(
                responseHandler.handleResponse(httpResponse),
                equalTo(String.format("{\"result\":%1$s}", result))
        );
    }

    @DataProvider
    public static Object[][] dataProviderCalculate() {
        // @formatter:off
        return new Object[][]{
                {"+", "10", "2", "12"},
                {"-", "10", "2", "8"},
                {"*", "1", "4", "20"},
                {"/", "10", "2", "5"},
                /* ... */
        };
        // @formatter:on
    }
}
