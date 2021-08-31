package fatec.api.Sirius;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import fatec.api.Sirius.model.User;
import fatec.api.Sirius.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
    	

        if( userRepository.findByUsername("admin") == null ){
        User user = new User("admin@code.com", passwordEncoder.encode("password"),"Admin", "Super", true, "admin" );
        userRepository.save(user);
        }
        
        if( userRepository.findByUsername("user") == null){
        User user = new User("user@code.com", passwordEncoder.encode("password"),"User", "Super", true, "user" );
        userRepository.save(user);
        }
    }
}
