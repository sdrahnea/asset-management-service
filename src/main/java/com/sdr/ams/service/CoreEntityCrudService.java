package com.sdr.ams.service;

import java.util.List;

import com.sdr.ams.model.core.CoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class CoreEntityCrudService<T extends CoreEntity> {

    private final JpaRepository<T, Long> repository;
    private final String entityName;

    protected CoreEntityCrudService(JpaRepository<T, Long> repository, String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public T findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(entityName + " not found for id: " + id));
    }

    public T create(T entity) {
        return repository.save(entity);
    }

    public T update(Long id, T input) {
        T existing = findById(id);
        existing.setName(input.getName());
        existing.setUpdatedBy(input.getUpdatedBy());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

