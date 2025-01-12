## Title
Events best practices

## Status
Draft

## Context
Events are a way to achieve more loosely coupled systems.

### <span style="color:#4863A0;">Same Module - Separate Transaction - Asynchronously</span>
When possible, processing events in a new transaction asynchronously is excellent.
It will allow the original request to finish and in case of errors during event processing, 
the error will only affect that particular flow.

### <span style="color:#4863A0;">Cross Module - Separate Transaction - Asynchronously</span>
Same benefits as [Same Module - Separate Transaction - Asynchronously]

### <span style="color:#4863A0;">External - Separate Transaction - Asynchronously</span>
External event publication for asynchronous processing in another transaction is excellent.
In addition to the benefits of (Same|Cross-Module (new Tx + async)).
It increases flexibility because it allows other systems to react on the event in 
new ways without our system needing to be changed to facilitate that new behaviour.

### <span style="color:#4863A0;">Same-Module, same Transaction, <u>Synchronous</u></span>
This is mostly not a good use of events, because it doesn't provide the benefits but
still makes the code more difficult to follow.

### <span style="color:#4863A0;">Same-Module, Separate Transaction, <u>Synchronous</u></span>
This is mostly not a good use of events, because in addition to not providing the benefits,
the original process will hang until the event is handled.

<span style="color:#FF7900;">!!! Use mostly @ApplicationModuleListener, this is a modulith annotation which runs
asynchronously in a separate transaction and persists the events to allow some recovery after errors.</span>

In regard to Modulith: Although we don't use it yet, it also allows externalizing events
with some with some common adapters like kafka, rabit etc. This may be something to look into
when the time comes.

## Decision
- Use mostly @ApplicationModuleListener for async handling in a new transaction.
- Use direct procedural style when immediate synchronous update is required.

## Compliance
Code reviews.

## Notes
- Author: Gert Wijns
- Changelog:
    - 0.1 initial proposed version on 2025-01-12
