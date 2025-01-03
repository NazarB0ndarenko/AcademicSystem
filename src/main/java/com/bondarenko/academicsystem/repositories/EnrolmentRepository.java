package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.enteties.Enrolment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrolmentRepository extends CrudRepository<Enrolment, Long> {
}
