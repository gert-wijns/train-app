package be.gert.trainapp.sm.planning._repository;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.planning._model.Certificate;
import be.gert.trainapp.sm.planning._model.CertificateCode;

@Repository
public interface CertificateJpaRepository extends CrudRepository<Certificate, CertificateCode> {
	static DomainException notFound(CertificateCode code) {
		return error("PLANNING_CERTIFICATE_CODE_NOT_FOUND",
				"CertificateCode not found code '${code}'.")
				.withParam("code", code.code())
				.asException();
	}

	default Certificate getById(CertificateCode id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
