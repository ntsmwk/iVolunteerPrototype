'use strict';
/**
 * Write your transction processor functions here
 */

/**
 * Create a new task
 * @param {at.jku.cis.CreateTask} createTask - the CreateTask transaction
 * @transaction
 */
function createTask(createTask) {
    console.log('createTask');

    var factory = getFactory();
    var NS = 'at.jku.cis';

    var task = factory.newResource(NS, 'Task', createTask.taskId);
    task.taskStatus = 'CREATED';
    task.description = createTask.description;
    task.creator = createTask.creator;

    // save the task
    return getAssetRegistry(task.getFullyQualifiedType())
        .then(function (registry) {
            return registry.add(task);
        })
        .then(function(){
    		var createTaskEvent = factory.newEvent(NS, 'CreateTaskEvent');
            createTaskEvent.taskId = task.taskId;
            createTaskEvent.description = task.description;
    		emit(createTaskEvent);
    	});

}


/**
 * Update the status of a task
 * @param {at.jku.cis.UpdateTaskStatus} updateTaskStatus - the UpdateTaskStatus transaction
 * @transaction
 */
function updateTaskStatus(updateTaskStatus) {
    console.log('updateTaskStatus');
}