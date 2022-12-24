package com.laptrinhjava.employee.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = Employee.ENTITY_NAME)
@ToString
@Table(name = Employee.TABLE_NAME)
public class Employee {
    public static final String ENTITY_NAME = "Employee";
    public static final String TABLE_NAME = "employee";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_FIRSTNAME_NAME = "firstName";
    public static final String COLUMN_LASTNAME_NAME = "lastName";
    public static final String COLUMN_SEX_NAME = "sex";
    public static final String COLUMN_ADDRESS_NAME = "address";
    public static final String COLUMN_NUMBERPHONE_NAME = "numberphone";
    public static final String COLUMN_EMAIL_NAME = "email";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Integer id;

    @NotEmpty(message = "Tên không thể trống")
    @Size(min = 2, max = 20, message = "Độ dài tên từ [2 - 20] ký tự")
    @Column(name = COLUMN_FIRSTNAME_NAME)
    private String firstname;

    @NotEmpty(message = "Họ không thể trống")
    @Size(min = 2, max = 30, message = "Độ dài họ từ [2 - 30] ký tự")
    @Column(name = COLUMN_LASTNAME_NAME)
    private String lastname;

    @NotEmpty(message = "Giới tính không thể trống")
    @Column(name = COLUMN_SEX_NAME)
    private String sex;

    @NotEmpty(message = "Địa chỉ không thể trống")
    @Column(name = COLUMN_ADDRESS_NAME)
    private String address;

    @NotEmpty(message = "Số điện thoại không thể trống")
    @Size(min = 6, max = 12, message = "Độ dài số điện thoại từ [6 - 12] ký tự")
    @Column(name = COLUMN_NUMBERPHONE_NAME)
    private String numberphone;

    @NotEmpty(message = "Email không thể trống")
    @Size(min = 4, max = 30, message = "Độ dài email từ [4 - 30] ký tự")
    @Email(message = "Email không hợp lệ")
    @Column(name = COLUMN_EMAIL_NAME)
    private String email;
}
