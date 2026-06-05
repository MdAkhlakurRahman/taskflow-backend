package com.taskflow.demo.repository;

import com.taskflow.demo.entity.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEventRepository extends JpaRepository<AuditEvent,Long> {

}
