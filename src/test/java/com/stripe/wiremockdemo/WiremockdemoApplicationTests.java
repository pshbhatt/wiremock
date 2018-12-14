package com.stripe.wiremockdemo;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.stripe.wiremockdemo.controller.StripeController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("integration")
@Profile("integration")
public class WiremockdemoApplicationTests {


	RestTemplate restTemplate;
	ResponseEntity response;


	String json = "{\n" +
			"  \"id\": \"ch_1DgzUF2eZvKYlo2CJcMNgtr6\",\n" +
			"  \"object\": \"charge\",\n" +
			"  \"amount\": 100000,\n" +
			"  \"amount_refunded\": 0,\n" +
			"  \"application\": null,\n" +
			"  \"application_fee\": null,\n" +
			"  \"balance_transaction\": \"txn_19XJJ02eZvKYlo2ClwuJ1rbA\",\n" +
			"  \"captured\": false,\n" +
			"  \"created\": 1544727883,\n" +
			"  \"currency\": \"usd\",\n" +
			"  \"customer\": \"cus_E9I4fj0YA2RCCd\",\n" +
			"  \"description\": \"kt97dlf09wy2053mw211jeu1\",\n" +
			"  \"destination\": null,\n" +
			"  \"dispute\": null,\n" +
			"  \"failure_code\": \"card_declined\",\n" +
			"  \"failure_message\": \"Your card was declined.\",\n" +
			"  \"fraud_details\": {\n" +
			"  },\n" +
			"  \"invoice\": null,\n" +
			"  \"livemode\": false,\n" +
			"  \"metadata\": {\n" +
			"  },\n" +
			"  \"on_behalf_of\": null,\n" +
			"  \"order\": null,\n" +
			"  \"outcome\": {\n" +
			"    \"network_status\": \"not_sent_to_network\",\n" +
			"    \"reason\": \"highest_risk_level\",\n" +
			"    \"risk_level\": \"highest\",\n" +
			"    \"risk_score\": 91,\n" +
			"    \"rule\": \"block_if_high_risk__enabled\",\n" +
			"    \"seller_message\": \"Stripe blocked this payment as too risky.\",\n" +
			"    \"type\": \"blocked\"\n" +
			"  },\n" +
			"  \"paid\": false,\n" +
			"  \"payment_intent\": null,\n" +
			"  \"receipt_email\": null,\n" +
			"  \"receipt_number\": null,\n" +
			"  \"refunded\": false,\n" +
			"  \"refunds\": {\n" +
			"    \"object\": \"list\",\n" +
			"    \"data\": [\n" +
			"\n" +
			"    ],\n" +
			"    \"has_more\": false,\n" +
			"    \"total_count\": 0,\n" +
			"    \"url\": \"/v1/charges/ch_1DgzUF2eZvKYlo2CJcMNgtr6/refunds\"\n" +
			"  },\n" +
			"  \"review\": null,\n" +
			"  \"shipping\": null,\n" +
			"  \"source\": {\n" +
			"    \"id\": \"card_1DgzUB2eZvKYlo2Cf4Y71vbA\",\n" +
			"    \"object\": \"card\",\n" +
			"    \"address_city\": null,\n" +
			"    \"address_country\": null,\n" +
			"    \"address_line1\": null,\n" +
			"    \"address_line1_check\": null,\n" +
			"    \"address_line2\": null,\n" +
			"    \"address_state\": null,\n" +
			"    \"address_zip\": null,\n" +
			"    \"address_zip_check\": null,\n" +
			"    \"brand\": \"Visa\",\n" +
			"    \"country\": \"US\",\n" +
			"    \"customer\": \"cus_E9I4fj0YA2RCCd\",\n" +
			"    \"cvc_check\": \"unavailable\",\n" +
			"    \"dynamic_last4\": null,\n" +
			"    \"exp_month\": 12,\n" +
			"    \"exp_year\": 2022,\n" +
			"    \"fingerprint\": \"yTFMYiPXiPa5AoSn\",\n" +
			"    \"funding\": \"credit\",\n" +
			"    \"last4\": \"4954\",\n" +
			"    \"metadata\": {\n" +
			"    },\n" +
			"    \"name\": \"muneeb@gmail.com\",\n" +
			"    \"tokenization_method\": null\n" +
			"  },\n" +
			"  \"source_transfer\": null,\n" +
			"  \"statement_descriptor\": null,\n" +
			"  \"status\": \"failed\",\n" +
			"  \"transfer_group\": null\n" +
			"}\n";

	@Test
	public void contextLoads() {
	}
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8090);

	@Before
	public void setup() throws Exception {
		restTemplate = new RestTemplate();
		response = null;
	}

	@Test
	public void testWireMockRest() {
		String URL  = "https://api.stripe.com/v1/charges -u sk_test_4eC39HqLyjWDarjtT1zdp7dc: -d amount=2000 -d currency=usd  -d source=tok_amex -d description=Charge for jenny.rosen@example.com";
		RestTemplate restTemplate = new RestTemplate();
		response = restTemplate.getForEntity(URL, String.class);
		assertThat("Verify Response Body", response.getBody().equals(""));
		assertThat("Verify Status Code", response.getStatusCode().equals(HttpStatus.OK));

	}

	@Test
	public void testWiremock() {
		String URL  = "/v1/charges -u sk_test_4eC39HqLyjWDarjtT1zdp7dc: -d amount=2000 -d currency=usd  -d source=tok_amex -d description=Charge for jenny.rosen@example.com";
		stubFor(get(urlEqualTo("/create"))
						.willReturn(aResponse()
										.withStatus(HttpStatus.OK.value())
										.withHeader("Content-Type", MediaType.TEXT_PLAIN_VALUE)
										.withBody(json)));
		response = restTemplate.getForEntity(URL, String.class);
		assertThat("Verify Response Body", response.getBody().equals(""));
		assertThat("Verify Status Code", response.getStatusCode().equals(HttpStatus.OK));
		verify(getRequestedFor(urlMatching("/create.*")));

	}


	@Test
	public void serviceTest() throws IOException, ParseException {
		StripeController controller = new StripeController();
		wireMockRule.stubFor(get(urlEqualTo("https://api.stripe.com/v1/charges -u sk_test_4eC39HqLyjWDarjtT1zdp7dc: -d amount=2000 -d currency=usd  -d source=tok_amex -d description=Charge for jenny.rosen@example.com"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody(json)));

		//controller.sendDataByDate(json);

	}

}

