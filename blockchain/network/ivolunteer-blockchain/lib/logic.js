'use strict';
/**
 * transction processor functions 
 */

var NS = 'at.jku.cis';
var MARKETPLACE = 'Marketplace';
var VOLUNTEER = 'Volunteer';
var COMPETENCE = 'competence';
var PUBLISHEDTASK = 'publishedTask';
var TASKINTERACTION = 'taskInteraction';
var FINISHEDTASK = 'finishedTask';

// ###### marketplaces queries

async function getAllMarketplaces() {
    let results = await query('selectAllMarketplaces');
}

async function getMarketplaceByID() {
    let results = await query('selectMarketplaceByID');
}

// ###### volunteers queries

async function getAllVolunteers() {
    let results = await query('selectAllVolunteers');
}

async function getVolunteerByID() {
    let results = await query('selectVolunteerByID');
}

// ###### task interaction queries

async function getAllTaskInteractions() {
    let results = await query('selectAllTaskInteractions');
}

async function getTaskInteractionByHash() {
    let results = await query('selectTaskInteractionByHash');
}

async function getTaskInteractionsByMarketplace() {
    let results = await query('selectTaskInteractionsByMarketplace');
}

async function getTaskInteractionsByType() {
    let results = await query('selectTaskInteractionsByType');
}
// ###### published tasks queries

async function getAllPublishedTasks() {
    let results = await query('selectAllPublishedTasks');
}

async function getPublishedTaskByHash() {
    let results = await query('selectPublishedTaskByHash');
}

async function getPublishedTaskByID() {
    let results = await query('selectPublishedTaskByID');
}

async function getPublishedTasksByMarketplace() {
    let results = await query('selectPublishedTasksByMarketplace');
}

// ###### finished tasks queries

async function getAllFinishedTasks() {
    let results = await query('selectAllFinishedTasks');
}

async function getFinishedTaskByHash() {
    let results = await query('selectFinishedTaskByHash');
}

async function getFinishedTasksByID() {
    let results = await query('selectFinishedTaskByID');
}

async function getAllFinishedTasksByVolunteer() {
    let results = await query('selectFinishedTasksByVolunteer');
}

async function getAllFinishedTasksByMarketplace() {
    let results = await query('selectFinishedTasksByMarketplace');
}

// ##### competences queries

async function getAllCompetences() {
    let results = await query('selectAllCompetences');
}

async function getCompetenceByHash() {
    let results = await query('selectCompetenceByHash');
}

async function getCompetenceByID() {
    let results = await query('selectCompetenceByID');
}

async function getCompetencesByVolunteer() {
    let results = await query('selectCompetencesByVolunteer');
}

async function getCompetencesByMarketplace() {
    let results = await query('selectCompetencesByMarketplace');
}

// https://stackoverflow.com/questions/50820526/creating-new-participant-and-adding-array-of-assets-by-reference-to-it#50822327
// https://stackoverflow.com/questions/50915604/cannot-post-hyperledger-transaction-on-rest-api?rq=1

// https://hyperledger.github.io/composer/latest/api/api-doc-index
// https://hyperledger.github.io/composer/latest/reference/js_scripts

// fix for transaction not going through:
// https://stackoverflow.com/questions/50915604/cannot-post-hyperledger-transaction-on-rest-api?rq=1

/**
 * @param {at.jku.cis.AddNewMarketplace} transaction
 * @transaction
 */
async function AddNewMarketplace(transaction) {
    let registry = await getParticipantRegistry(NS + "." + MARKETPLACE);
    let marketplace = getFactory().newResource(NS, MARKETPLACE, SHA256(transaction.marketplaceId));
    try {
        await registry.add(marketplace);
    } catch (error) { 
        // TODO: proper error handling
        throw new Error ('Error while trying to add new marketplace' + '\n' + error);
    }
}

/**
 * @param {at.jku.cis.AddNewVolunteer} transaction
 * @transaction
 */
async function AddNewVolunteer(transaction) {
    let registry = await getParticipantRegistry(NS + "." + VOLUNTEER);
    let volunteer = getFactory().newResource(NS, VOLUNTEER, SHA256(transaction.volunteerId));
    try {
        await registry.add(volunteer);    
    } catch (error) { 
        // TODO: proper error handling
        throw new Error ('Error while trying to add new volunteer' + '\n' + error);
    }
}

/**
 * @param {at.jku.cis.AddNewPublishedTask} transaction
 * @transaction
 */
