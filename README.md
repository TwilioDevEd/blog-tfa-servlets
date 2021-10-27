<a href="https://www.twilio.com">
  <img src="https://static0.twilio.com/marketing/bundles/marketing/img/logos/wordmark-red.svg" alt="Twilio" width="250" />
</a>

# Blog TFA Post - Java/Servlets

[![Java Servlet CI](https://github.com/TwilioDevEd/blog-tfa-servlets/actions/workflows/gradle.yml/badge.svg)](https://github.com/TwilioDevEd/blog-tfa-servlets/actions/workflows/gradle.yml)

## Set up

### Requirements

1. [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
   installed in your operative system.
1. A Twilio account with a verified [phone number](https://www.twilio.com/console/phone-numbers/incoming). (Get a
   [free account](https://www.twilio.com/try-twilio?utm_campaign=tutorials&utm_medium=readme)
   here.) If you are using a Twilio Trial Account, you can learn all about it
   [here](https://www.twilio.com/help/faq/twilio-basics/how-does-twilios-free-trial-work).
1.[ngrok](https://ngrok.com)
1. [PostgreSQL](https://www.postgresql.org/)

### Twilio Account Settings

This application should give you a ready-made starting point for writing your
own appointment reminder application. Before we begin, we need to collect
all the config values we need to run the application:

| Config Value | Description                                                                                                                                                  |
| :---------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Account Sid  | Your primary Twilio account identifier - find this [in the Console](https://www.twilio.com/console).                                                         |
| Auth Token   | Used to authenticate - [just like the above, you'll find this here](https://www.twilio.com/console).                                                         |
| Phone number | A Twilio phone number in [E.164 format](https://en.wikipedia.org/wiki/E.164) - you can [get one here](https://www.twilio.com/console/phone-numbers/incoming) |

### Local development

1. First clone this repository and `cd` into it.

   ```
   git clone git@github.com:TwilioDevEd/blog-tfa-servlets.git
   cd blog-tfa-servlets
   ```

1. Copy the sample configuration file and edit it to match your configuration.
   ```bash
   cp .env.example .env
   ```
   See [Twilio Account Settings](#twilio-account-settings) to locate the necessary environment variables.

1. Make sure the all tests succeed.

   ```bash
   ./gradlew integrationTest
   ```

1. Run the application.

   ```bash
   ./gradlew appRun
   ```

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
