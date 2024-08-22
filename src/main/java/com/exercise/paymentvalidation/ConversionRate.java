package com.exercise.paymentvalidation;
import org.springframework.data.annotation.Id;

record ConversionRate(@Id Long id, String from_cur, String to_cur, Double rate) {
}
