package be.gert.trainapp.sm;

import static be.gert.trainapp.api.personnel.generated.model.EmployeeRole.TRAIN_CONDUCTOR;
import static be.gert.trainapp.api.personnel.generated.model.EmployeeRole.TRAIN_ENGINEER;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import be.gert.trainapp.api.assets.generated.model.AddLocomotiveRequest;
import be.gert.trainapp.api.assets.generated.model.AddWagonRequest;
import be.gert.trainapp.api.network.generated.model.AddNodeRequest;
import be.gert.trainapp.api.network.generated.model.AddTrackRequest;
import be.gert.trainapp.api.network.generated.model.GeoPositionBody;
import be.gert.trainapp.api.network.generated.model.SpeedBody;
import be.gert.trainapp.api.network.generated.model.SpeedBody.MeasurementEnum;
import be.gert.trainapp.api.personnel.generated.model.AssignEmployeeRoleRequest;
import be.gert.trainapp.api.personnel.generated.model.FullNameBody;
import be.gert.trainapp.api.personnel.generated.model.NewEmployeeRequest;
import be.gert.trainapp.sm.assets.LocomotiveId;
import be.gert.trainapp.sm.assets.LocomotiveModelId;
import be.gert.trainapp.sm.assets.WagonModelId;
import be.gert.trainapp.sm.assets.locomotive.AddLocomotiveUseCase;
import be.gert.trainapp.sm.assets.wagon.AddWagonUseCase;
import be.gert.trainapp.sm.network.NodeId;
import be.gert.trainapp.sm.network.TrackGauge;
import be.gert.trainapp.sm.network.node.AddNodeUseCase;
import be.gert.trainapp.sm.network.track.AddTrackUseCase;
import be.gert.trainapp.sm.personnel.EmployeeId;
import be.gert.trainapp.sm.personnel.employee.AssignEmployeeRoleUseCase;
import be.gert.trainapp.sm.personnel.employee.NewEmployeeUseCase;
import lombok.RequiredArgsConstructor;

@Component
@Profile("localhost")
@RequiredArgsConstructor
public class LocalDataLoader {
	private final NewEmployeeUseCase newEmployeeUseCase;
	private final AssignEmployeeRoleUseCase assignEmployeeRoleUseCase;
	private final AddLocomotiveUseCase addLocomotiveUseCase;
	private final AddWagonUseCase addWagonUseCase;
	private final AddNodeUseCase addNodeUseCase;
	private final AddTrackUseCase addTrackUseCase;

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
	public static final TrackGauge standardGauge = new TrackGauge("1435mm");

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

		addLocomotiveUseCase.execute(new AddLocomotiveRequest()
				.id(locomotiveAHaroldBibby.id())
				.modelTypeId(locomotiveModelLNERB1.id())
				.name("A HAROLD BIBBY")
				.serialNumber("2024-364-0600-01")
				.gauge(standardGauge.type()));
		addLocomotiveUseCase.execute(new AddLocomotiveRequest()
				.id(locomotiveAbbotsburyCastle.id())
				.modelTypeId(locomotiveModel_GWR4073.id())
				.name("ABBOTSBURY CASTLE")
				.serialNumber("2024-364-0600-02")
				.gauge(standardGauge.type()));
		addLocomotiveUseCase.execute(new AddLocomotiveRequest()
				.id(locomotiveAbberleyHall.id())
				.modelTypeId(locomotiveModel_Gwr4900.id())
				.name("ABBERLEY HALL")
				.serialNumber("2024-364-0600-03")
				.gauge(standardGauge.type()));

		addWagons(wagonModelCoach, 5);
		addWagons(wagonModelCovered, 4);
		addWagons(wagonModelTanker, 3);
		addWagons(wagonModelBox, 2);

		networkBrusselsToAntwerp();
	}

	private void addWagons(WagonModelId modelId, int n) {
		for (int i=1; i <= n; i++) {
			addWagonUseCase.execute(new AddWagonRequest()
					.wagonId(modelId.id() + "-" + i)
					.modelTypeId(modelId.id())
					.gauge(standardGauge.type())
					.serialNumber("2024-364-0700-" + (i > 9 ? i: "0" + i)));
		}
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

	private void networkBrusselsToAntwerp() {
		addTracks(
			addNode("Brussels-Central", 235, 10806),
			addNode("Brussels-Central_Brussels-North_1", 254, 10734),
			addNode("Brussels-Central_Brussels-North_2", 297, 10689),
			addNode("Brussels-Central_Brussels-North_3", 348, 10574),
			addNode("Brussels-Central_Brussels-North_4", 299, 10488),
			addNode("Brussels-North", 317, 10401),
			addNode("Brussels-North_Schaarbeek_1", 391, 10118),
			addNode("Schaarbeek", 602, 9892),
			addNode("Schaarbeek_Buda-1", 766, 9809),
			addNode("Schaarbeek_Buda-2", 994, 9763),
			addNode("Schaarbeek_Buda-3", 1165, 9582),
			addNode("Schaarbeek_Buda-4", 1176, 9274),
			addNode("Buda", 1297, 9070),
			addNode("Vilvoorde", 1569, 8616),
			addNode("Eppegem", 2000,7668),
			addNode("Weerde", 2240,7140),
			addNode("Weerde_Mechelen_1", 2309,6724),
			addNode("Weerde_Mechelen_2", 2299,6269),
			addNode("Mechelen", 2452, 6024),
			addNode("Mechelen_Mechelen-Nekkerspoel_1", 2546, 5898),
			addNode("Mechelen-Nekkerspoel", 2571, 5669),
			addNode("Sint-Katelijne-Waver", 2662, 4555),
			addNode("Sint-Katelijne-Waver_Duffel_1", 2664, 4249),
			addNode("Duffel", 2610, 3977),
			addNode("Duffel_Kontich_1", 2497, 3287),
			addNode("Kontich", 2335, 2803),
			addNode("Hove", 2139, 2262),
			addNode("Mortsel-Deurnsesteenweg", 1815, 1450),
			addNode("Antwerp-Berchem", 1550, 982),
			addNode("Antwerp-Berchem_Antwerp-Central_1", 1379, 658),
			addNode("Antwerpen-Central", 1361, 507));
	}

	private void addTracks(NodeId... nodeIds) {
		for (int i = 1; i < nodeIds.length; i++) {
			addTrackUseCase.execute(new AddTrackRequest()
					.gauge("1435mm")
					.fromNodeId(nodeIds[i-1].id())
					.toNodeId(nodeIds[i].id())
					.slope(BigDecimal.ZERO)
					.speedLimit(new SpeedBody()
							.speed(new BigDecimal(70))
							.measurement(MeasurementEnum.KPH))
							.gauge(standardGauge.type())
					.electrified(true));
		}
	}

	//dummy data:
	//maxLongitude: 2664 >
	//minLongitude: 235   > offset -1214
	//maxLatitude: 10806 >
	//minLatitude: 507    > offset -5149
	private NodeId addNode(String name, int x, int y) {
		addNodeUseCase.execute(new AddNodeRequest()
				.id(name)
				.name(name.contains("_") ? "": name.replace("_", " "))
				.geoPosition(new GeoPositionBody()
						.longitude(new BigDecimal(x - 1214).setScale(7, HALF_UP)
								.divide(new BigDecimal(1000).setScale(7, HALF_UP), HALF_UP))
						.latitude(new BigDecimal(y-5149).setScale(7, HALF_UP)
								.divide(new BigDecimal(1000).setScale(7, HALF_UP)))));
		return new NodeId(name);
	}
}

