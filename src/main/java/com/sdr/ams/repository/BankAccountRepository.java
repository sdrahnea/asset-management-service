package com.sdr.ams.repository;
import com.sdr.ams.model.financial.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Query("""
        select case when count(b) > 0 then true else false end
        from BankAccount b
        where lower(b.iban) = lower(:iban)
          and (:excludeId is null or b.id <> :excludeId)
        """)
    boolean existsIban(@Param("iban") String iban, @Param("excludeId") Long excludeId);
    @Query("""
        select case when count(b) > 0 then true else false end
        from BankAccount b
        where lower(b.localAccountNumber) = lower(:localAccountNumber)
          and lower(b.bankName) = lower(:bankName)
          and lower(coalesce(b.branchName, '')) = lower(coalesce(:branchName, ''))
          and (:excludeId is null or b.id <> :excludeId)
        """)
    boolean existsLocalAccountForBankAndBranch(
        @Param("localAccountNumber") String localAccountNumber,
        @Param("bankName") String bankName,
        @Param("branchName") String branchName,
        @Param("excludeId") Long excludeId
    );
}
