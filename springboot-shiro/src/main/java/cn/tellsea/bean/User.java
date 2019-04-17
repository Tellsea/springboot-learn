package cn.tellsea.bean;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_pwd")
    private String userPwd;
}
