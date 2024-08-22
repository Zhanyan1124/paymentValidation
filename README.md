# paymentValidation
This is a coding exercise for validating the payment using Spring Boot which includes the unit testing

How to setup
1. Clone the repository
2. Open in any IDE and cd to paymentValidation
3. Run ./gradlew test for executing the unit tests
4. Run PaymentvalidationApplication.java to start the web server
5. Test the API on endpoint (http://localhost:8080/validatePayment)

Accounts Data Stored in h2 database (in-memory database)
AccounID   Balance  Currency

    1        10.50    'USD'
    
    2       250.75    'EUR'
    
    3       500.00    'GBP'
    
    4         0.00    'MYR'


Supportable Currencies: [MYR, SGD, USD, GBP, EUR]

Sample Requests & Response

Sample 1:

JSON input 
{
    "accountID":1,
    "amount":5.5,
    "currency":"USD"
}

JSON response (HTTP status code: 200 OK)
{
  "Payment successful. Your account balance is 5.0"
}


Sample 2:

JSON input 
{
    "accountID":2,
    "amount":3,
    "currency":"MYR"
}

JSON response (HTTP status code: 200 OK)
{
  "Payment successful. Your account balance is 250.15"
}

Sample 3:

JSON input 
{
    "accountID":1,
    "amount":5.5,
    "currency":""
}

JSON response (HTTP status code: 400 Bad Request)
{
  "Invalid currency"
}


Sample 4:

JSON input 
{
    "accountID":1000,
    "amount":5.5,
    "currency":"GBP"
}

JSON response (HTTP status code: 400 Bad Request)
{
  "Account not found"
}


Sample 5:

JSON input 
{
    "accountID":3,
    "amount":-12,
    "currency":"GBP"
}

JSON response (HTTP status code: 400 Bad Request)
{
  "Invalid amount"
}


Sample 6:

JSON input 
{
    "accountID":1,
    "amount":50,
    "currency":"USD"
}

JSON response (HTTP status code: 400 Bad Request)
{
  "Insufficient balance"
}

