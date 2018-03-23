'use strict';
/**
 * Write your transction processor functions here
 */

var NS = 'at.jku.cis';

// could be removed and replaces with simple predefined rest call 

/**
 * postSimpleHash
 * @param {at.jku.cis.postSimpleHash} postSimpleHash
 * @transaction
 */
function postSimpleHash(postSimpleHash) {
    console.log('postSimpleHash');

    var factory = getFactory();
    var simpleHash = factory.newResource(NS, 'simpleHash', postSimpleHash.hash);

    // save the hash
    return getAssetRegistry(simpleHash.getFullyQualifiedType())
        .then(function (registry) {
            return registry.add(simpleHash);
    	});
}