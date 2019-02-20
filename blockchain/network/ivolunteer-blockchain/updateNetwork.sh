search="  \"version\": \"0.0.";
startIndex=${#search}

# get current version number
while IFS=: read line
do
	if [[ "$line" == "$search"* ]]; then 
		((length=${#line}-${startIndex}-2))
		oldVersion=${line:startIndex:length}
		break
	fi
done <package.json

((newVersion=$oldVersion+1))

#increase version number in package.json file
sed -i 's/"version": "0.0.'"$oldVersion"'",/"version": "0.0.'"$newVersion"'",/g' package.json

# creating business network archive
composer archive create --sourceType dir --sourceName . -a ivolunteer-blockchain@0.0.$newVersion.bna

# installing the business network
composer network install --card PeerAdmin@hlfv1 --archiveFile ivolunteer-blockchain@0.0.$newVersion.bna

# upgrading the business network
composer network upgrade -c PeerAdmin@hlfv1 -n ivolunteer-blockchain -V 0.0.$newVersion

# ping newly deplyoed network
composer network ping -c admin@ivolunteer-blockchain

# remove old business archive
rm ivolunteer-blockchain@0.0.$oldVersion.bna


