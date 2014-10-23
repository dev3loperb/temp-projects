package com.github.ipergenitsa;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by ipergenitsa on 23.10.14.
 */
public class MainWireMockTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8081);

    @Test
    public void testGetSuccessful() throws URISyntaxException {
        stubFor(get(urlMatching("/my/resource/.*"))
                .withHeader("Accept", equalTo("text/html"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/html")
                        .withBody("<response>Some content</response>")));

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(new URI("http://127.0.0.1:8081")).path("/my/resource/abc");
        String entity = webTarget.request()
                .header("Accept", "text/html")
                .get(String.class);

        System.out.println("entity = " + entity.toString());

        verify(getRequestedFor(urlMatching("/my/resource/[a-z0-9]+")));
    }
}
