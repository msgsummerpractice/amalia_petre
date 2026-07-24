package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import java.util.List;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.controller.UserController;
import com.example.demo.service.UserService;
import com.example.demo.repository.UsersRepo;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureRestTestClient
class DemoApplicationTests {


	@Autowired
	private RestTestClient restTestClient;

	// In order to run individual tests for the UserRepo and UserService, i have to comment all the other tests
	// So the tests can be run individually, but not all together

	// @MockitoBean
	// private UserService userService;

	// @MockitoBean
	// private UsersRepo usersRepo;

	// Testing the /users endpoint 
	// @Test
	// void testGetUsersEndpoint() throws Exception {
	// 	restTestClient.get().uri("/users")
	// 			.exchange()
	// 			.expectStatus().isOk()
	// 			.expectBody(String.class).isEqualTo("[{\"id\":1,\"name\":\"John Doe\",\"age\":26},{\"id\":2,\"name\":\"Jane Smith\",\"age\":30},{\"id\":3,\"name\":\"Alice Johnson\",\"age\":22}]");
		
	// }

	// Testing the service layer using Mockito

	// @Test
	// void testReturnFromService() throws Exception {
	// 	when(userService.getAllUsers()).thenReturn(List.of(
	// 			new User(1L, "John Doe", 26),
	// 			new User(2L, "Jane Smith", 30),
	// 			new User(3L, "Alice Johnson", 22)
	// 	));
	// 	restTestClient.get().uri("/users")
	// 			.exchange()
	// 			.expectStatus().isOk()
	// 			.expectBody(String.class).isEqualTo("[{\"id\":1,\"name\":\"John Doe\",\"age\":26},{\"id\":2,\"name\":\"Jane Smith\",\"age\":30},{\"id\":3,\"name\":\"Alice Johnson\",\"age\":22}]");
	// }

	// Testing the repository layer using Mockito

	// @Test
	// void testReturnFromRepo() throws Exception {
	// 	when(usersRepo.findAll()).thenReturn(List.of(
	// 			new User(1L, "John Doe", 26),
	// 			new User(2L, "Jane Smith", 30),
	// 			new User(3L, "Alice Johnson", 22)
	// 	));
	// 	restTestClient.get().uri("/users")
	// 			.exchange()
	// 			.expectStatus().isOk()
	// 			.expectBody(String.class).isEqualTo("[{\"id\":1,\"name\":\"John Doe\",\"age\":26},{\"id\":2,\"name\":\"Jane Smith\",\"age\":30},{\"id\":3,\"name\":\"Alice Johnson\",\"age\":22}]");
	// }


	// Testing endpoint validation /users/{id} with invalid id 
	@Test
	void testGetUserByIdEndpointWithInvalidId() throws Exception {
		restTestClient.get().uri("/users/-1")
				.exchange()
				.expectStatus().is5xxServerError();
	}

	@Test
	void testGetUserByIdEndpointWithInvalidId2() throws Exception {
		restTestClient.get().uri("/users/5")
				.exchange()
				.expectStatus().is5xxServerError();
	}

	// Testing endpoint validation /users/{id} with valid id
	@Test
	void testGetUserByIdEndpointWithValidId() throws Exception {
		restTestClient.get().uri("/users/2")
				.exchange()
				.expectStatus().is2xxSuccessful();
	}
}
