package kopo.poly.repsitory.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NOTICE")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="noticeSeq")
    private Long noticeSeq;

    @NonNull
    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @NonNull
    @Column(name = "notice_yn", length = 1, nullable = false)
    private String noticeYn;

    @NonNull
    @Column(name = "contents", nullable = false)
    private String contents;

    @NonNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NonNull
    @Column(name = "read_cnt", nullable = false)
    private Long readCnt;

    @Column(name = "reg_Id", updatable = false)
    private String regId;

    @Column(name = "reg_dt", updatable = false)
    private String regDt;

    @Column(name = "chg_id")
    private String chgId;

    @Column(name = "chqDt")
    private String chgDt;









}
