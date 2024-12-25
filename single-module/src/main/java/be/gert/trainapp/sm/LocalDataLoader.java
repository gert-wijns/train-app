package be.gert.trainapp.sm;

import static be.gert.trainapp.api.personnel.generated.model.EmployeeRole.TRAIN_CONDUCTOR;
import static be.gert.trainapp.api.personnel.generated.model.EmployeeRole.TRAIN_ENGINEER;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import be.gert.trainapp.api.assets.generated.model.AddLocomotiveRequest;
import be.gert.trainapp.api.assets.generated.model.AddWagonRequest;
import be.gert.trainapp.api.personnel.generated.model.AssignEmployeeRoleRequest;
import be.gert.trainapp.api.personnel.generated.model.FullNameBody;
import be.gert.trainapp.api.personnel.generated.model.NewEmployeeRequest;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets.locomotive.AddLocomotiveUseCase;
import be.gert.trainapp.sm.assets.wagon.AddWagonUseCase;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel.employee.AssignEmployeeRoleUseCase;
import be.gert.trainapp.sm.personnel.employee.NewEmployeeUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class LocalDataLoader {
	private final NewEmployeeUseCase newEmployeeUseCase;
	private final AssignEmployeeRoleUseCase assignEmployeeRoleUseCase;
	private final AddLocomotiveUseCase addLocomotiveUseCase;
	private final AddWagonUseCase addWagonUseCase;

	public static final EmployeeId employeeJohnId = new EmployeeId("john");
	public static final EmployeeId employeeCaseyId = new EmployeeId("casey");
	public static final EmployeeId employeeChristinaId = new EmployeeId("christina");
	public static final EmployeeId employeeHannahId = new EmployeeId("hannah");

	public static final LocomotiveId locomotiveAHaroldBibby = new LocomotiveId("1250");
	public static final LocomotiveId locomotiveAbbotsburyCastle = new LocomotiveId("4083");
	public static final LocomotiveId locomotiveAbberleyHall = new LocomotiveId("4981");

	//https://www.brdatabase.info/locoqry.php?action=class&type=S&id=146005//
	public static final LocomotiveModelId locomotiveModel_Gwr4900 = new LocomotiveModelId("GWR_4900");
	//https://www.brdatabase.info/locoqry.php?action=class&type=S&id=146005//
	public static final LocomotiveModelId locomotiveModel_GWR4073 = new LocomotiveModelId("GWR_4073");
	//https://www.brdatabase.info/locoqry.php?action=class&type=S&id=600201
	public static final LocomotiveModelId locomotiveModelLNERB1 = new LocomotiveModelId("LNR_B1");

	public static final WagonModelId wagonModelCoach = new WagonModelId("Coach");
	public static final WagonModelId wagonModelCovered = new WagonModelId("Covered");
	public static final WagonModelId wagonModelTanker = new WagonModelId("Tanker");
	public static final WagonModelId wagonModelBox = new WagonModelId("Box");

	@EventListener(ApplicationStartedEvent.class)
	public void onApplicationStarted() {
		newEmployeeUseCase.execute(newEmployeeChristinaRequest());
		newEmployeeUseCase.execute(newEmployeeHannahRequest());
		newEmployeeUseCase.execute(newEmployeeCaseyRequest());
		newEmployeeUseCase.execute(newEmployeeJohnRequest());

		assignEmployeeRoleUseCase.execute(new AssignEmployeeRoleRequest()
				.employeeId(employeeChristinaId.id())
				.role(TRAIN_ENGINEER));
		assignEmployeeRoleUseCase.execute(new AssignEmployeeRoleRequest()
				.employeeId(employeeHannahId.id())
				.role(TRAIN_ENGINEER));
		assignEmployeeRoleUseCase.execute(new AssignEmployeeRoleRequest()
				.employeeId(employeeCaseyId.id())
				.role(TRAIN_CONDUCTOR));
		assignEmployeeRoleUseCase.execute(new AssignEmployeeRoleRequest()
				.employeeId(employeeJohnId.id())
				.role(TRAIN_CONDUCTOR));

		addLocomotiveUseCase.execute(addLocomotiveRequest(locomotiveAHaroldBibby, locomotiveModelLNERB1, "A HAROLD BIBBY"));
		addLocomotiveUseCase.execute(addLocomotiveRequest(locomotiveAbbotsburyCastle, locomotiveModel_GWR4073, "ABBOTSBURY CASTLE"));
		addLocomotiveUseCase.execute(addLocomotiveRequest(locomotiveAbberleyHall, locomotiveModel_Gwr4900, "ABBERLEY HALL"));

		addWagons(wagonModelCoach, 5);
		addWagons(wagonModelCovered, 4);
		addWagons(wagonModelTanker, 3);
		addWagons(wagonModelBox, 2);
	}

	private void addWagons(WagonModelId modelId, int n) {
		for (int i=1; i <= n; i++) {
			addWagonUseCase.execute(new AddWagonRequest()
					.wagonId(modelId.id() + "-" + i)
					.modelTypeId(modelId.id()));
		}
	}

	private @Valid AddLocomotiveRequest addLocomotiveRequest(LocomotiveId locomotiveId,
	                                                         LocomotiveModelId modelId,
	                                                         String name) {
		return new AddLocomotiveRequest()
				.id(locomotiveId.id())
				.modelTypeId(modelId.id())
				.name(name);
	}

	public static NewEmployeeRequest newEmployeeJohnRequest() {
		return new NewEmployeeRequest()
				.employeeId(employeeJohnId.id())
				.fullName(new FullNameBody()
						.firstName("John")
						.lastName("Armstrong"));
	}

	public static NewEmployeeRequest newEmployeeCaseyRequest() {
		return new NewEmployeeRequest()
				.employeeId(employeeCaseyId.id())
				.fullName(new FullNameBody()
						.firstName("Casey")
						.lastName("Jones"));
	}

	public static NewEmployeeRequest newEmployeeChristinaRequest() {
		return new NewEmployeeRequest()
				.employeeId(employeeChristinaId.id())
				.fullName(new FullNameBody()
						.firstName("Christina")
						.lastName("Gonzales"));
	}
	
	public static NewEmployeeRequest newEmployeeHannahRequest() {
		return new NewEmployeeRequest()
				.employeeId(employeeHannahId.id())
				.fullName(new FullNameBody()
						.firstName("Hannah")
						.lastName("Dadds"));
	}
}
