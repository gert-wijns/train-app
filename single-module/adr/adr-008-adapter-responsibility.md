## Title
Adapter responsibility

## Status
Draft

## Context
Pulls in data from other modules or external sources, mapping them to our domain and protecting the internal code
from external changes.

Pushes data to other modules or external sources, hiding their api from our internal code.

### <span style="color:#4863A0;">Responsibilities</span>
- interact with external APIs, hiding their implementation details from the domain

### <span style="color:#4863A0;">Constraints</span>
- Adapters must not change any data directly
- Adapters are never RestControllers.

## Decision
Adapters will be used as an anti corruption layer to protect the domain from external changes.

## Compliance
Code reviews.

## Notes
- Author: Gert Wijns
- Changelog:
    - 0.1 initial proposed version on 2025-01-12
