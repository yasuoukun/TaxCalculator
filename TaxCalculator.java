import java.util.Scanner;

public class TaxCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Input income
        System.out.print("Enter your annual income (THB): ");
        double income = scanner.nextDouble();
        
        // Input expenses
        System.out.print("Enter deductible expenses (THB): ");
        double expenses = scanner.nextDouble();
        
        // Basic deduction
        double personalDeduction = 60000;
        System.out.println("Personal deduction: " + personalDeduction + " THB (Maximum 60,000 THB)");
        
        System.out.print("Enter number of children eligible for deduction: ");
        int numChildren = scanner.nextInt();
        double childDeduction = numChildren * 30000;
        System.out.println("Child deduction (30,000 THB per child): " + childDeduction + " THB (No limit)");
        
        System.out.print("Enter spouse deduction (THB): ");
        double spouseDeduction = scanner.nextDouble();
        spouseDeduction = Math.min(spouseDeduction, 60000);
        System.out.println("Spouse deduction: " + spouseDeduction + " THB (Maximum 60,000 THB)");
        
        System.out.print("Enter life insurance deduction (THB): ");
        double lifeInsurance = scanner.nextDouble();
        lifeInsurance = Math.min(lifeInsurance, 100000);
        System.out.println("Life insurance deduction: " + lifeInsurance + " THB (Maximum 100,000 THB)");
        
        System.out.print("Enter provident fund deduction (THB): ");
        double providentFund = scanner.nextDouble();
        providentFund = Math.min(providentFund, 500000);
        System.out.println("Provident fund deduction: " + providentFund + " THB (Maximum 500,000 THB including RMF and SSF)");
        
        System.out.print("Enter other deductions (THB): ");
        double otherDeductions = scanner.nextDouble();
        System.out.println("Other deductions: " + otherDeductions + " THB");
        
        // Calculate total deductions
        double totalDeductions = personalDeduction + spouseDeduction + childDeduction + lifeInsurance + providentFund + otherDeductions;
        System.out.println("Total deductions: " + totalDeductions + " THB");
        
        // Calculate net income
        double netIncome = income - expenses - totalDeductions;
        if (netIncome < 0) netIncome = 0;
        System.out.println("Net income after expenses and deductions: " + netIncome + " THB");
        
        // Calculate tax based on progressive rates
        double tax = calculateTax(netIncome);
        
        // Display result
        System.out.println("Tax payable: " + tax + " THB");
        
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