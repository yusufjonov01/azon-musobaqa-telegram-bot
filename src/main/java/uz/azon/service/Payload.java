package uz.azon.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payload {
    private String tr;
    private String firstName;
    private String lastName;
    private String city;
    private String phoneNumber;
    private String age;
    private Timestamp date;
    private String edu;
    private boolean read;
}
