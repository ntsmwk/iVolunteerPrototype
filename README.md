# iVolunteerPrototype v2


### Marketplace-DB:
docker run --name marketplace-db -p 27017:27017 -d mongo:3.6.2

### rest server:
* On first startup
    1. cd ~/.composer/cards/admin@ivolunteer-blockchain
    2. in connection.json change ip adresses from "localhost" to "172.17.0.1"

1. cd ~/iVolunteerPrototype/blockchain/rest\ server
2. docker-compose up -d


