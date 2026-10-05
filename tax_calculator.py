import sys

# Make sure the peso symbol displays correctly
sys.stdout.reconfigure(encoding="utf-8")


# Function to calculate Philippine income tax
def calculate_tax(annual_income):

    if annual_income <= 250000:

        tax = 0

    elif annual_income <= 400000:

        tax = (annual_income - 250000) * 0.15

    elif annual_income <= 800000:

        tax = 22500 + (annual_income - 400000) * 0.20

    elif annual_income <= 2000000:

        tax = 102500 + (annual_income - 800000) * 0.25

    elif annual_income <= 8000000:

        tax = 402500 + (annual_income - 2000000) * 0.30

    else:

        tax = 2202500 + (annual_income - 8000000) * 0.35

    return tax


# Get information from JavaScript
name = sys.stdin.readline().strip()

monthly_salary = float(
    sys.stdin.readline().strip()
)

other_income = float(
    sys.stdin.readline().strip()
)

months_worked = int(
    sys.stdin.readline().strip()
)

tax_withheld = float(
    sys.stdin.readline().strip()
)


# Calculate annual salary
annual_salary = monthly_salary * months_worked


# Calculate annual other income
annual_other_income = other_income * months_worked


# Calculate total annual income
annual_income = annual_salary + annual_other_income


# Calculate tax due
income_tax = calculate_tax(annual_income)


# Calculate tax after tax credits
net_tax = income_tax - tax_withheld


# Calculate estimated net income
net_income = annual_income - income_tax


# Display results
print("===== BIR INCOME TAX RESULT =====")

print("Employee Name:", name)

print(
    "Annual Basic Salary: ₱{:,.2f}".format(
        annual_salary
    )
)

print(
    "Annual Other Income: ₱{:,.2f}".format(
        annual_other_income
    )
)

print(
    "Total Annual Income: ₱{:,.2f}".format(
        annual_income
    )
)

print(
    "Tax Due: ₱{:,.2f}".format(
        income_tax
    )
)

print(
    "Tax Withheld: ₱{:,.2f}".format(
        tax_withheld
    )
)


# Determine whether there is tax payable or overpayment
if net_tax > 0:

    print(
        "Net Tax Payable: ₱{:,.2f}".format(
            net_tax
        )
    )

elif net_tax < 0:

    print(
        "Overpayment: ₱{:,.2f}".format(
            abs(net_tax)
        )
    )

else:

    print(
        "Net Tax Payable: ₱0.00"
    )


print(
    "Estimated Net Income: ₱{:,.2f}".format(
        net_income
    )
)