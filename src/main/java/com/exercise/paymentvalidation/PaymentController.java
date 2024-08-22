package com.exercise.paymentvalidation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.List;


@RestController
public class PaymentController {
    private final AccountRepository accountRepository;
    private final ConversionRateRepository conversionRateRepository;

    private PaymentController(AccountRepository accountRepository, ConversionRateRepository conversionRateRepository) {
        this.accountRepository = accountRepository;
        this.conversionRateRepository = conversionRateRepository;
    }

    @PostMapping("/validatePayment")
    private ResponseEntity<String> validatePayment(@RequestBody Payment payment) {

        //Check if the account exist
        Optional<Account> optAccount = accountRepository.findById(payment.accountID());
        if (optAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Account not found");
        }

        //Check if the input amount is valid
        if (payment.amount() <= 0) {
            return ResponseEntity.badRequest().body("Invalid amount");
        }

        //Check if the input amount is valid
        String[] currencies = {"USD", "EUR", "GBP", "MYR", "SGD"};
        boolean validCurrency = false;
        for (String currency : currencies) {
            if (payment.currency().equals(currency)) {
                validCurrency = true;
                break;
            }
        }
        if (!validCurrency) {
            return ResponseEntity.badRequest().body("Invalid currency");
        }

        Account account = optAccount.get();
        //Without Currency Conversion
        if (payment.currency().equals(account.getCurrency())){
            if (payment.amount() > account.getBalance()) {
                return ResponseEntity.badRequest().body("Insufficient balance");
            }
            double balance = account.getBalance();
            account.setBalance(balance - payment.amount());
        }

        //With Currency Conversion
        else{
            List<ConversionRate> conversionRates = (List<ConversionRate>) conversionRateRepository.findAll();
            Optional<ConversionRate> optRate = conversionRates.stream()
                    .filter(rate -> rate.from_cur().equals(payment.currency()) && rate.to_cur().equals(account.getCurrency()))
                    .findFirst();
            double rate = optRate.get().rate();
            if (payment.amount() * rate > account.getBalance()) {
                return ResponseEntity.badRequest().body("Insufficient balance");
            }
            double balance = account.getBalance();
            account.setBalance(balance - payment.amount()*rate);
        }


        return ResponseEntity.ok("Payment successful. Your account balance is " + String.valueOf(account.getBalance()));

    }
}
