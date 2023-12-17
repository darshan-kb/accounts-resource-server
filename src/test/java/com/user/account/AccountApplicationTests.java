package com.user.account;

import com.user.account.dto.UserDTO;
import com.user.account.entities.Client;
import com.user.account.entities.GameServer;
import com.user.account.repository.ClientRepository;
import com.user.account.repository.GameServerRepository;
import com.user.account.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootTest
class AccountApplicationTests {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	TokenService tokenService;
	@Autowired
	GameServerRepository gameServerRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void addClient(){
		clientRepository.save(new Client("auth-server",encoder.encode("Henry2023!"),"ROLE_USER"));
	}

	@Test
	void findClient(){
		Optional<Client> c = clientRepository.findByClientname("auth-server");
		System.out.println(c.get());
	}

	@Test
	void testSlotMachineApi(){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.set("Authorization","Bearer "+tokenService.getToken());
		HttpEntity<String> entity = new HttpEntity(new UserDTO("Darshan","darshanbehere@gmail.com","ADMIN") ,headers2);

		ResponseEntity<String> response2 = restTemplate.exchange("http://localhost:8081/accountw", HttpMethod.POST, entity, String.class);
		System.out.println(response2.getBody());
	}

	@Test
	void gameServerDetails(){
		gameServerRepository.save(new GameServer("slotmachine","http://localhost:8081/account"));
		gameServerRepository.save(new GameServer("spinmachine","http://localhost:9090/account"));
	}
}
