package be.gert.trainapp.sm.network.network;

import static be.gert.trainapp.sm.network._model.NetworkDefaults.networkBelgium;
import static be.gert.trainapp.sm.network._model.NetworkDefaults.networkBelgiumId;
import static be.gert.trainapp.sm.network.network.AddNetworkUseCase.alreadyExists;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.gert.trainapp.api.network.generated.model.AddNetworkRequest;
import be.gert.trainapp.sm.ModuleCoreTest;
import be.gert.trainapp.sm.network._repository.NetworkJpaRepository;

@ModuleCoreTest
class AddNetworkUseCaseTest {
	@Autowired
	NetworkJpaRepository jpa;
	@Autowired
	AddNetworkUseCase usecase;

	AddNetworkRequest request = new AddNetworkRequest()
			.id(networkBelgiumId.id())
			.name(networkBelgium().name());

	@Test
	void success() {
		// when
		usecase.execute(request);

		// then
		assertThat(jpa.getById(networkBelgiumId))
				.isEqualTo(networkBelgium());
	}

	@Test
	void throwsAlreadyExists() {
		// given
		jpa.save(networkBelgium());

		// when
		assertThatThrownBy(() -> usecase.execute(request))
				.isEqualTo(alreadyExists(networkBelgiumId));
	}

}
