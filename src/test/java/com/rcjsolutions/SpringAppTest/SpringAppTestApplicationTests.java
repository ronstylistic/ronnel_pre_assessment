package com.rcjsolutions.SpringAppTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.aspectj.bridge.MessageUtil.fail;

@SpringBootTest
class SpringAppTestApplicationTests {

	@Test
	void ensureThatUserAPICallReturnStatusCode200() throws Exception {
		HttpClient client = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/users")).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		assert(response.statusCode() == 200);
		fail("FIXME");
	}

}
