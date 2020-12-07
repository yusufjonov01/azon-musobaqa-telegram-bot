package uz.azon.entity;


import uz.azon.entity.enums.Status;
import uz.azon.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ishtirokchilar")
@Data
public class User extends AbsEntity {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private int age;
    @Column
    Long chatId;
    @Column
    private String city;

    private boolean read;

    private String phoneNumber;
    private String edu;

    @Enumerated(EnumType.STRING)
    private Status status;

    public User(Long chatId, Status status) {
        this.chatId = chatId;
        this.status = status;
    }
}