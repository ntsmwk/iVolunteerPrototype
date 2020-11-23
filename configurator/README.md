# iVolunteerPrototype v4

### Marketplace-DB:
docker run --name marketplace-db --restart=unless-stopped -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=root -p 27017:27017 -d mongo:3.6.2

### Marketplace-Workflow-DB
docker run --name marketplace-workflow-db --restart=unless-stopped -e MYSQL_DATABASE=workflow -e MYSQL_USER=workflow -e MYSQL_PASSWORD=workflow -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:5.7.22

### Install MxGraph
npm i mxgraph

### start fabric and deploy business network (precondition: hlf1.1):
1. cd ~/iVolunteerPrototype/blockchain/network/ivolunteer-blockchain
2. ./init

### start rest server:
1. cd ~/iVolunteerPrototype/blockchain/rest\ server
2. docker-compose up -d


### Deployment:

mvn clean install -Pdev 

mvn clean install -Pprod1

mvn clean install -Pprod2

start: nohup java -jar target/marketplace-application-0.0.1-SNAPSHOT.jar &> marketplace.out &

