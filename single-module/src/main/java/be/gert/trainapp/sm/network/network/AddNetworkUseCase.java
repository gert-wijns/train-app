package be.gert.trainapp.sm.network.network;

import static be.gert.trainapp.sm._shared.message.TranslatableMessage.error;
import static be.gert.trainapp.sm.network._model.Network.newNetwork;
import static org.springframework.http.ResponseEntity.noContent;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import be.gert.trainapp.api.network.generated.AddNetworkUseCaseApi;
import be.gert.trainapp.api.network.generated.model.AddNetworkRequest;
import be.gert.trainapp.sm._shared.exception.DomainException;
import be.gert.trainapp.sm.network.NetworkId;
import be.gert.trainapp.sm.network._repository.NetworkJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@RestController
public class AddNetworkUseCase implements AddNetworkUseCaseApi {
	private final NetworkJpaRepository jpa;

	public static DomainException alreadyExists(NetworkId id) {
		return error("NETWORK_NETWORK_ALREADY_EXISTS",
				"Network already exists for id '${id}'.")
				.withParam("id", id.id())
				.asException();
	}
	@Override
	@Transactional
	public ResponseEntity<Void> execute(AddNetworkRequest request) {
		NetworkId networkId = new NetworkId(request.getId());
		if (jpa.existsById(networkId)) {
			throw alreadyExists(networkId);
		}
		jpa.save(newNetwork(networkId, request.getName()));
		return noContent().build();
	}
}
