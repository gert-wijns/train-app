## Title
Package structure

## Status
Draft

## Context
Keeping the same package structure across the application helps with intuitively being able to 
find what we are looking for when we don't know the exact name of what we want to find.

The decided on package structure must be followed to avoid chaos.

There exists many ways to organize code into packages where one might be better than another 
in each particular use case, but to keep the order we will decide on one.

<span style="color:#FF7900;">!!! Packages prefixed with an underscore (example: _repository, _model, ...) are considered non-functional packages.<br>
!!! This means their semantic is not indicative of any functional concept, but rather a technical grouping.</span>

## <span style="color:#1589FF;"><u>src/main/java</u></span>
### <span style="color:#4863A0;">be.gert.trainapp.sm</span>
This is the root package and it only contains the spring boot application.

### <span style="color:#4863A0;">be.gert.trainapp.sm._shared</span>
Contains all shared configuration, base classes, utility classes and value objects which may be used by the entire application.
Subpackages contain a package-info.java to add them to the public api of the shared module. Anything in the shared module is exempt from dependency checks.

### <span style="color:#4863A0;">be.gert.trainapp.sm._localhost</span>
Contains configuration and beans which are loaded when the localhost profile is used. 
All configurations/components in this package must use @Profile("localhost") to ensure they are not loaded otherwise.

! This is validated by an archunit test.

### <span style="color:#4863A0;">be.gert.trainapp.sm.xxxxxxxxxx</span>
None underscore packages under the root are bounded contexts and dependencies across modules are only allowed 
when they are part of the public API of the module. 
Check out the standard modulith documentation how it defines what is part of the public API. 

In a nutshell, the public API consists of any class directly under xxxxxx, and classes in packages with a package-info.class having a @NamedInterface.

! The module dependencies are validated by a test (SpringModulithTests).

### <span style="color:#4863A0;">be.gert.trainapp.sm.xxxxxxxxxx._model</span>
This package contains all domain objects. These may be JPA entities or other value objects which participate in 
the execution of behaviour within the domain.

### <span style="color:#4863A0;">be.gert.trainapp.sm.xxxxxxxxxx._repository</span>
This package contains all jpa repositories used to fetch the JPA entities specified in _model.

### <span style="color:#4863A0;">be.gert.trainapp.sm.xxxxxxxxxx._events</span>
This package contains all domain events. This package will contain a package-info.java to expose the events as part of the API.
Though only events having a @PublicEvent will be allowed to be used elsewhere. 

! The @PublicEvent rule is validated using in an ArchUnit test.

### <span style="color:#4863A0;">be.gert.trainapp.sm.xxxxxxxxxx._adapter</span>
This package contains adapters which will pull in data from other modules or external sources and push data when applicable as well.
The adapters exist to protect the internal parts of the domain from external pollution.

### <span style="color:#4863A0;">be.gert.trainapp.sm.xxxxxxxxxx._port</span>
This package contains the interfaces for the adapters so that we may replace them with testdoubles during testing.

### <span style="color:#4863A0;">be.gert.trainapp.sm.xxxxxxxxxx._mapper</span>
This package contains mappers which can be shared in the domain. Consider adding a mapper when multiple pieces of code
require the same mapping. Avoid putting mappers for mappings which are only used in a single place.

### <span style="color:#4863A0;">be.gert.trainapp.sm.xxxxxxxxxx.yyyyyyyyyy</span>
These packages contain the use cases and queries related to the domain. Most will implement a RestAPI.
Others are listening to events via @ApplicationModuleListener, usually to react on events from other domains.

## <span style="color:#1589FF;"><u>src/main/test</u></span>

### <span style="color:#4863A0;">be.gert.trainapp.sm.xxxxxxxxxx._model</span>
In tests, the model package contains some reusable defaults for each domain object.
The tests should generally start from the defaults and adapt them as they need.

### <span style="color:#4863A0;">be.gert.trainapp.sm.xxxxxxxxxx._testdoubles</span>
This package contains Fake implementations for each external API used by the module (usually in the adapter classes).
These fake implementation return some default values (preferable based on the Defaults defined in model).
These fakes are included in the application as spy beans so that when necessary, they can have a specific
behavior in a specific test.

### <span style="color:#4863A0;">be.gert.trainapp.sm.xxxxxxxxxx.yyyyyyyyyy</span>
These packages contain the use case and query tests. 
Because the module test loads all module beans including adapters, specific adapter tests should in general not be necessary.
Their purpose is to support the use cases, so when use cases have sufficient test coverage, the adapters are tested implicitly.

## Decision

We will use the aforementioned package structure in this project.

## Compliance

### ArchUnit 
- Check spring data repositories are only defined in _repository
- Check classes extending JpaEntity are only defined in _model
- Check components in _localhost have the @Profile("localhost")
- Check classes defined in _event are only used in other modules if they have @PublicEvent

### Modulith 
Check module internal code is not used by other modules.

## Notes
- Author: Gert Wijns
- Changelog:
    - 0.1 initial proposed version on 2025-01-10
