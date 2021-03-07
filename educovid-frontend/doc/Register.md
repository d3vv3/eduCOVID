# Register component


## What is it for

This page is meant to allow COVID center responsible to register their center
in our service, provided a:
* Center name
* COVID center responsible's ID
* Password
* GDPR acceptance


## How has it been solved

We implemented a form that provides the fields described above.
We have also added form validation, so every field must validate the following:
* Center name must be longer than 3 caracters.
* COVID center responsible's ID must be a valid ID number.
* Password must be greater than weak.
* GDPR must be accepted.
* None of the fields above can be empty.


## Disclaimers

None.

## References

Tree of the folders related to this documentation.

```
src/pages
├── MainPage.js
└── Register.js
```
