-- Scenario 1 : Apply 1% Discount for Senior Citizens

DECLARE
    CURSOR customerList IS
        SELECT CustomerID, Age
        FROM Customers;
BEGIN
    FOR customer IN customerList LOOP

        IF customer.Age > 60 THEN

            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE CustomerID = customer.CustomerID;

        END IF;

    END LOOP;

    COMMIT;
END;
/

-- Scenario 2 : Promote High Balance Customers to VIP

DECLARE
    CURSOR customerList IS
        SELECT CustomerID, Balance
        FROM Customers;
BEGIN
    FOR customer IN customerList LOOP

        IF customer.Balance > 10000 THEN

            UPDATE Customers
            SET IsVIP = 'TRUE'
            WHERE CustomerID = customer.CustomerID;

        END IF;

    END LOOP;

    COMMIT;
END;
/

-- Scenario 3 : Display Loan Due Reminders

DECLARE
    CURSOR dueLoans IS
        SELECT CustomerID, LoanID
        FROM Loans
        WHERE DueDate BETWEEN SYSDATE AND SYSDATE + 30;
BEGIN

    FOR loan IN dueLoans LOOP

        DBMS_OUTPUT.PUT_LINE(
            'Reminder: Loan ' || loan.LoanID ||
            ' for Customer ' || loan.CustomerID ||
            ' is due within the next 30 days.'
        );

    END LOOP;

END;
/