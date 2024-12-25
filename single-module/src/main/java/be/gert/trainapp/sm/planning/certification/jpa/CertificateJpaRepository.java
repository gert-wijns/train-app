package be.gert.trainapp.sm.planning.certification.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm.planning.certification.CertificateCode;
import be.gert.trainapp.sm.planning.certification.model.Certificate;

@Repository
public interface CertificateJpaRepository extends CrudRepository<Certificate, CertificateCode> {
}
