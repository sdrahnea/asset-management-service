package com.sdr.ams.repository;

import java.time.LocalDate;
import java.util.List;

import com.sdr.ams.model.financial.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("""
        select i
        from Invoice i
        where (:invoiceNumber is null or upper(i.invoiceNumber) like concat('%', upper(:invoiceNumber), '%'))
          and (:status is null or i.status = :status)
          and (:sellerName is null or upper(i.seller.name) like concat('%', upper(:sellerName), '%'))
          and (:buyerName is null or upper(i.buyer.name) like concat('%', upper(:buyerName), '%'))
          and (:issueDateFrom is null or i.issueDate >= :issueDateFrom)
          and (:issueDateTo is null or i.issueDate <= :issueDateTo)
          and (:dueDateFrom is null or i.dueDate >= :dueDateFrom)
          and (:dueDateTo is null or i.dueDate <= :dueDateTo)
        order by i.updatedAt desc, i.invoiceNumber asc
        """)
    List<Invoice> search(
        @Param("invoiceNumber") String invoiceNumber,
        @Param("status") Invoice.InvoiceStatus status,
        @Param("sellerName") String sellerName,
        @Param("buyerName") String buyerName,
        @Param("issueDateFrom") LocalDate issueDateFrom,
        @Param("issueDateTo") LocalDate issueDateTo,
        @Param("dueDateFrom") LocalDate dueDateFrom,
        @Param("dueDateTo") LocalDate dueDateTo
    );

    @Query("""
        select case when count(i) > 0 then true else false end
        from Invoice i
        where upper(i.name) = upper(:invoiceId)
          and (:excludeId is null or i.id <> :excludeId)
        """)
    boolean existsInvoiceId(@Param("invoiceId") String invoiceId, @Param("excludeId") Long excludeId);

    @Query("""
        select case when count(i) > 0 then true else false end
        from Invoice i
        where upper(i.invoiceNumber) = upper(:invoiceNumber)
          and (:excludeId is null or i.id <> :excludeId)
        """)
    boolean existsInvoiceNumber(@Param("invoiceNumber") String invoiceNumber, @Param("excludeId") Long excludeId);
}

