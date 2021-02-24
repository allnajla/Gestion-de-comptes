package com.iset.demo;

import com.iset.bp.dao.IClientRepository;
import com.iset.bp.dao.ICompteRepository;
import com.iset.bp.dao.IOperationRepository;
import com.iset.bp.entities.Client;
import com.iset.bp.entities.Compte;
import com.iset.bp.entities.CompteCourant;
import com.iset.bp.entities.CompteEpargne;
import com.iset.bp.entities.Retrait;
import com.iset.bp.entities.Versement;
import com.iset.bp.metier.IBanqueMetier;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
@EntityScan("com.iset.bp.entities")
@EnableJpaRepositories("com.iset.bp.dao")
@ComponentScan("com.iset.bp.metier")
@ComponentScan("com.iset.bp.web")
@Configuration //à ajouter pour la sécurité
@SpringBootApplication
public class VotreBanqueApplication implements CommandLineRunner{
@Autowired
private IClientRepository clientrepository;
@Autowired
	private ICompteRepository iCompteRepository;

@Autowired
	private IOperationRepository iOperationRepository;

//pour tester la couche metier
@Autowired
	private IBanqueMetier iBanqueMetier;
	public static void main(String[] args) {
		SpringApplication.run(VotreBanqueApplication.class, args);
		
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Client client1 = clientrepository.save(new Client("Najla", "allouche.najla@gmail.com"));
		Client client2 = clientrepository.save(new Client("khaoula", "khaoulajri@gmail.com"));
		Compte compte1 = iCompteRepository.save(new CompteCourant("compte1",new Date(),90000.0 ,client1,6000.0));
		Compte compte2 = iCompteRepository.save(new CompteEpargne("compte2",new Date(),6000.0, client2, 5.5));
		 
		//operations de compte1
		  iOperationRepository.save(new Versement( new Date(), 9000.0,compte1));
		  iOperationRepository.save(new Versement( new Date(), 6000.0,compte1));
	      iOperationRepository.save(new Versement( new Date(), 2300.0,compte1));
		  iOperationRepository.save(new Retrait( new Date(),9000.0,compte1));
		
		
		//operations de compte2
		  iOperationRepository.save(new Versement( new Date(), 2300.0,compte2));
		  iOperationRepository.save(new Versement( new Date(), 400.0,compte2));
	      iOperationRepository.save(new Versement( new Date(), 2300.0,compte2));
	      iOperationRepository.save(new Retrait( new Date(),3000.0,compte2));
	
		 	
		 //Tester la couche Metier
		 iBanqueMetier.versement("compte1", 111111.0);
	}
	
	//Configuration de Spring security
	@Bean
	public WebSecurityConfigurerAdapter webSecurityConfig() {
        return new WebSecurityConfigurerAdapter() {
	
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// cryptage du passeword
			PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			//définir les utilisateurs de l'application , leurs password et leurs rôles
			auth.inMemoryAuthentication().withUser("admin").password(encoder.encode("admin")).roles("ADMIN","USER").and().withUser("user").password(encoder.encode("user")).roles("USER");
			
		}

		protected void configure(HttpSecurity http) throws Exception {
			http.formLogin();
			// configuration des rôles  
			http.authorizeRequests().antMatchers("/operations","/consultercompte").hasRole("USER");
			http.authorizeRequests().antMatchers("/saveOperation","/operation","/consultercompte").hasRole("ADMIN");
			}
		};
	}

}

