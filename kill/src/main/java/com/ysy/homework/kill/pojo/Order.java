package com.ysy.homework.kill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @anthor silenceYin
 * @date 2022/4/30 - 17:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Order {
    private Integer id;
    private Integer sid;
    private String name;
    private Date createDate;
}
