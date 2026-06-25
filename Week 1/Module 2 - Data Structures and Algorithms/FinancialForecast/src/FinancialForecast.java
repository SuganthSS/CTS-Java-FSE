public class FinancialForecast {

    public static double futureValue(double amount, double rate, int years) {

        if (years == 0) {
            return amount;
        }

        return futureValue(amount * (1 + rate), rate, years - 1);
    }

    public static void main(String[] args) {

        double amount = 15000.0;
        double rate = 0.065;
        int years = 10;

        double result = futureValue(amount, rate, years);

        System.out.printf("Investment Value after %d years = $%.2f", years, result);
    }
}
