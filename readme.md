# Train App

Experiment with architecture to attempt to find the sweet spot of
not doing too much upfront but still keeping our options open so
we can grow the application as complexity and needs grow.

## api-train-app 

Contains the API exposed by the application.
Currently implemented as openapi-specification which is used to generate code.

## multi-module (mm-train-app)

A multi-module approach, strictly separating parts of the application using maven modules.
(though at this point empty, focused on single-module)

## single-module (sm-train-app)

A single-module approach, separating parts using ArchUnit where needed.

See ADRs in single-module/adr.

## train-front

A frontend using svelte/svelte-kit because it's fun :)
