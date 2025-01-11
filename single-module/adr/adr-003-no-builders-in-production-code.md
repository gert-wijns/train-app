## Title
Builder and toBuilder not allowed in production code.
(everywhere, or just on model?)

## Status
Draft

## Context

### <span style="color:#4863A0;">X.builder():</span>
- confusing contract: doesn't tell which fields are mandatory at initial creation
- make it easy to forget setting certain fields which then needs to be validated somehow
- validation is possible on build but very ugly <span style="color:#4863A0;">(override Builder class and implement build method 
in which you will have to call the AllArgsConstructor manually anyway)</span>

### <span style="color:#4863A0;">x.toBuilder():</span>
- very easy to create a shallow copy and change just a small part
  - creating copies is not a good idea for hibernate entities in production code though
- useful when working with (larger) immutable objects, though in that case it should only be used as a helper within 
  methods of the immutable object
- when used from outside it leads to anemic objects and is hard to understand why and what should change together
- validation becomes problematic (must always validate everything because anything can be changed)
- useful in tests because we want to create some default objects and then make minor modifications per test

## Decision
Builders will not be used.
ToBuilders will be used only in tests data setup.

To be able to have toBuilder without builder, simply configure as
```java
@Builder(builderMethodName = "", toBuilder = true)
class SomeClass {
	// will have this.toBuilder(), but no SomeClass.builder()
}
```

## Compliance

An archunit test will validate "builder" and "toBuilder" is not used in production code.
An archunit test will validate @Builder annotations have builderMethodName = "".

## Notes
- Author: Gert Wijns
- Changelog:
    - 0.1 initial proposed version on 2025-01-12
