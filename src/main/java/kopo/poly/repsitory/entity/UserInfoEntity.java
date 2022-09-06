package kopo.poly.repsitory.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
public class UserInfoEntity {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @NonNull
    @Column(name = "USER_NAME", length = 500, nullable = false)
    private String userName;

    @NonNull
    @Column(name = "PASSWORD", length = 1, nullable = false)
    private String password;

    @NonNull
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @NonNull
    @Column(name = "ADDR1", nullable = false)
    private String addr1;

    @Column(name = "ADDR2", nullable = false)
    private String addr2;

    @Column(name = "REG_ID", updatable = false)
    private String regId;

    @Column(name = "REG_DT", updatable = false)
    private String regDt;

    @Column(name = "CHG_ID")
    private String chgId;

    @Column(name = "CHG_DT")
    private String chgDt;

}