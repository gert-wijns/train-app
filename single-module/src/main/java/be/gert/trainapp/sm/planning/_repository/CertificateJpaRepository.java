package be.gert.trainapp.sm.planning._repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.planning._model.CertificateCode;
import be.gert.trainapp.sm.planning._model.Certificate;

@Repository
public interface CertificateJpaRepository extends CrudRepository<Certificate, CertificateCode> {
}
