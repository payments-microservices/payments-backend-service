package com.payments.backendservice.controller;

import com.payments.backendservice.dao.PaymentRequest;
import com.payments.backendservice.dao.PaymentResponse;
import com.payments.backendservice.collection.Account;
import com.payments.backendservice.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {

        Account payerAccount = accountRepository.findByUserName(paymentRequest.getPayment_details().getAccount_holder_payer());
        Account receiverAccount = accountRepository.findByUserName(paymentRequest.getPayment_details().getAccount_holder_receiver());

        if (payerAccount == null || receiverAccount == null) {
            return new ResponseEntity<>(new PaymentResponse(null, "FAILURE"), HttpStatus.BAD_REQUEST);
        }

        if (Integer.parseInt(payerAccount.getAccountBalance()) < paymentRequest.getPayment_details().getAmount()) {
            return new ResponseEntity<>(new PaymentResponse(null, "FAILURE"), HttpStatus.BAD_REQUEST);
        }

        payerAccount.setAccountBalance(String.valueOf(Integer.parseInt(payerAccount.getAccountBalance()) - paymentRequest.getPayment_details().getAmount()));
        receiverAccount.setAccountBalance(String.valueOf(Integer.parseInt(receiverAccount.getAccountBalance()) + paymentRequest.getPayment_details().getAmount()));

        accountRepository.save(payerAccount);
        accountRepository.save(receiverAccount);

        PaymentResponse response = new PaymentResponse(paymentRequest.getId(), "SUCCESS");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
