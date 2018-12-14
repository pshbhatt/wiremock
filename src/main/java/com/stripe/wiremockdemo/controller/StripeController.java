package com.stripe.wiremockdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wiremock.org.apache.http.client.methods.CloseableHttpResponse;
import wiremock.org.apache.http.client.methods.HttpGet;
import wiremock.org.apache.http.impl.client.CloseableHttpClient;
import wiremock.org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@RestController
public class StripeController {

Logger log = LoggerFactory.getLogger(StripeController.class);
    private String convertHttpResponseToString(CloseableHttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }

    private String convertInputStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String string = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return string;
    }

    @GetMapping(value="/create")
    public void sendDataByDate(String input) throws IOException {
        String URL  = "https://api.stripe.com/v1/charges -u sk_test_4eC39HqLyjWDarjtT1zdp7dc: -d amount=2000 -d currency=usd  -d source=tok_amex -d description=Charge for jenny.rosen@example.com";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(URL);
        CloseableHttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertHttpResponseToString(httpResponse);
        log.debug(stringResponse);

    }
}
