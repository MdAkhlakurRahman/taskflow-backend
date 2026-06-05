package com.taskflow.demo.entity;

import com.taskflow.demo.enums.ActionTaken;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class AuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActionTaken actionTaken;

    private Long userId;
    private LocalDateTime localDateTime;

    public AuditEvent(ActionTaken actionTaken, Long userId, LocalDateTime localDateTime) {
        this.actionTaken = actionTaken;
        this.userId = userId;
        this.localDateTime = localDateTime;
    }

    public AuditEvent() {
    }
}
