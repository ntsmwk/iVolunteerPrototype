# iVolunteerPrototype v3 BLOCKCHAIN

### bei Versionierungsproblemen -> Network updaten

### update network
0. update version number in package.json
1. composer archive create -t dir -n .
2. composer network install --card PeerAdmin@hlfv1 --archiveFile ivolunteer-blockchain@X.bna (X stands for the updated version number, eg 0.0.2)
3. composer network upgrade --card PeerAdmin@hlfv1 --networkName ivolunteer-blockchain --networkVersion X






