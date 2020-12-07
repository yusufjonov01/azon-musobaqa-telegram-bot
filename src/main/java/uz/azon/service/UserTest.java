package uz.azon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.azon.entity.User;
import uz.azon.entity.enums.Status;
import uz.azon.repository.UserRepository;

@Service
public class UserTest {
    @Autowired
    UserRepository userRepository;

    public void saveUser() {
        for (int i = 0; i < 5000; i++) {
            userRepository.save(new User("fsdlom", "sadf", 20, 708154659L, "shahar", true, "34567213456", "12345453", Status.FINISHED));
        }
    }
}
