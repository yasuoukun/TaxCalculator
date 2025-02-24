import java.util.Scanner;

public class TaxCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Please enter your annual income (Baht): ");
        double income = scanner.nextDouble();
        
        System.out.print("Please enter your deductible expenses (Baht): ");
        double expenses = scanner.nextDouble();
        
        double personalDeduction = 60000;
        
        System.out.print("Please enter the number of children eligible for tax deductions: ");
        int numChildren = scanner.nextInt();
        double childDeduction = numChildren * 30000;
        
        System.out.print("Please enter the spouse deduction (Baht): ");
        double spouseDeduction = scanner.nextDouble();
        spouseDeduction = Math.min(spouseDeduction, 60000);
        
        System.out.print("Please enter life insurance deductions (Baht): ");
        double lifeInsurance = scanner.nextDouble();
        lifeInsurance = Math.min(lifeInsurance, 100000);
        
        System.out.print("Please enter provident fund deductions (Baht): ");
        double providentFund = scanner.nextDouble();
        providentFund = Math.min(providentFund, 500000);
        
        System.out.print("Please enter other deductions (Baht): ");
        double otherDeductions = scanner.nextDouble();
        
        double totalDeductions = personalDeduction + spouseDeduction + childDeduction + lifeInsurance + providentFund + otherDeductions;
        double netIncome = income - expenses - totalDeductions;
        if (netIncome < 0) netIncome = 0;
        double tax = calculateTax(netIncome);
        
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-35s %15s %20s\n", "Item", "Amount Deducted", "Maximum Deductible Amount");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-35s %15.2f Baht %20.2f Baht\n", "Personal Deduction", personalDeduction, 60000.0);
        System.out.printf("%-35s %15.2f Baht %20s\n", "Child Deduction (" + numChildren + " Children)", childDeduction, "Unlimited");
        System.out.printf("%-35s %15.2f Baht %20.2f Baht\n", "Spouse Deduction", spouseDeduction, 60000.0);
        System.out.printf("%-35s %15.2f Baht %20.2f Baht\n", "Life Insurance Deduction", lifeInsurance, 100000.0);
        System.out.printf("%-35s %15.2f Baht %20.2f Baht\n", "Provident Fund Deduction", providentFund, 500000.0);
        System.out.printf("%-35s %15.2f Baht %20s\n", "Other Deductions", otherDeductions, "Unlimited");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-35s %15.2f Baht\n", "Total Deductions", totalDeductions);
        System.out.printf("%-35s %15.2f Baht\n", "Net Income", netIncome);
        System.out.printf("%-35s %15.2f Baht\n", "Tax Payable", tax);
        System.out.println("---------------------------------------------------------------");
        
        scanner.close();
    }
    
    public static double calculateTax(double income) {
        double tax = 0;
        double[][] brackets = {
            {150000, 0.00},
            {300000, 0.05},
            {500000, 0.10},
            {750000, 0.15},
            {1000000, 0.20},
            {2000000, 0.25},
            {5000000, 0.30},
            {Double.MAX_VALUE, 0.35}
        };
        
        double previousLimit = 0;
        for (double[] bracket : brackets) {
            double limit = bracket[0];
            double rate = bracket[1];
            
            if (income > previousLimit) {
                double taxableAmount = Math.min(income - previousLimit, limit - previousLimit);
                tax += taxableAmount * rate;
            } else {
                break;
            }
            previousLimit = limit;
        }
        
        return tax;
    }
}
