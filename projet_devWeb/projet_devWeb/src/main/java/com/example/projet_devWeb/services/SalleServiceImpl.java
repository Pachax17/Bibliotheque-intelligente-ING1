package com.example.projet_devWeb.services;

import com.example.projet_devWeb.model.Salle;
import com.example.projet_devWeb.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalleServiceImpl implements SalleService {

    @Autowired
    private SalleRepository salleRepository;

    @Override
    public List<Salle> findAll() {
        return salleRepository.findAll();
    }

    @Override
    public List<String> getAllNomsDeSalles() {
        return salleRepository.findAllNomSalles();
    }
}
