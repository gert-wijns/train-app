package be.gert.trainapp.sm.planning._repository;

import static be.gert.trainapp.sm._shared.exception.DomainException.DomainExceptionType.NOT_FOUND;
import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm._shared.message.TranslatableMessage.KeyParam;
import be.gert.trainapp.sm.planning._model.Certificate;
import be.gert.trainapp.sm.planning._model.CertificateCode;

@Repository
public interface CertificateJpaRepository extends CrudRepository<Certificate, CertificateCode> {
	static DomainException notFound(CertificateCode code) {
		return error("NOT_FOUND", "${entity} not found id '${id}'.")
				.withParam("entity", KeyParam.key("CERTIFICATE"))
				.withParam("id", code.code())
				.asException(NOT_FOUND);
	}

	default Certificate getById(CertificateCode id) {
		return findById(id).orElseThrow(() -> notFound(id));
	}
}
