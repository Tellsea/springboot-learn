package cn.tellsea.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

    private Date ctime;
}
