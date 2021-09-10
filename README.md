# thoughtworks-vodqa-contract-testing
This reposiroty consists of code used for demo in talk give at "Thoughtworks vodqa, Pune" for contract testing on 01-Aug-2020


## Setup instructions:

### 1. Setting up pack broker:
1. Clone this repository.
2. Start docker in the machine.
Install docker in your machine from [here](https://www.docker.com/products/docker-desktop) if you dont have it.
3. Navigate to the root folder level of the cloned repository and bring up the pact broker in docker using below command.

      `docker-compose up`
      
      P.S: If above command did not work, try running `docker-compose pull` and then `docker-compose up`
      
      The docker compose file pulls the pact-broker image and also spawns up the prostgres
      DB which is used by pact-broker to store data. Dive into the compose file for 
      more understanding.

4. If every thing goes well, you should be able to access the pact broker UI at http://localhost:9292

----
### 2. Setting up the consumers and provider in IDE:

1. We have two consumers and one provider for the setup. (There are actually three different projects. I have pushed then into a single repo for ease of cloning.)
2. Import the `consumer_one`, `consumer_two` and `provider_one` into three different instances of your IDE respectively.
---
### 3. Running the contract tests:

#### <ins>3.1 consumer_one:</ins>
1. Open `consumer_one` in IDE.
2. Execute the gradle task to run the contract tests of `consumer_one` with below command.

   `./gradlew contractTest` 
   
        If everything goes well, the test runs successfully and you should be able to see
        a pact json file in 'build>pacts>consumer_two-provider_one.json'
        
3. Execute the gradle task to publish this pact to the pact-broker running in docker with below command.

   `./gradlew pactPublish`
   
4. Go to the pact-broker UI (link in the Setting up pack broker above) and you should be able to see the pact of consumer_one in this UI

#### <ins>3.2 consumer_two:</ins>
1. Repeat the same process as mentioned for consumer_one.

#### <ins>3.3 provider_one:</ins>
1. Open `provider_one` in IDE.
2. Execute the gradle task to verify the contracts from the pact-broker with below command.

   `./gradlew contractTest`
   
        This task will verify the pacts of both the consumers published in pact-broker.
        
3. For the current setup, all the tests should come out green.
---
Experiment with the setup changing the pacts, repeat the process and run the contract tests, they should fail.
The best way to learn is to do !

⭐️ Give the repo a star if you liked the work or found it useful !


GOD SPEED !!
