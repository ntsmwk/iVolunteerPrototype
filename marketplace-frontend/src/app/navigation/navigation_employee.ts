export const navigation_employee = [
  {
    'id': 'dashboard',
    'title': 'Dashboard',
    'type': 'item',
    'icon': 'dashboard',
    'url': '/main/dashboard'
  },
  {
    'id': 'projects',
    'title': 'Projects',
    'type': 'collapse',
    'icon': 'work',
    'children': [
      {
        'id': 'create-project',
        'title': 'Create',
        'type': 'item',
        'url': '/main/project-form'
      },
      {
        'id': 'all-projects',
        'title': 'List',
        'type': 'item',
        'url': '/main/projects/all'
      }
    ]
  },
  {
    'id': 'tasks',
    'title': 'Tasks',
    'type': 'collapse',
    'icon': 'work',
    'children': [
      {
        'id': 'create-task',
        'title': 'Create',
        'type': 'item',
        'url': '/main/task-form'
      },
      {
        'id': 'all-tasks',
        'title': 'List',
        'type': 'item',
        'url': '/main/tasks/all'
      }
    ]
  },
  {
    'id': 'tasksTemplate',
    'title': 'Task Templates',
    'type': 'collapse',
    'icon': 'work',
    'children': [
      {
        'id': 'create-tasks-template',
        'title': 'Create',
        'type': 'item',
        'url': '/main/task-template-form'
      },
      {
        'id': 'all-task-templates',
        'title': 'List',
        'type': 'item',
        'url': '/main/task-templates/all'
      }
    ]
  }
];
