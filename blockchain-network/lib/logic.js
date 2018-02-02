'use strict';
/**
 * Write your transction processor functions here
 */

var NS = 'at.jku.cis';

/**
 * Create a new task
 * @param {at.jku.cis.createTask} createTask - the createTask transaction
 * @transaction
 */
function createTask(createTask) {
    console.log('createTask');

    var factory = getFactory();

    var task = factory.newResource(NS, 'Task', createTask.taskId);
    task.taskStatus = 'CREATED';
    task.description = createTask.description;
    task.creator = createTask.creator;

    // var currentParticipant = getCurrentParticipant();
    // task.creator = currentParticipant;

    // save the task
    return getAssetRegistry(task.getFullyQualifiedType())
        .then(function (registry) {
            return registry.add(task);
        })
        .then(function(){
    		var createTaskEvent = factory.newEvent(NS, 'createTaskEvent');
            createTaskEvent.task = task;
    		emit(createTaskEvent);
    	});

}

/**
 * Reserve volunteer for a task
 * @param {at.jku.cis.reserveTask} reserveTask - the reserveTask transaction
 * @transaction
 */
function reserveTask(reserveTask) {
    console.log('reserveTask');
    var factory = getFactory();

    var task = reserveTask.task;

    if(task.taskStatus == 'CREATED' || task.taskStatus == 'RESERVED') {
        task.taskStatus = 'RESERVED';

        if (!task.reservedVolunteers) {
            task.reservedVolunteers = [];
        }
        task.reservedVolunteers.push(reserveTask.volunteer);

        return getAssetRegistry(NS + '.Task')
            .then(function (registry) {
                return registry.update(task);
            })
            .then(function(){
                var reserveTaskEvent = factory.newEvent(NS, 'reserveTaskEvent');
                reserveTaskEvent.task = task;
                reserveTaskEvent.volunteer = reserveTask.volunteer;
                emit(reserveTaskEvent);
        })

    } else {
        throw new Error('Task not in right status!');
        // inform invoker
    };
    
}

/**
 * Assign a task reservation to volunteer(s)
 * @param {at.jku.cis.assignTask} assignTask - the assignTask transaction
 * @transaction
 */
function assignTask(assignTask) {
    console.log('assignTask');
    var factory = getFactory();

    var task = assignTask.task;

    if(task.taskStatus == 'RESERVED') {
        task.taskStatus = 'ASSIGNED';

        // make taskPerformer to an array if it isn't
        if (!task.taskPerformer) {
            task.taskPerformer = [];
        }

        // TODO: error handling überprüfen!
                
        // check if volunteer from assignTask.taskPerformer is also in task.reservedVolunteers
        while(assignTask.taskPerformer.length > 0) {
            var volunteer = assignTask.taskPerformer.pop();

            // if(task.reservedVolunteers.contains(volunteer))
            if(task.reservedVolunteers.indexOf(volunteer) != -1) {
                task.taskPerformer.push(volunteer);
            }else {
                throw new Error('Volunteer not in reservedVolunteers array');
                // inform invoker
                // via event? but no update
            }
        }
        
        return getAssetRegistry(NS + '.Task')
            .then(function (registry) {
                return registry.update(task);
            })
            .then(function(){
                var assignTaskEvent = factory.newEvent(NS, 'assignTaskEvent');
                assignTaskEvent.task = task;
                assignTaskEvent.taskPerformer = task.taskPerformer;
                emit(assignTaskEvent);
        });

    } else {
        throw new Error('Task not in right status!');
    }
}

/**
 * Finish a task
 * @param {at.jku.cis.finishTask} finishTask - the finishTask transaction
 * @transaction
 */
function finishTask(finishTask) {
    // TODO:
    // add check, that only taskPerformer can finish task
    // and that all taskPerformer have to finish task, before task gets to state "FINISHED"

    console.log('finishTask');
    var factory = getFactory();

    var task = finishTask.task;

    if(task.taskStatus == 'ASSIGNED') {
        task.taskStatus = 'FINISHED';

        return getAssetRegistry(NS + '.Task')
            .then(function (registry) {
                return registry.update(task);
            })
            .then(function(){
                var finishTaskEvent = factory.newEvent(NS, 'finishTaskEvent');
                finishTaskEvent.task = task;
                emit(finishTaskEvent);
        });

    } else {
        throw new Error('Task not in right status!');
    }
    
}
