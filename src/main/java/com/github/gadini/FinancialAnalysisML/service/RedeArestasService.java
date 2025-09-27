package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.repository.RedeArestasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
public class RedeArestasService {

    @Autowired
    private RedeArestasRepository redeArestasRepository;

    @Transactional
    public void criarRedeArestas(LocalDate inicio, LocalDate fim){
        redeArestasRepository.spCriarRedeArestas(inicio, fim);
    }
}
