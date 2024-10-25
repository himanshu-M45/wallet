package org.example.wallet.Service;

import com.example.wallet.grpc.CurrencyConverterGrpc;
import com.example.wallet.grpc.ConvertRequest;
import com.example.wallet.grpc.ConvertResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Slf4j
@Service
public class CurrencyConverterClient {

    private ManagedChannel channel;
    private CurrencyConverterGrpc.CurrencyConverterBlockingStub blockingStub;

    @Value("${grpc.server.host:localhost}")
    private String host;

    @Value("${grpc.server.port:50051}")
    private int port;

    @PostConstruct
    void init() {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = CurrencyConverterGrpc.newBlockingStub(channel);
        log.info("gRPC client initialized, connecting to {}:{}", host, port);
    }

    public double convertCurrency(double amount, String senderCurrency, String receiverCurrency) {
        try {
            ConvertRequest request = ConvertRequest.newBuilder()
                    .setAmount(amount)
                    .setSenderCurrencyType(senderCurrency)
                    .setReceiverCurrencyType(receiverCurrency)
                    .build();

            ConvertResponse response = blockingStub.convert(request);
            return response.getConvertedAmount();
        } catch (Exception e) {
            log.error("Error converting currency: {} {} to {}", amount, senderCurrency, receiverCurrency, e);
            throw new RuntimeException("Currency conversion failed", e);
        }
    }

    @PreDestroy
    private void cleanup() {
        if (channel != null) {
            channel.shutdown();
        }
    }
}