# iVolunteerPrototype v3


### Marketplace-DB:
docker run --name marketplace-db -p 27017:27017 -d mongo:3.6.2

### start fabric and deploy business network (precondition: hlf1.1):
1. cd ~/iVolunteerPrototype/blockchain/network/ivolunteer-blockchain
2. ./init

### start rest server:
1. cd ~/iVolunteerPrototype/blockchain/rest\ server
2. docker-compose up -d


