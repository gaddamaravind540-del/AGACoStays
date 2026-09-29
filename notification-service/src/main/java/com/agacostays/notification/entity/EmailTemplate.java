package com.agacostays.notification.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="email_templates", uniqueConstraints=@UniqueConstraint(name="uk_email_template_code", columnNames="template_code"))
public class EmailTemplate {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="template_id") private Long templateId;
    @Column(name="template_code", nullable=false, length=100) private String templateCode;
    @Column(nullable=false, length=200) private String subject;
    @Column(nullable=false, columnDefinition="text") private String body;
    @Column(nullable=false) private boolean active;
    @Column(name="created_at", nullable=false) private LocalDateTime createdAt;
    @Column(name="updated_at", nullable=false) private LocalDateTime updatedAt;
    @PrePersist void prePersist(){var now=LocalDateTime.now(); createdAt=now; updatedAt=now;}
    @PreUpdate void preUpdate(){updatedAt=LocalDateTime.now();}
    public Long getTemplateId(){return templateId;} public void setTemplateId(Long v){templateId=v;}
    public String getTemplateCode(){return templateCode;} public void setTemplateCode(String v){templateCode=v;}
    public String getSubject(){return subject;} public void setSubject(String v){subject=v;}
    public String getBody(){return body;} public void setBody(String v){body=v;}
    public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
}