async function AddNewPublishedTask(transaction) {
    let registry = await getAssetRegistry(NS + "." + PUBLISHEDTASK);
    let hash = SHA256(transaction.timeStamp + transaction.taskId +
        transaction.marketplaceId);
    
    let task = getFactory().newResource(NS, PUBLISHEDTASK, hash);
    task.timestamp =  transaction.timeStamp;
    task.taskId = transaction.taskId;
    task.marketplaceId =  transaction.marketplaceId;

    await registry.add(task);    
}

/**
 * @param {at.jku.cis.AddNewTaskInteraction} transaction
 * @transaction
 */
async function AddNewTaskInteraction(transaction) {
    let registry = await getAssetRegistry(NS + "." + TASKINTERACTION);
    let hash = SHA256(transaction.timeStamp + transaction.taskId +
        transaction.marketplaceId + transaction.taskInteractionType);
    
    let task = getFactory().newResource(NS, TASKINTERACTION, hash);
    task.timestamp =  transaction.timeStamp;
    task.taskId = transaction.taskId;
    task.marketplaceId =  transaction.marketplaceId;
    task.taskInteractionType = transaction.taskInteractionType; // SHA256 needed?
    
    await registry.add(task); 
}

/**
 * @param {at.jku.cis.AddNewFinishedTask} transaction
 * @transaction
 */
async function AddNewFinishedTask(transaction) {
    let registry = await getAssetRegistry(NS + "." + FINISHEDTASK);
    let hash = SHA256(transaction.timeStamp + transaction.taskId +
        transaction.marketplaceId + transaction.volunteerId);
    
    let task = getFactory().newResource(NS, FINISHEDTASK, hash);
    task.timestamp =  transaction.timeStamp;
    task.taskId = SHA256(transaction.taskId);
    task.marketplaceId =  SHA256(transaction.marketplaceId);
    task.volunteerId = SHA256(transaction.volunteerId);
    
    await registry.add(task);    
}

/**
 * @param {at.jku.cis.AddNewCompetence} transaction
 * @transaction
 */
async function AddNewCompetence(transaction) {
    // TODO: check if competence can be rewarded

    let registry = await getAssetRegistry(NS + "." + COMPETENCE);
    let hash = SHA256(transaction.timeStamp + transaction.competenceId +
        transaction.marketplaceId + transaction.volunteerId);

    let competence = getFactory().newResource(NS, COMPETENCE, hash);
    competence.timestamp = transaction.timeStamp;
    competence.competenceId = SHA256(transaction.competenceId);
    competence.marketplaceId = SHA256(transaction.marketplaceId);
    competence.volunteerId = SHA256(transaction.volunteerId);

    await registry.add(competence);
}


// online hash calculator to verify if necessary:
// https://www.xorbin.com/tools/sha256-hash-calculator

// TODO: MOVE HASH FUNCTION TO SEPARATE FILE
// http://www.webtoolkit.info/javascript_sha256.html

