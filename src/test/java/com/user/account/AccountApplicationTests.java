package com.user.account;

import com.user.account.entities.Client;
import com.user.account.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
class AccountApplicationTests {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private PasswordEncoder encoder;

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

}
