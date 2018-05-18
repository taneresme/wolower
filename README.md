[![Build Status](https://travis-ci.org/taneresme/swe.573.project.png?branch=master)](https://travis-ci.org/taneresme/swe.573.project)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/820e539363de414c84ce1c2694061b0a)](https://app.codacy.com/app/tnr.esme/swe.573.project/dashboard)


This project is a Twitter-based application that is analyzed, designed, 
developed and managed for the lesson SWE-573 Software Development Practice 
at Bogaziçi University.

# The wolower
It is possible to use your Twitter account to sell some of your belongings, 
but there is a significant problem when you want to sell and buy something
on Twitter, that is payment. Twitter does not provide a social payment
infrastructure. Sure you consider that there is an option to pay at the door, 
but it is not useful for international sales. You cannot expect from someone
abroad to accept the payment on behalf of you, right!. A trusted, secure,
and automated payment mechanism is needed. 
**The wolower engages right here!**

## Screenshots
### Sign in with Twitter
The wolower makes use of "Sign in with Twitter" feature of Twitter. 
This provides a really simple way to log into the wolower. 

***

![Sign in with Twitter](https://github.com/taneresme/swe.573.project/blob/master/docs.project/signin.gif)

### Masterpass Pairing
The wolower uses Masterpass to provide you a secure, trusted payment mechanism.
The wolower assures you that it will never ever know your credit card
number. To be honest, we do not care your credit card!

***

![Masterpass Pairing](https://github.com/taneresme/swe.573.project/blob/master/docs.project/masterpass-pairing.gif)

### New Product
The wolower has two different types of entries, one of them is a product. 
If you want to sell something on your Twitter account, the only thing you will
do is to mention "wolower_payment" twitter account when sharing your product 
with its price. 

***

![New Product](https://github.com/taneresme/swe.573.project/blob/master/docs.project/new-product.gif)

### New Order
The other type of entries for the wolower is an order. 
If you love something brilliant being sold on Twitter, the only thing you will do
is to reply that tweet it with your order (with how much are you willing to pay).

***

![New Order](https://github.com/taneresme/swe.573.project/blob/master/docs.project/new-order.gif)

## Project Management
MS Project is used for the purposes such as
time-tracking, project planning, resource planning etc.
The general project plan will be as follows;

![General Project Plan](https://github.com/taneresme/swe.573.project/blob/master/docs.project/wolower.project-plan-general.png)

You can access the details of the project management approach by
clicking [here](https://github.com/taneresme/swe.573.project/wiki/2.-Project-Management)

For all project plan,
[project plan](https://github.com/taneresme/swe.573.project/blob/master/docs.project/wolower.project-plan.pdf)

## Requirement Specification
A requirement specification document was also studied for the wolower.
For all document,
[requirements Specification Document](https://github.com/taneresme/swe.573.project/blob/master/docs.project/wolower.requirement-specification.pdf)

## Design Diagrams
If you want to learn more about how the wolower works,
you review its design documents,
[design diagrams](https://github.com/taneresme/swe.573.project/blob/master/docs.project/wolower.diagrams.pdf)
and
[mockups](https://github.com/taneresme/swe.573.project/blob/master/docs.project/wolower.mockups.pdf)

## Tools
* **Derby** as en embedded DBMS
* **Hibernate** as an ORM tool
* **Spring Boot** as a framework
* **Java 8** as the primary programming language
* **Maven** for building the software
* **Bootstrap 4** as a HTML template with Jquery
* **Thymeleaf** as a HTML template engine
* **Travis.CI** as a continuous integration tool
* **Codacy** as a code quality tool

## Authors
* Taner Eşme

For more details, please visit [wiki pages](https://github.com/taneresme/swe.573.project/wiki)