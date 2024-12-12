Practical FP in Scala
---
by Gabriel Volpe

Book: [Leanpub](https://leanpub.com/pfp-scala).
Code: [Examples](https://github.com/gvolpe/pfps-examples)
and [Shopping Cart](https://github.com/gvolpe/pfps-shopping-cart)

Table of contents
<br/>[Chapter 01: Best Practices](#chapter-01-best-practices)
<br/>[Chapter 02: Tagless Final](#chapter-02-tagless-final)
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
