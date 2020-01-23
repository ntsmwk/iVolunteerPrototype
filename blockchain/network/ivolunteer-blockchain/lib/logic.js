'use strict';
/**
 * transction processor functions 
 */

var NS = 'at.jku.cis';

/**
* A transaction processor function description
* @param {at.jku.cis.StoreVerificationObjects} tx 
* @transaction
*/

function storeVerificationObjects(tx) {
  getAssetRegistry('at.jku.cis.verificationObject')
    .then(function(assetRegistry){
      return tx.verificationObjects.then(function(val){});
      // return assetRegistry.addAll(tx.verificationObjects);  
    });
}

/**
* A transaction processor function description
* @param {at.jku.cis.TestTransaction} test 
* @transaction
*/

function test(test) {
  getAssetRegistry('at.jku.cis.verificationObject')
  .then(function(assetRegistry){
        var factory = getFactory();
        var newAsset = factory.newResource('at.jku.cis', 'verificationObject', 'hash123'); 
        newAsset.volunteerId="vol123";
        assetRegistry.add(newAsset);
    // return tx.verificationObjects.then(function(val){});
    // return assetRegistry.addAll(tx.verificationObjects);  
  });
}