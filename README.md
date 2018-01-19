# iVolunteerPrototype

docker ps -aq | xargs docker rm -f
docker images -aq | xargs docker rmi -f

curl -sSL https://hyperledger.github.io/composer/install-hlfv1.sh | bash
