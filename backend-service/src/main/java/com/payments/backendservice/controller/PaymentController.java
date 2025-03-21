package com.payments.backendservice.controller;

import com.payments.backendservice.dao.PaymentRequest;
import com.payments.backendservice.dao.PaymentResponse;
import com.payments.backendservice.collection.Account;
import com.payments.backendservice.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {

        Optional<Account> payerAccountOpt = accountRepository.findById(paymentRequest.getPayment_details().getAccount_holder_payer());
        Optional<Account> receiverAccountOpt = accountRepository.findById(paymentRequest.getPayment_details().getAccount_holder_receiver());

        // Check if both accounts exist
        if (payerAccountOpt.isEmpty() || receiverAccountOpt.isEmpty()) {
            return new ResponseEntity<>(new PaymentResponse(paymentRequest.getId(), "FAILURE"), HttpStatus.BAD_REQUEST);
        }

        // Get actual Account objects
        Account payerAccount = payerAccountOpt.get();
        Account receiverAccount = receiverAccountOpt.get();

        // Check if payer has sufficient balance
        int payerBalance = Integer.parseInt(payerAccount.getAccountBalance());
        int transferAmount = paymentRequest.getPayment_details().getAmount();

        if (payerBalance < transferAmount) {
            return new ResponseEntity<>(new PaymentResponse(paymentRequest.getId(), "FAILURE"), HttpStatus.BAD_REQUEST);
        }

        // Perform transaction
        payerAccount.setAccountBalance(String.valueOf(payerBalance - transferAmount));
        receiverAccount.setAccountBalance(String.valueOf(Integer.parseInt(receiverAccount.getAccountBalance()) + transferAmount));

        // Save updated accounts
        accountRepository.save(payerAccount);
        accountRepository.save(receiverAccount);

        // Return success response
        PaymentResponse response = new PaymentResponse(paymentRequest.getId(), "SUCCESS");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
