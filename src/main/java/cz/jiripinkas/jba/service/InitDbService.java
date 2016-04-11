package cz.jiripinkas.jba.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.jiripinkas.jba.entity.Blog;
import cz.jiripinkas.jba.entity.Item;
import cz.jiripinkas.jba.entity.Role;
import cz.jiripinkas.jba.entity.User;
import cz.jiripinkas.jba.repository.BlogRepository;
import cz.jiripinkas.jba.repository.ItemRepository;
import cz.jiripinkas.jba.repository.RoleRepository;
import cz.jiripinkas.jba.repository.UserRepository;

@Transactional
@Service
public class InitDbService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@PostConstruct
	public void init(){
		Role roleUser = new Role();
		roleUser.setName("ROLE_USER");
		roleRepository.save(roleUser);
		
		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_ADMIN");
		roleRepository.save(roleAdmin);
		
		User userAdmin = new User();
		userAdmin.setEnabled(true);
		userAdmin.setName("admin");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userAdmin.setPassword(encoder.encode("admin"));
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleUser);
		roles.add(roleAdmin);
		userAdmin.setRoles(roles);
		userRepository.save(userAdmin);
		
		Blog blogjavatut = new Blog();
		blogjavatut.setName("Java tutorial");
		blogjavatut.setUrl("http://feeds.feedburner.com/javavids?format=xml");
		blogjavatut.setUser(userAdmin);
		blogRepository.save(blogjavatut);
		
		Item item01 = new Item();
		item01.setBlog(blogjavatut);
		item01.setTitle("First");
		item01.setLink("http://www.javavids.com/");
		item01.setPublishedDate(new java.util.Date());
		itemRepository.save(item01);
	
		Item item02 = new Item();
		item02.setBlog(blogjavatut);
		item02.setTitle("Second");
		item02.setLink("http://www.javavids.com/");
		item02.setPublishedDate(new java.util.Date());
		itemRepository.save(item02);
		
		}

}
