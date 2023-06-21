package local.ln.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "links")
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 10, nullable = false)
    private String suffix;

    @Column(nullable = false, name = "redirect_url")
    private String redirectUrl;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "disable_date")
    private LocalDateTime disableDate;

    @Column(nullable = false, name = "delete_date")
    private LocalDateTime deleteDate;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserEntity user;


    public LinkEntity() {
    }

    public LinkEntity(String suffix, String redirectUrl, UserEntity user) {
        this.suffix = suffix;
        this.redirectUrl = redirectUrl;
        this.createdAt = LocalDateTime.now();
        this.disableDate = this.createdAt.plusDays(7);
        this.deleteDate = this.createdAt.plusDays(30);
        this.user = user;
    }

    public LinkEntity(String suffix, String redirectUrl, UserEntity user, LocalDateTime disabledDate) {
        this.suffix = suffix;
        this.redirectUrl = redirectUrl;
        this.createdAt = LocalDateTime.now();
        this.disableDate = disabledDate;
        this.deleteDate = disabledDate.plusDays(30);
        this.user = user;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public UserEntity getUser() {
        return user;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }
}
