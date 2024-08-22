package com.exercise.paymentvalidation;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentvalidationApplicationTests {
	@Autowired TestRestTemplate restTemplate;

	@Test
	void shouldReturnValidPaymentWhenValidDataWithoutConversion() {
		Payment payment = new Payment(1L,1.5,"USD");
		ResponseEntity<String> response = restTemplate.postForEntity("/validatePayment", payment, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		String responseBody = response.getBody();
		assertThat(responseBody).isNotNull();
		String[] parts = responseBody.split(" Your account balance is ");
		String message = parts[0];
		String balance = parts[1];
		assertThat(message).isEqualTo("Payment successful.");
		assertThat(balance).isEqualTo("9.0");
	}

	@Test
	void shouldReturnValidPaymentWhenValidDataWithConversion() {
		Payment payment = new Payment(1L,1.5,"MYR");
		ResponseEntity<String> response = restTemplate.postForEntity("/validatePayment", payment, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		String responseBody = response.getBody();
		assertThat(responseBody).isNotNull();
		String[] parts = responseBody.split(" Your account balance is ");
		String message = parts[0];
		String balance = parts[1];
		assertThat(message).isEqualTo("Payment successful.");
		assertThat(balance).isEqualTo("10.17");
	}

	@Test
	void shouldReturnInvalidPaymentWhenAccountNotFound() {
		Payment payment = new Payment(1000L,1.5,"MYR");
		ResponseEntity<String> response = restTemplate.postForEntity("/validatePayment", payment, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isEqualTo("Account not found");
	}

	@Test
	void shouldReturnInvalidPaymentWhenInvalidAmount() {
		Payment payment = new Payment(1L,-1.5,"MYR");
		ResponseEntity<String> response = restTemplate.postForEntity("/validatePayment", payment, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isEqualTo("Invalid amount");
	}

	@Test
	void shouldReturnInvalidPaymentWhenInvalidAmount2() {
		Payment payment = new Payment(1L,0.0,"MYR");
		ResponseEntity<String> response = restTemplate.postForEntity("/validatePayment", payment, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isEqualTo("Invalid amount");
	}

	@Test
	void shouldReturnInvalidPaymentWhenInsufficientBalanceWithoutConversion() {
		Payment payment = new Payment(1L,10000000.0,"USD");
		ResponseEntity<String> response = restTemplate.postForEntity("/validatePayment", payment, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isEqualTo("Insufficient balance");
	}
	@Test
	void shouldReturnInvalidPaymentWhenInsufficientBalanceWithConversion() {
		Payment payment = new Payment(1L,10000000.0,"MYR");
		ResponseEntity<String> response = restTemplate.postForEntity("/validatePayment", payment, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isEqualTo("Insufficient balance");
	}

	@Test
	void shouldReturnInvalidPaymentWhenInvalidCurrency() {
		Payment payment = new Payment(1L,1.50,"ABC");
		ResponseEntity<String> response = restTemplate.postForEntity("/validatePayment", payment, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isEqualTo("Invalid currency");
	}

}
