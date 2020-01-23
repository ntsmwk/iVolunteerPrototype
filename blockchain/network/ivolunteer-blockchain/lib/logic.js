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
      return assetRegistry.addAll(tx.verificationObjects).then(function(ret){});  
    });
}