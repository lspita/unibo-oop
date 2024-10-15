# Design with abstract classes

Create from scratch an `abstract class AbstractBankAccount implements BankAccount`:

* It must contain all the elements common to all the implementations of `BankAccount`
* All fields **must** be `private`
* The class must expose as `public` all the `BankAccount`'s methods, and only those
* The class must define a `protected abstract` method `boolean isWithDrawAllowed(double)`, which returns `true` if it's possible to draw from the bank account the amount passed as parameter.
* The class must define a `protected abstract` method `double computeFee()`, which returns the current amount of the management fees (does not modify the state of `AbstractBankAccount`).

Create from scratch a class `ClassicBankAccount extends AbstractBankAccount`, with the same behavior of `SimpleBankAccount`.

Create from scratch a class `RestrictedBankAccount extends AbstractBankAccount`, with the same behavior of `StrictBankAccount`.

Answer the following question:

Is this design in your opinion better or worse than the previous one? Why?
