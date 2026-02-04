package com.youtube.central.repository;

import com.youtube.central.models.Channel;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChannelRepo extends JpaRepository<Channel, UUID> {

}