'use strict';
/**
 * Write your transction processor functions here
 */

/**
 * Place an order for a vehicle
 * @param {at.jku.cis.CreateTask} createTask - the CreateTask transaction
 * @transaction
 */
function createTask(createTask) {
    console.log('createTask');
}


/**
 * Update the status of a task
 * @param {at.jku.cis.UpdateTaskStatus} updateTaskStatus - the UpdateTaskStatus transaction
 * @transaction
 */
function updateTaskStatus(updateTaskStatus) {
    console.log('updateTaskStatus');
}