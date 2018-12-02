import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(DataProviderRunner.class)
public class CalcGetTest {

    @Test
    @UseDataProvider("dataProviderCalculate")
    public void givenArithmeticOperationWithArgument_whenCalculationRequestSent_then200CodeAndCorrectResultReceived(
             OperationType operation, int a, int b, float expectedResult
    )
            throws IOException {

        CalculatorClient calculatorClient = new CalculatorClient();
        float calculationResult = calculatorClient.calculate(operation, a, b);

        // Then
        Assert.assertThat(
                expectedResult,
                equalTo(calculationResult)
        );

    }

    @DataProvider
    public static Object[][] dataProviderCalculate() {
        // @formatter:off
        return new Object[][] {
                { OperationType.sum(), 4, 2, (float) 6 },
                { OperationType.subtraction(), 4, 2, (float) 2 },
                { OperationType.multiply(), 4, 2, (float) 8 },
                { OperationType.division(), 4, 2, (float) 2 },
                /* ... */
        };
        // @formatter:on
    }
}
