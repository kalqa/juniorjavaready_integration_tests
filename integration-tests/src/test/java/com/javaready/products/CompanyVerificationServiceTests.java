package com.javaready.products;

import java.time.LocalDate;
import java.util.UUID;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SocketUtils;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.BDDAssertions.then;

class CompanyVerificationServiceTests {

    int port = SocketUtils.findAvailableTcpPort();

    WireMockServer wireMockServer;

    CompanyVerificationService service = new CompanyVerificationService("http://localhost:" + port + "/");

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(options().port(port));
        wireMockServer.start();
        WireMock.configureFor(port);
    }

    private CloseableHttpClient httpClient() {
        return HttpClientBuilder.create()
                .setDefaultSocketConfig(SocketConfig.custom()
                        .setSoTimeout(1000)
                        .build())
                .build();
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void should_return_positive_verification() {
        WireMock.stubFor(WireMock.get("/Krakus")
                .willReturn(WireMock.aResponse().withBody("VERIFICATION_PASSED")));

        then(service.verify(krakus()).getStatus())
                .isEqualTo(ProductVerificationResult.Status.VERIFICATION_PASSED);
    }

    @Test
    void should_return_negative_verification() {
        WireMock.stubFor(WireMock.get("/Krakus")
                .willReturn(WireMock.aResponse().withBody("VERIFICATION_FAILED")));

        then(service.verify(krakus()).getStatus())
                .isEqualTo(ProductVerificationResult.Status.VERIFICATION_FAILED);
    }

    @Test
    void should_fail_with_connection_reset_by_peer() {
        WireMock.stubFor(WireMock.get("/Krakus")
                .willReturn(WireMock.aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));

        then(service.verify(krakus()).getStatus())
                .isEqualTo(ProductVerificationResult.Status.VERIFICATION_FAILED);
    }

    @Test
    void should_fail_with_empty_response() {
        WireMock.stubFor(WireMock.get("/Krakus")
                .willReturn(WireMock.aResponse().withFault(Fault.EMPTY_RESPONSE)));

        then(service.verify(krakus()).getStatus())
                .isEqualTo(ProductVerificationResult.Status.VERIFICATION_FAILED);
    }

    @Test
    void should_fail_with_malformed() {
        WireMock.stubFor(WireMock.get("/Krakus")
                .willReturn(WireMock.aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

        then(service.verify(krakus()).getStatus())
                .isEqualTo(ProductVerificationResult.Status.VERIFICATION_FAILED);
    }

    @Test
    void should_fail_with_random() {
        WireMock.stubFor(WireMock.get("/Krakus")
                .willReturn(WireMock.aResponse().withFault(Fault.RANDOM_DATA_THEN_CLOSE)));

        then(service.verify(krakus()).getStatus())
                .isEqualTo(ProductVerificationResult.Status.VERIFICATION_FAILED);
    }

    private CompanyDto krakus() {
        return new CompanyDto(UUID.randomUUID(), tooOldMeat());
    }

    Product tooOldMeat() {
        return new Product(UUID.randomUUID(), "Kiełbasa", "Krakus", LocalDate.now(), "18210116954");
    }
}

