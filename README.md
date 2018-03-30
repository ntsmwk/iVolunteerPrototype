# iVolunteerPrototype v2


### Marketplace-DB:
docker run --name marketplace-db -p 27017:27017 -d mongo:3.6.2

### start fabric and deploy business network (precondition: hlf1.1):
1. cd ~/iVolunteerPrototype/blockchain/network/ivolunteer-blockchain
2. ./init

### update network
1. composer archive create -t dir -n .
2. composer network update --archiveFile ivolunteer-blockchain@0.0.1.bna --card admin@ivolunteer-blockchain

### start rest server:
1. cd ~/iVolunteerPrototype/blockchain/rest\ server
2. docker-compose up -d



## Update Fabric to 1.1:
1. Remove old docker images 
	docker rmi $(docker images -q)
	// This will delete ALL images

2. Remove old fabric-tools
	rmdir ~/fabric-tools

3. Update composer
	npm install -g composer-cli
	npm install -g composer-rest-server
	npm install -g generator-hyperledger-composer
	npm install -g yo

4. Download fabric-tools
	mkdir ~/fabric-tools && cd ~/fabric-tools
	curl -O https://raw.githubusercontent.com/hyperledger/composer-tools/master/packages/fabric-dev-servers/fabric-dev-servers.tar.gz
	tar -xvf fabric-dev-servers.tar.gz

5. Run init script to download and start fabric and deploy network
	cd ~/iVolunteerPrototype/blockchain/network/ivolunteer-blockchain
	./init




