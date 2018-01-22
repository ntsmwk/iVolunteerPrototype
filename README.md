# iVolunteerPrototype

# Start Fabric
1. docker ps -aq | xargs docker rm -f
2. docker images -aq | xargs docker rmi -f

3. curl -sSL https://hyperledger.github.io/composer/install-hlfv1.sh | bash
4. ./startFabric.sh
4. ./createPeerAdminCard.sh

# 


0. cd iVolunteer/blockchain-network
1. composer archive create -t dir -n .
2. composer runtime install --card PeerAdmin@hlfv1 --businessNetworkName blockchain-network
3. composer network start --card PeerAdmin@hlfv1 --networkAdmin admin --networkAdminEnrollSecret adminpw --archiveFile blockchain-network@0.0.1.bna --file networkadmin.card
4. composer card import --file networkadmin.card
5. composer network ping --card admin@blockchain-network
6. composer-rest-server -c admin@blockchain-network -n never -w true

# Connect to couchDB
1. Open url in browser http://localhost:5984/_utils/

#ANGULAR SETUP

1. npm install -g @angular/cli
2. npm install


