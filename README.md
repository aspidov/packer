# Packer
The project which solves knapsack problem by branch and bound method

## How to use it
1. Build the jar using 
`mvn clean package`

_Note: Java 11 is required._

2. Import jar to your project

3. Use com.mobiquityinc.packer.Packer#pack static method to make your knapsacks from you problem's file

## Input file format
Input file can consist of multiple lines in format like this:
```
81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
```

