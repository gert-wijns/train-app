## Title
Always use temporal with app clock

## Status
Draft

## Context
Temporal classes have a "now()" method to initialize at the current time. 
The problematic part about using the <span style="color:#4863A0;">"now()"</span> method is it <span style="color:#4863A0;">uses the system default clock</span>.
This makes testing code which uses temporals more difficult. Increasingly so when
we want to create static defaults.

To make unit testing easier, we have an AppClock.clock which is initially set up with a fixed clock.
This means all static initializers in tests will (when using AppClock.clock) be fixed points in time.

By using this approach:
- tests need no mock a clock. 
- no need to inject a "time factory" in all places of the code. 

Everything just works using the default statically available methods on Temporal,
as long as we use them with the AppClock.clock.

The application itself must not run with a fixed clock of course.
For this reason, the AppClock.clock is set in the TrainAppApplication main mehtod before
SpringApplication.run. Any beans which will keep a time on startup (unless they would do so statically)
will see the 'real' clock by the time they are initialized.

## Decision
Use AppClock.clock to have a consistent time value during tests.
TrainAppApplication sets the clock before SpringApplication.run.

## Compliance
An ArchUnit test validates Temporal now() is never used without a clock.

## Notes
- Author: Gert Wijns
- Changelog:
    - 0.1 initial proposed version on 2025-01-12
