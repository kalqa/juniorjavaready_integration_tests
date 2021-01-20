package com.javaready.verifier.products.verification.producername;

import java.io.IOException;
import java.net.URI;

import com.javaready.product.CompanyDto;
import com.javaready.verifier.products.ProductVerificationResult;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompanyHttpVerificationService {

    private static final Logger LOG = LoggerFactory
            .getLogger(CompanyHttpVerificationService.class);

    private final String companyServiceUri;

    private final HttpClient client;

    public CompanyHttpVerificationService(String companyServiceUri) {
        this.companyServiceUri = companyServiceUri;
        this.client = HttpClientBuilder.create().build();
    }

    public ProductVerificationResult verify(CompanyDto companyDto) {
        try {
            HttpResponse response = client
                    .execute(new HttpGet(URI.create(companyServiceUri + companyDto.getCompanyName())));
            String externalStatus = EntityUtils.toString(response.getEntity());
            if (ProductVerificationResult.Status.VERIFICATION_PASSED.name()
                    .equals(externalStatus)) {
                return ProductVerificationResult.passed(companyDto.getUuid());
            }
        } catch (IOException exception) {
            processException(exception);
        }
        return ProductVerificationResult.failed(companyDto.getUuid());
    }

    void processException(IOException exception) {
        LOG.error("Http request execution failed.", exception);
    }
}
