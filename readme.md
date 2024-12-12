Practical FP in Scala
---
by Gabriel Volpe

Book: [Leanpub](https://leanpub.com/pfp-scala).
Code: [Examples](https://github.com/gvolpe/pfps-examples)
and [Shopping Cart](https://github.com/gvolpe/pfps-shopping-cart)

Table of contents
<br/>[Chapter 01: Best Practices](#chapter-01-best-practices)
<br/>[Chapter 02: Tagless Final](#chapter-02-tagless-final)
<br/>[Chapter 03: Technical Stack](#chapter-03-technical-stack)
<b4/>

### Chapter 01: Best Practices

Anti-Patters

* Thou shalt not use 'Seq' in your interface.
* Thou shalt not use 'Monad Transformers' in your interface.
* Use `NoStackTrace` instead of `Exception` for custom error ADTs.
* hou shalt not use the 'ifM' extension method.

### Chapter 02: Tagless Final

* Tagless algebras should not have typeclass constraints

> If you find yourself needing to add a typeclass constraint, such as Monad, to
> your algebra, what you probably need is a program. The reason being that
> typeclass constraints define capabilities, which belong in programs and
> interpreters. Algebras should remain completely abstract.

* Programs can make use of algebras and other programs

> Tagless final is all about algebras and interpreters. Yet, something is >
> missing when it comes to writing applications:
> we need to use these algebras > to describe business logic, and this logic
> belongs in what I like to call > programs.

#### Pure business logic

* Combine pure computations in terms of tagless algebras and programs.
* Perform logging (or console stuﬀ) only via a tagless algebra.

### Chapter 03: Technical Stack

* **cats:** basic functional blocks. From typeclasses such as Functor to syntax
  and instances for some datatypes and monad transformers.
* **cats-effect:** concurrency and functional eﬀects. It ships the default IO
  monad.
* **cats-retry:** retrying actions that can fail in a purely functional fashion.
* **circe:** standard JSON library to create encoders and decoders.
* **ciris:** flexible configuration library with support for diﬀerent
  environments.
* **derevo:** typeclass derivation via macro-annotations.
* **fs2:** powerful streaming in constant memory and control flow.
* **http4s:** purely functional HTTP server and client, built on top of fs2.
* **http4s-jwt-auth:** opinionated JWT authentication built on top of jwt-scala.
* **log4cats:** standard logging framework for Cats.
* **monocle:** access and transform immutable data with optics.
* **redis4cats:** client for Redis compatible with cats-effect.
* **refined:** refinement types for type-level validation.
* **scalacheck:** property-based test framework for Scala.
* **scala-newtype:** zero-cost wrappers for strongly typed functions.
* **skunk:** purely functional, non-blocking PostgreSQL client.
* **squants:** strongly-typed units of measure such as “money”.
* **weaver:** a test framework with native support for eﬀect types.
