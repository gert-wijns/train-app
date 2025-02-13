## Title
ModuleTest domains in isolation

## Status
Draft

## Context
Each domain has their own model, usecases and queries.

The integration with other modules and external parties is implemented in adapters, 
or by reacting to @PublicEvent events.

In order to test our domain core is behaving as expected in isolation with the least impact due to external changes, 
we will mock/fake adapters which are our integration points with external APIs.

<span style="color:#4863A0;">!!! ModuleTests are mini integration tests within the boundary of a module.
This means all non adapter beans are loaded as well as an h2-database.
Adapters are replaced by test doubles.</span>

## Decision

- @ModuleCoreTest is introduced to configure the spring context to run usecase and queries tests.
- @ModuleCoreTest will exclude classes defined in ._adapter.
- @ModuleCoreTest will run with an H2 db.
- UesCaseTests and QueryTests must use @ModuleCoreTest
- Adapters must be tested separately

## Compliance

## Notes
- Author: Gert Wijns
- Changelog:
    - 0.1 initial proposed version on 2025-01-12
