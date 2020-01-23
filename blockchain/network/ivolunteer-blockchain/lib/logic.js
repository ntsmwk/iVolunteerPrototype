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

async function storeVerificationObjects(tx) {
  getAssetRegistry('at.jku.cis.verificationObject')
    .then(function(assetRegistry){
      var factory = getFactory();
      var ls = [];
      tx.verificationObjects.forEach(vo => {
        var resource = factory.newResource('at.jku.cis', 'verificationObject', vo.hash);
        resource.volunteerId = vo.volunteerId;
        ls.push(resource);
      });
      assetRegistry.addAll(ls).then(ret => {});
    });
}