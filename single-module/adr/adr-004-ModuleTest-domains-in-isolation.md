## Title
ModuleTest domains in isolation

## Status
Draft

## Context
Each domain has their own model, usecases and queries.

The integration with other modules and external parties is implemented in adapters, 
or by reacting to @PublicEvent events.

In order to test our domain is behaving as expected in isolation, we do not want to depend on the
other domain(s) in our test and must be able to provide mock/fake data for those parts which
would normally be provided by another domain.

We want to test in isolation mainly to stay decoupled and keep our options open 
to extract other domains to another app.

<span style="color:#4863A0;">!!! ModuleTests are mini integration tests within the boundary of a module.
This means all beans are loaded as well as an h2-database. 
Only external concerns are mocked/faked.</span>

## Decision

UseCase and Query tests must all use the @ModuleTest annotation.
Using option 2 in Compliance, it has all necessary configuration to load one application context
which takes into account our modular setup.

## Compliance

To separate beans per module:

Option 1
- We could enforce this by creating base/component scan per module, but this would increase test times
because spring would have to start multiple contexts (one for each module)

Option 2
- Because of our package structure, we can 'easily' identify which module a class belongs to.
This allows us to run a test in isolation by rejecting bean autowiring from other modules.
When this is done, it becomes easy to identify which testdoubles are still missing because the
application context will not start unless the setup is correct and complete.

To illustrate option 2, imagine:
```text
 /assets <- module
   SearchLocomotivesQueryApi <- 'api' interface
   /locomotive
     SearchLocomotivesQuery, implements SearchLocomotivesQueryApi
 /planning <- module
   /_adapter
      SearchLocomotive, depends on SearchLocomotivesQueryApi
   /_testdoubles
      SearchLocomotivesQueryApiFake, implements SearchLocomotivesQueryApi
```
When running ModuleTest, the SearchLocomotive will get SearchLocomotivesQueryApiFake 
because it is in the same module (planning), <span style="color:#4863A0;">even though the spring application context
contains 2 beans for SearchLocomotivesQueryApi (searchLocomotivesQueryApiFake and searchLocomotivesQuery)
and neither is @Primary.</span>

If SearchLocomotivesQueryApiFake didn't exist, the test application context would
fail to start because <span style="color:#4863A0;">searchLocomotivesQuery</span> is rejected as it is from a different module.

## Notes
- Author: Gert Wijns
- Changelog:
    - 0.1 initial proposed version on 2025-01-12
