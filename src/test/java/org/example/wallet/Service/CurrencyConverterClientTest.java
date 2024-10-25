package org.example.wallet.Service;

import com.example.wallet.grpc.CurrencyConverterGrpc;
import com.example.wallet.grpc.ConvertRequest;
import com.example.wallet.grpc.ConvertResponse;
import io.grpc.ManagedChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyConverterClientTest {

    @Mock
    private ManagedChannel channel;

    @Mock
    private CurrencyConverterGrpc.CurrencyConverterBlockingStub blockingStub;

    @InjectMocks
    private CurrencyConverterClient currencyConverterClient;

    @BeforeEach
    void setUp() {
        // Directly set the mocked blockingStub
        ReflectionTestUtils.setField(currencyConverterClient, "blockingStub", blockingStub);
    }

    @Test
    void testConvertCurrencySuccess() {
        double amount = 100.0;
        String senderCurrency = "USD";
        String receiverCurrency = "EUR";
        double expectedConvertedAmount = 85.0;

        ConvertResponse response = ConvertResponse.newBuilder()
                .setConvertedAmount(expectedConvertedAmount)
                .build();

        when(blockingStub.convert(any(ConvertRequest.class))).thenReturn(response);

        double actualConvertedAmount = currencyConverterClient.convertCurrency(amount, senderCurrency, receiverCurrency);

        assertEquals(expectedConvertedAmount, actualConvertedAmount);
        verify(blockingStub, times(1)).convert(any(ConvertRequest.class));
    }

    @Test
    void testConvertCurrencyException() {
        double amount = 100.0;
        String senderCurrency = "USD";
        String receiverCurrency = "EUR";

        when(blockingStub.convert(any(ConvertRequest.class))).thenThrow(new RuntimeException("gRPC error"));

        try {
            currencyConverterClient.convertCurrency(amount, senderCurrency, receiverCurrency);
        } catch (RuntimeException e) {
            assertEquals("Currency conversion failed", e.getMessage());
        }

        verify(blockingStub, times(1)).convert(any(ConvertRequest.class));
    }

    @Test
    void testConvertINRtoUSD() {
        double amount = 100.0;
        String senderCurrency = "INR";
        String receiverCurrency = "USD";
        double expectedConvertedAmount = 1.3;

        ConvertResponse response = ConvertResponse.newBuilder()
                .setConvertedAmount(expectedConvertedAmount)
                .build();

        when(blockingStub.convert(any(ConvertRequest.class))).thenReturn(response);

        double actualConvertedAmount = currencyConverterClient.convertCurrency(amount, senderCurrency, receiverCurrency);

        assertEquals(expectedConvertedAmount, actualConvertedAmount);
        verify(blockingStub, times(1)).convert(any(ConvertRequest.class));
    }

    @Test
    void testConvertINRtoEUR() {
        double amount = 100.0;
        String senderCurrency = "INR";
        String receiverCurrency = "EUR";
        double expectedConvertedAmount = 1.1;

        ConvertResponse response = ConvertResponse.newBuilder()
                .setConvertedAmount(expectedConvertedAmount)
                .build();

        when(blockingStub.convert(any(ConvertRequest.class))).thenReturn(response);

        double actualConvertedAmount = currencyConverterClient.convertCurrency(amount, senderCurrency, receiverCurrency);

        assertEquals(expectedConvertedAmount, actualConvertedAmount);
        verify(blockingStub, times(1)).convert(any(ConvertRequest.class));
    }

    @Test
    void testConvertUSDtoINR() {
        double amount = 100.0;
        String senderCurrency = "USD";
        String receiverCurrency = "INR";
        double expectedConvertedAmount = 7500.0;

        ConvertResponse response = ConvertResponse.newBuilder()
                .setConvertedAmount(expectedConvertedAmount)
                .build();

        when(blockingStub.convert(any(ConvertRequest.class))).thenReturn(response);

        double actualConvertedAmount = currencyConverterClient.convertCurrency(amount, senderCurrency, receiverCurrency);

        assertEquals(expectedConvertedAmount, actualConvertedAmount);
        verify(blockingStub, times(1)).convert(any(ConvertRequest.class));
    }

    @Test
    void testConvertEURtoUSD() {
        double amount = 100.0;
        String senderCurrency = "EUR";
        String receiverCurrency = "USD";
        double expectedConvertedAmount = 118.0;

        ConvertResponse response = ConvertResponse.newBuilder()
                .setConvertedAmount(expectedConvertedAmount)
                .build();

        when(blockingStub.convert(any(ConvertRequest.class))).thenReturn(response);

        double actualConvertedAmount = currencyConverterClient.convertCurrency(amount, senderCurrency, receiverCurrency);

        assertEquals(expectedConvertedAmount, actualConvertedAmount);
        verify(blockingStub, times(1)).convert(any(ConvertRequest.class));
    }

    @Test
    void testConvertEURtoINR() {
        double amount = 100.0;
        String senderCurrency = "EUR";
        String receiverCurrency = "INR";
        double expectedConvertedAmount = 8800.0;

        ConvertResponse response = ConvertResponse.newBuilder()
                .setConvertedAmount(expectedConvertedAmount)
                .build();

        when(blockingStub.convert(any(ConvertRequest.class))).thenReturn(response);

        double actualConvertedAmount = currencyConverterClient.convertCurrency(amount, senderCurrency, receiverCurrency);

        assertEquals(expectedConvertedAmount, actualConvertedAmount);
        verify(blockingStub, times(1)).convert(any(ConvertRequest.class));
    }

    @Test
    void testConvertCurrencyZeroAmount() {
        double amount = 0.0;
        String senderCurrency = "USD";
        String receiverCurrency = "EUR";
        double expectedConvertedAmount = 0.0;

        ConvertResponse response = ConvertResponse.newBuilder()
                .setConvertedAmount(expectedConvertedAmount)
                .build();

        when(blockingStub.convert(any(ConvertRequest.class))).thenReturn(response);

        double actualConvertedAmount = currencyConverterClient.convertCurrency(amount, senderCurrency, receiverCurrency);

        assertEquals(expectedConvertedAmount, actualConvertedAmount);
        verify(blockingStub, times(1)).convert(any(ConvertRequest.class));
    }

    @Test
    void testConvertCurrencyNegativeAmount() {
        double amount = -100.0;
        String senderCurrency = "USD";
        String receiverCurrency = "EUR";
        double expectedConvertedAmount = -85.0;

        ConvertResponse response = ConvertResponse.newBuilder()
                .setConvertedAmount(expectedConvertedAmount)
                .build();

        when(blockingStub.convert(any(ConvertRequest.class))).thenReturn(response);

        double actualConvertedAmount = currencyConverterClient.convertCurrency(amount, senderCurrency, receiverCurrency);

        assertEquals(expectedConvertedAmount, actualConvertedAmount);
        verify(blockingStub, times(1)).convert(any(ConvertRequest.class));
    }

    @Test
    void testConvertCurrencyInvalidCurrencyType() {
        double amount = 100.0;
        String senderCurrency = "INVALID";
        String receiverCurrency = "EUR";

        when(blockingStub.convert(any(ConvertRequest.class))).thenThrow(new RuntimeException("Invalid currency type"));

        try {
            currencyConverterClient.convertCurrency(amount, senderCurrency, receiverCurrency);
        } catch (RuntimeException e) {
            assertEquals("Currency conversion failed", e.getMessage());
        }

        verify(blockingStub, times(1)).convert(any(ConvertRequest.class));
    }

    @Test
    void testConvertCurrencySameCurrencyType() {
        double amount = 100.0;
        String senderCurrency = "USD";
        String receiverCurrency = "USD";
        double expectedConvertedAmount = 100.0;

        ConvertResponse response = ConvertResponse.newBuilder()
                .setConvertedAmount(expectedConvertedAmount)
                .build();

        when(blockingStub.convert(any(ConvertRequest.class))).thenReturn(response);

        double actualConvertedAmount = currencyConverterClient.convertCurrency(amount, senderCurrency, receiverCurrency);

        assertEquals(expectedConvertedAmount, actualConvertedAmount);
        verify(blockingStub, times(1)).convert(any(ConvertRequest.class));
    }
}