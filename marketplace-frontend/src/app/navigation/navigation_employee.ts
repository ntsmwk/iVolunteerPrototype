export const navigation_employee = [
  {
    'id': 'dashboard',
    'title': 'Dashboard',
    'type': 'item',
    'icon': 'dashboard',
    'url': '/main/dashboard'
  },
  {
    'id': 'profile',
    'title': 'Profile',
    'type': 'item',
    'icon': 'person',
    'url': '/main/profile'
  },
  {
    'id': 'calendar',
    'title': 'Calendar',
    'type': 'item',
    'icon': 'today',
    'url': '/main/calendar'
  },
  {
    'id': 'tasks',
    'title': 'Tasks',
    'type': 'collapse',
    'icon': 'work',
    'children': [
      {
        'id': 'create-tasks',
        'title': 'Form',
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
        'title': 'Form',
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
