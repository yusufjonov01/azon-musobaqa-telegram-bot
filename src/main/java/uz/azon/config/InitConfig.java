package uz.azon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import uz.azon.service.UserTest;

import javax.swing.*;
import java.util.Properties;

@Service
public class InitConfig {
    public static boolean isStart() {

        Properties props = new Properties();
        try {
            props.load(new ClassPathResource("/application.properties").getInputStream());
            if (props.getProperty("spring.jpa.hibernate.ddl-auto").equals("update")) {
                return true;
            } else {
//                String confirm = JOptionPane.showInputDialog("Ma'lumotlarni o'chirib yuborma! Keyin bilmay qoldim dema! Agar rostdan ham o'chirmoqchi bo'lsang. O'chirish kodi (ECMADAN SURA) :");
//                if (confirm != null && confirm.equals("DUONOTARY_DELETE")) {
                return true;
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
