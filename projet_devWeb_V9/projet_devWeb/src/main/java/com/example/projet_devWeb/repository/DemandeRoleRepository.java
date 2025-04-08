package com.example.projet_devWeb.repository;

import com.example.projet_devWeb.model.DemandeRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRoleRepository extends JpaRepository<DemandeRole, Long> {
    List<DemandeRole> findByTraiteeFalse();
}
