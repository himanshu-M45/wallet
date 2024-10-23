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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterServiceTest {

    @Mock
    private ManagedChannel channel;

    @Mock
    private CurrencyConverterGrpc.CurrencyConverterBlockingStub blockingStub;

    @InjectMocks
    private CurrencyConverterService currencyConverterService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(currencyConverterService, "host", "localhost");
        ReflectionTestUtils.setField(currencyConverterService, "port", 50051);
        currencyConverterService.init();
    }

    @Test
    void testConvertCurrency_Success() {
        double amount = 100.0;
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        double convertedAmount = 85.0;

        ConvertRequest request = ConvertRequest.newBuilder()
                .setAmount(amount)
                .setSenderCurrencyType(fromCurrency)
                .setReceiverCurrencyType(toCurrency)
                .build();

        ConvertResponse response = ConvertResponse.newBuilder()
                .setConvertedAmount(convertedAmount)
                .build();

        when(blockingStub.convert(request)).thenReturn(response);

        double result = currencyConverterService.convertCurrency(amount, fromCurrency, toCurrency);
        assertEquals(convertedAmount, result);

        verify(blockingStub, times(1)).convert(request);
    }

    @Test
    void testConvertCurrency_Failure() {
        double amount = 100.0;
        String fromCurrency = "USD";
        String toCurrency = "EUR";

        ConvertRequest request = ConvertRequest.newBuilder()
                .setAmount(amount)
                .setSenderCurrencyType(fromCurrency)
                .setReceiverCurrencyType(toCurrency)
                .build();

        when(blockingStub.convert(request)).thenThrow(new RuntimeException("gRPC error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            currencyConverterService.convertCurrency(amount, fromCurrency, toCurrency);
        });

        assertEquals("Currency conversion failed", exception.getMessage());
        verify(blockingStub, times(0)).convert(request);
    }
}