function SHA256(s){

 


    var chrsz   = 8;


    var hexcase = 0;

 


    function safe_add (x, y) {


        var lsw = (x & 0xFFFF) + (y & 0xFFFF);


        var msw = (x >> 16) + (y >> 16) + (lsw >> 16);


        return (msw << 16) | (lsw & 0xFFFF);


    }

 


    function S (X, n) { return ( X >>> n ) | (X << (32 - n)); }


    function R (X, n) { return ( X >>> n ); }


    function Ch(x, y, z) { return ((x & y) ^ ((~x) & z)); }


    function Maj(x, y, z) { return ((x & y) ^ (x & z) ^ (y & z)); }


    function Sigma0256(x) { return (S(x, 2) ^ S(x, 13) ^ S(x, 22)); }


    function Sigma1256(x) { return (S(x, 6) ^ S(x, 11) ^ S(x, 25)); }


    function Gamma0256(x) { return (S(x, 7) ^ S(x, 18) ^ R(x, 3)); }


    function Gamma1256(x) { return (S(x, 17) ^ S(x, 19) ^ R(x, 10)); }

 


    function core_sha256 (m, l) {


        var K = new Array(0x428A2F98, 0x71374491, 0xB5C0FBCF, 0xE9B5DBA5, 0x3956C25B, 0x59F111F1, 0x923F82A4, 0xAB1C5ED5, 0xD807AA98, 0x12835B01, 0x243185BE, 0x550C7DC3, 0x72BE5D74, 0x80DEB1FE, 0x9BDC06A7, 0xC19BF174, 0xE49B69C1, 0xEFBE4786, 0xFC19DC6, 0x240CA1CC, 0x2DE92C6F, 0x4A7484AA, 0x5CB0A9DC, 0x76F988DA, 0x983E5152, 0xA831C66D, 0xB00327C8, 0xBF597FC7, 0xC6E00BF3, 0xD5A79147, 0x6CA6351, 0x14292967, 0x27B70A85, 0x2E1B2138, 0x4D2C6DFC, 0x53380D13, 0x650A7354, 0x766A0ABB, 0x81C2C92E, 0x92722C85, 0xA2BFE8A1, 0xA81A664B, 0xC24B8B70, 0xC76C51A3, 0xD192E819, 0xD6990624, 0xF40E3585, 0x106AA070, 0x19A4C116, 0x1E376C08, 0x2748774C, 0x34B0BCB5, 0x391C0CB3, 0x4ED8AA4A, 0x5B9CCA4F, 0x682E6FF3, 0x748F82EE, 0x78A5636F, 0x84C87814, 0x8CC70208, 0x90BEFFFA, 0xA4506CEB, 0xBEF9A3F7, 0xC67178F2);


        var HASH = new Array(0x6A09E667, 0xBB67AE85, 0x3C6EF372, 0xA54FF53A, 0x510E527F, 0x9B05688C, 0x1F83D9AB, 0x5BE0CD19);


        var W = new Array(64);


        var a, b, c, d, e, f, g, h, i, j;


        var T1, T2;

 


        m[l >> 5] |= 0x80 << (24 - l % 32);


        m[((l + 64 >> 9) << 4) + 15] = l;

 


        for ( var i = 0; i<m.length; i+=16 ) {


            a = HASH[0];


            b = HASH[1];


            c = HASH[2];


            d = HASH[3];


            e = HASH[4];


            f = HASH[5];


            g = HASH[6];


            h = HASH[7];

 


            for ( var j = 0; j<64; j++) {


                if (j < 16) W[j] = m[j + i];


                else W[j] = safe_add(safe_add(safe_add(Gamma1256(W[j - 2]), W[j - 7]), Gamma0256(W[j - 15])), W[j - 16]);

 


                T1 = safe_add(safe_add(safe_add(safe_add(h, Sigma1256(e)), Ch(e, f, g)), K[j]), W[j]);


                T2 = safe_add(Sigma0256(a), Maj(a, b, c));

 


                h = g;


                g = f;


                f = e;


                e = safe_add(d, T1);


                d = c;


                c = b;


                b = a;


                a = safe_add(T1, T2);


            }

 


            HASH[0] = safe_add(a, HASH[0]);


            HASH[1] = safe_add(b, HASH[1]);


            HASH[2] = safe_add(c, HASH[2]);


            HASH[3] = safe_add(d, HASH[3]);


            HASH[4] = safe_add(e, HASH[4]);


            HASH[5] = safe_add(f, HASH[5]);


            HASH[6] = safe_add(g, HASH[6]);


            HASH[7] = safe_add(h, HASH[7]);


        }


        return HASH;


    }

 


    function str2binb (str) {


        var bin = Array();


        var mask = (1 << chrsz) - 1;


        for(var i = 0; i < str.length * chrsz; i += chrsz) {


            bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (24 - i%32);


        }


        return bin;


    }

 


    function Utf8Encode(string) {


        string = string.replace(/\r\n/g,"\n");


        var utftext = "";

 


        for (var n = 0; n < string.length; n++) {

 


            var c = string.charCodeAt(n);

 


            if (c < 128) {


                utftext += String.fromCharCode(c);


            }


            else if((c > 127) && (c < 2048)) {


                utftext += String.fromCharCode((c >> 6) | 192);


                utftext += String.fromCharCode((c & 63) | 128);


            }


            else {


                utftext += String.fromCharCode((c >> 12) | 224);


                utftext += String.fromCharCode(((c >> 6) & 63) | 128);


                utftext += String.fromCharCode((c & 63) | 128);


            }

 


        }

 


        return utftext;


    }

 


    function binb2hex (binarray) {


        var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";


        var str = "";


        for(var i = 0; i < binarray.length * 4; i++) {


            str += hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8+4)) & 0xF) +


            hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8  )) & 0xF);


        }


        return str;


    }

 


    s = Utf8Encode(s);


    return binb2hex(core_sha256(str2binb(s), s.length * chrsz));

 

}


