# Labour Management Utilities — Test Plan

## 1. Purpose

This document defines the test cases for the standalone labour wage calculator and labour-entry validation utility in KisanMitra.

## 2. Wage Calculator Tests

| Test scenario | Input | Expected result |
|---|---|---|
| Normal wage calculation | 6 days, ₹500/day | ₹3,000 |
| Zero days worked | 0 days, ₹500/day | ₹0 |
| Negative days worked | -1 day, ₹500/day | Reject input |
| Negative daily rate | 5 days, -₹100/day | Reject input |
| Non-finite daily rate | NaN or Infinity | Reject input |

## 3. Labour Entry Validation Tests

| Test scenario | Input | Expected result |
|---|---|---|
| Valid worker name | `Ramesh` | Valid |
| Blank worker name | Whitespace only | Invalid |
| Valid days worked | 10 | Valid |
| Zero days worked | 0 | Valid |
| Negative days worked | -1 | Invalid |
| Valid daily rate | ₹500 | Valid |
| Negative daily rate | -₹1 | Invalid |
| Non-finite daily rate | NaN or Infinity | Invalid |

## 4. Verification Procedure

1. Run the project's available unit-test task.
2. Review test failures and identify whether they originate in the test code, application code, or build configuration.
3. Record the actual result for each test.
4. Do not report a test as passed unless it ran successfully.

## 5. Scope and Limitations

The calculator and validator are standalone utilities. This test plan does not claim that they are integrated into the running application's screens.

The project's Gradle and Android SDK compatibility issue must be resolved before a successful test run can be confirmed.
