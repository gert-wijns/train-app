## Title
Query responsibility

## Status
Draft

## Context
A query is an entry point into a module with as purpose to expose data externally. (readonly)

### <span style="color:#4863A0;">Responsibilities</span>
- lookup (combined) data

### <span style="color:#4863A0;">Constraints</span>
- A query must never change data.
- A query must not reuse other queries in the same module (since we may simple join data)
- A query might reuse a query from another module (when performance becomes a concern
we should consider creating a read copy in our module for faster querying)

### <span style="color:#4863A0;">Query as a RestController</span>
Since a query is meant to expose data externally and must not be reused within the same module,
it is good to implement as a rest controller. This reduces the mapping load by removing the need 
for an additional controller layer.

<span style="color:#FF7900;">!!! A query is meant to expose data for external lookups. 
It is not meant for internal lookups. Internally, use the Jpa repositories.</span>

## Decision

A query
- must never change data
- can implement a rest API
- must not reuse other queries in the same module
- may reuse queries of another module (though consider performance)

## Compliance
Constraints will be checked by reviews.

## Notes
- Author: Gert Wijns
- Changelog:
    - 0.1 initial proposed version on 2025-01-12
