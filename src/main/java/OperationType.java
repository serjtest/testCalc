public class OperationType
{

    private String value;

    public String getValue()
    {
        return this.value;
    }

    private OperationType(final String value) {

        this.value = value;
    }

    public static OperationType sum()
    {
        return new OperationType("+");
    }

    public static OperationType subtraction()
    {
        return new OperationType("-");
    }

    public static OperationType multiply()
    {
        return new OperationType("*");
    }

    public static OperationType division()
    {
        return new OperationType("/");
    }

}
