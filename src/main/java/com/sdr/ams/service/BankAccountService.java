package com.sdr.ams.service;

import java.util.List;

import com.sdr.ams.model.financial.BankAccount;
import com.sdr.ams.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional(readOnly = true)
    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public BankAccount findById(Long id) {
        return bankAccountRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("BankAccount not found for id: " + id));
    }

    public BankAccount create(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount update(Long id, BankAccount input) {
        BankAccount existing = findById(id);
        existing.setName(input.getName());
        existing.setUpdatedBy(input.getUpdatedBy());
        return bankAccountRepository.save(existing);
    }

    public void delete(Long id) {
        bankAccountRepository.deleteById(id);
    }
}

