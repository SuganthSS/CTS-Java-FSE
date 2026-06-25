-- Scenario 1 : Process Monthly Interest

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
AS
BEGIN

    UPDATE Accounts
    SET Balance = Balance + (Balance * 0.01)
    WHERE AccountType = 'Savings';

    COMMIT;

END;
/

-- Scenario 2 : Update Employee Bonus

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus
(
    deptName IN VARCHAR2,
    bonusPercent IN NUMBER
)
AS
BEGIN

    UPDATE Employees
    SET Salary = Salary + (Salary * bonusPercent / 100)
    WHERE Department = deptName;

    COMMIT;

END;
/

-- Scenario 3 : Transfer Funds

CREATE OR REPLACE PROCEDURE TransferFunds
(
    sourceAccount IN NUMBER,
    destinationAccount IN NUMBER,
    transferAmount IN NUMBER
)
AS
    availableBalance NUMBER;
BEGIN

    SELECT Balance
    INTO availableBalance
    FROM Accounts
    WHERE AccountID = sourceAccount;

    IF availableBalance >= transferAmount THEN

        UPDATE Accounts
        SET Balance = Balance - transferAmount
        WHERE AccountID = sourceAccount;

        UPDATE Accounts
        SET Balance = Balance + transferAmount
        WHERE AccountID = destinationAccount;

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Amount transferred successfully.');

    ELSE

        DBMS_OUTPUT.PUT_LINE('Transfer failed. Insufficient balance.');

        ROLLBACK;

    END IF;

END;
/