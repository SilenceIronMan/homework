package com.ysy.homework.kill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @anthor silenceYin
 * @date 2022/4/30 - 17:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private Integer uid;
    private String uname;
    private String upwd;
}
