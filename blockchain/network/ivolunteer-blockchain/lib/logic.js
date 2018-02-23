'use strict';
/**
 * Write your transction processor functions here
 */

var NS = 'at.jku.cis';

/**
 * postSimpleHash
 * @param {at.jku.cis.postSimpleHash} postSimpleHash
 * @transaction
 */
function postSimpleHash(postSimpleHash) {
    console.log('postSimpleHash');

    var factory = getFactory();

    var simpleHash = factory.newResource(NS, 'simpleHash', postSimpleHash.id);
    simpleHash.hash = postSimpleHash.hash;

    // save the hash
    return getAssetRegistry(simpleHash.getFullyQualifiedType())
        .then(function (registry) {
            return registry.add(simpleHash);
    	});
}


/**
 * postGlobalHash
 * @param {at.jku.cis.postGlobalHash} postGlobalHash
 * @transaction
 */
function postGlobalHash(postGlobalHash) {
    console.log('postGlobalHash');

    var factory = getFactory();

    var globalHash = factory.newResource(NS, 'globalHash', postGlobalHash.id);
    globalHash.userId = postGlobalHash.userId;
    globalHash.hash = postGlobalHash.hash;

    // save the hash
    return getAssetRegistry(globalHash.getFullyQualifiedType())
        .then(function (registry) {
            return registry.add(globalHash);
    	});
}