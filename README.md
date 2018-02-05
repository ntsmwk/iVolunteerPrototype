# iVolunteerPrototype

# Starting the Fabric:
0. cd fabric-tools/
1. ./downloadFabric.sh
2. ./startFabric.sh
3. ./createPeerAdminCard.sh

other commands:
./stopFabric.sh
./teardownFabric.sh
composer card list
# 

# Deploying the business network
0. cd iVolunteer/blockchain-network
1. composer archive create -t dir -n .
2. composer runtime install --card PeerAdmin@hlfv1 --businessNetworkName blockchain-network
3. composer network start --card PeerAdmin@hlfv1 --networkAdmin admin --networkAdminEnrollSecret adminpw --archiveFile blockchain-network@0.0.1.bna --file blockchainadmin.card
4. composer card import --file blockchainadmin.card
5. composer network ping --card admin@blockchain-network
6. composer-rest-server -c admin@blockchain-network -n never -w true
# 

# Updating the network
0. cd iVolunteer/blockchain-network
1. composer archive create -t dir -n .
2. composer network update --archiveFile blockchain-network@0.0.1.bna --card admin@blockchain-network
# 

# Creating participant, creating and importing business network cards
* composer participant add --card admin@blockchain-network --data '{"$class":"at.jku.cis.Organisation","orgName":"org1","email":"org1@mail.com"}'
* composer identity issue --card admin@blockchain-network --file organisation1.card --newUserId organisation1 --participantId 'resource:at.jku.cis.Organisation#org1@mail.com'
* composer card import --file organisation1.card


# Connecting to couchDB
1. Open url in browser: http://localhost:5984/_utils/

# Angular setup
1. npm install -g @angular/cli
2. npm install electron-packager -g
3. npm install
