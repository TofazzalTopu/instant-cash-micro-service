# Instant Cash Exchange House API Integration
```
1. Returns a list of outstanding transactions which belong to a receiving agent. API:- /transactions/outstanding.
2. Gets the status of the transaction associated with the provided Instant Cash Transaction Code (ICTC) number. API:- /transactions/status.
3. Returns the payment details of the outstanding transaction for the provided Instant Cash Transaction Code (ICTC) number. API:- /transactions/receive-payment.
4. Unlocks the transaction associated with the provided Instant Cash Transaction Code (ICTC) number. API:- /transactions/unlock.
4. Confirms the status of the transaction as 'Downloaded', 'Paid' or 'Report Error'. API:- /transactions/confirm.
```

# Instant Cash Exchange House API Scheduler

````
The process starts with the Scheduler (ApiClientScheduler class), which triggers at a specified interval defined in the properties file. 
The scheduler calls a processor, InstantCashAPIProcessor, to handle the API interaction and response processing.

1. The Scheduler invokes the `fetchICOutstandingRemittance()` method to retrieve a list of outstanding transactions.
2. If any transactions are found, it calls the `confirmOutstandingTransactionStatus()` method to update their statuses:
   - "D" for "Downloaded" status
   - "X" for "Exception reported from Agent" status (for transactions with issues such as "Duplicate", "Invalid Account Number", "Invalid Routing Number" or "Invalid IFSC Code")
3. For each valid transaction that has been successfully paid to the customer, the `notifyPaidStatus()` method is called to update the status to "Y" for "Paid."

````